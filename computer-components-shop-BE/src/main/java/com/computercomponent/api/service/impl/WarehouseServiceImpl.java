package com.computercomponent.api.service.impl;

import com.computercomponent.api.common.*;
import com.computercomponent.api.dto.*;
import com.computercomponent.api.entity.Products;
import com.computercomponent.api.entity.Warehouse;
import com.computercomponent.api.entity.WarehouseProduct;
import com.computercomponent.api.repository.ProductsRepository;
import com.computercomponent.api.repository.WarehouseProductRepository;
import com.computercomponent.api.repository.WarehouseRepository;
import com.computercomponent.api.request.WarehouseRequest;
import com.computercomponent.api.response.WarehouseDetail;
import com.computercomponent.api.service.WarehouseService;
import com.computercomponent.api.until.DataUtil;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class WarehouseServiceImpl implements WarehouseService {
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    private WarehouseProductRepository warehouseProductRepository;

    @Override
    @Transactional
    public String create(WarehouseDTO warehouseDTO) {
        // Kiểm tra các đầu vào quan trọng
        Assert.notNull(warehouseDTO.getWarehouseProductDTOS(), "Danh sách sản phẩm không được để trống");
        Assert.notNull(warehouseDTO.getType(), "Loại giao dịch không được để trống");
        Assert.notNull(warehouseDTO.getPaymentStatus(), "Trạng thái thanh toán không được để trống");

        // Khởi tạo entity Warehouse
        Warehouse warehouse = new Warehouse();
        BeanUtils.copyProperties(warehouseDTO, warehouse);
        warehouse.setTransactionDate(LocalDateTime.now());

        // Lưu trước warehouse để lấy ID
        warehouseRepository.save(warehouse);

        // Kiểm tra và xử lý từng sản phẩm trong danh sách
        for (WarehouseProductDTO warehouseProductDTO : warehouseDTO.getWarehouseProductDTOS()) {
            Long productId = warehouseProductDTO.getProductId();
            Products product = productsRepository.findProductsById(productId);
            Assert.isTrue(product != null, Const.PRODUCTS.PROD_NOT_FOUND);

            Integer currentQuantity = product.getQuantityAvailable() != null ? product.getQuantityAvailable() : 0;
            Integer transactionQuantity = warehouseProductDTO.getQuantity();
            Assert.isTrue(transactionQuantity > 0, "Số lượng sản phẩm phải lớn hơn 0");

            // Xử lý trạng thái thanh toán
            if (warehouseDTO.getPaymentStatus() == PaymentStatus.COMPLETED) {
                if (warehouseDTO.getType() == TransactionType.IMPORT) {
                    product.setQuantityAvailable(currentQuantity + transactionQuantity);
                } else if (warehouseDTO.getType() == TransactionType.EXPORT) {
                    Assert.isTrue(currentQuantity >= transactionQuantity, Const.PRODUCTS.INSUFFICIENT_QUANTITY);
                    product.setQuantityAvailable(currentQuantity - transactionQuantity);
                }
                productsRepository.save(product);
            } else {
                warehouse.setStatus(WarehouseStatus.PENDING);
            }

            // Lưu thông tin WarehouseProduct
            WarehouseProduct warehouseProduct = new WarehouseProduct();
            BeanUtils.copyProperties(warehouseProductDTO, warehouseProduct);
            warehouseProduct.setWarehouseId(warehouse.getId());
            warehouseProduct.setProductId(productId);
            warehouseProduct.setQuantity(transactionQuantity);
            warehouseProductRepository.save(warehouseProduct);
        }

        // Lưu lại thông tin giao dịch kho sau khi đã cập nhật trạng thái
        warehouseRepository.save(warehouse);

        return Const.MESSAGE_CODE.SUCCESS;
    }

    @Override
    public Page<WarehouseManagementDTO> getList(WarehouseRequest warehouseRequest) {
        PageRequest pageRequest = DataUtil.getPageable(warehouseRequest.getPageNumber(), warehouseRequest.getPageSize());
        if (warehouseRequest.getSearchField() == null) {
            warehouseRequest.setSearchField("");
        }
        if (warehouseRequest.getStatus() != null) {
            ProductsStatus status = ProductsStatus.fromValue(warehouseRequest.getStatus());
            Assert.notNull(status, Const.MESSAGE_CODE.STATUS_NOT_FOUND);
        }
        return warehouseRepository.findAllAndSearch(warehouseRequest.getSearchField().trim(), warehouseRequest.getStatus(), warehouseRequest.getType(), pageRequest);
    }

    @Override
    @Transactional
    public WarehouseUpdateRequestDTO update(WarehouseUpdateRequestDTO warehouseUpdateRequestDTO) {
        // Tìm đối tượng Warehouse theo ID
        Warehouse warehouse = warehouseRepository.findWarehouseById(warehouseUpdateRequestDTO.getId());
        Assert.isTrue(warehouse != null, Const.WAREHOUSE.WAREHOUSE_NOT_FOUND);

        // Lấy số lượng cũ để tính toán số lượng sau khi cập nhật
        Integer oldTotalQuantity = warehouse.getTotalQuantity();

        // Cập nhật các trường mới từ DTO vào entity Warehouse
        warehouse.setTransactionDate(LocalDateTime.now()); // Lấy ngày hiện tại cho giao dịch
        BeanUtils.copyProperties(warehouseUpdateRequestDTO, warehouse); // Copy các trường khác từ DTO vào entity

        // Lưu cập nhật Warehouse
        warehouseRepository.save(warehouse);

        // Xử lý sản phẩm
        Long productId = warehouseUpdateRequestDTO.getProductId();
        Integer newTotalQuantity = warehouseUpdateRequestDTO.getTotalQuantity();

        Products product = productsRepository.findProductsById(productId);
        Assert.isTrue(product != null, Const.PRODUCTS.PROD_NOT_FOUND);

        Integer currentQuantity = product.getQuantityAvailable();

        // Kiểm tra trạng thái thanh toán
        if (warehouseUpdateRequestDTO.getPaymentStatus() == PaymentStatus.COMPLETED) {
            // Điều chỉnh số lượng tồn kho theo loại giao dịch (nhập hoặc xuất)
            Integer newQuantity;
            if (warehouseUpdateRequestDTO.getType() == TransactionType.IMPORT) {
                // Nhập kho: tăng số lượng
                newQuantity = currentQuantity - oldTotalQuantity + newTotalQuantity;
            } else if (warehouseUpdateRequestDTO.getType() == TransactionType.EXPORT) {
                // Xuất kho: giảm số lượng, kiểm tra đủ hàng
                Assert.isTrue(currentQuantity >= newTotalQuantity, Const.PRODUCTS.INSUFFICIENT_QUANTITY);
                newQuantity = currentQuantity + oldTotalQuantity - newTotalQuantity;
            } else {
                throw new RuntimeException(Const.WAREHOUSE.INVALID_TRANSACTION_TYPE);
            }

            // Cập nhật lại số lượng sản phẩm
            product.setQuantityAvailable(newQuantity);
            productsRepository.save(product);
        } else {
            // Xử lý nếu trạng thái thanh toán không phải là COMPLETED
            warehouse.setStatus(WarehouseStatus.PENDING);
            warehouseRepository.save(warehouse);
        }

        return warehouseUpdateRequestDTO;
    }

    @Override
    public String delete(Long id) {
        // Tìm đối tượng Warehouse theo ID
        Warehouse warehouse = warehouseRepository.findWarehouseById(id);
        Assert.isTrue(warehouse != null, Const.WAREHOUSE.WAREHOUSE_NOT_FOUND);

        // Kiểm tra trạng thái thanh toán
        if (warehouse.getPaymentStatus() == PaymentStatus.COMPLETED) {
            throw new IllegalArgumentException(Const.WAREHOUSE.CANNOT_DELETE_WAREHOUSE_RECORD);
        }

        // Đánh dấu là đã xóa
        warehouse.setDeleted(true);
        warehouseRepository.save(warehouse);
        return Const.MESSAGE_CODE.SUCCESS;
    }

    @Override
    public WarehouseDetail getDetail(Long id) {
        WarehouseDetail warehouseDetail = warehouseRepository.getDetail(id);
        return warehouseDetail;
    }

    @Override
    public WarehouseManagementStatusDTO updateStatus(WarehouseManagementStatusDTO warehouseManagementStatusDTO) {
        Warehouse warehouse = warehouseRepository.findWarehouseById(warehouseManagementStatusDTO.getId());
        org.springframework.util.Assert.isTrue(warehouse != null, Const.WAREHOUSE.WAREHOUSE_NOT_FOUND);
        BeanUtils.copyProperties(warehouseManagementStatusDTO, warehouse);
        warehouseRepository.save(warehouse);
        return null;
    }
}
