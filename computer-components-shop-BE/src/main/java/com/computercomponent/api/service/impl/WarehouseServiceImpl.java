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
import java.util.List;

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
                warehouse.setPaymentStatus(PaymentStatus.PENDING);
                warehouseRepository.save(warehouse);
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
        Integer oldTotalQuantity = warehouse.getTotalQuantity() != null ? warehouse.getTotalQuantity() : 0;

        // Cập nhật các trường mới từ DTO vào entity Warehouse
        warehouse.setTransactionDate(LocalDateTime.now()); // Lấy ngày hiện tại cho giao dịch
        BeanUtils.copyProperties(warehouseUpdateRequestDTO, warehouse); // Copy các trường khác từ DTO vào entity

        // Lưu cập nhật Warehouse
        warehouseRepository.save(warehouse);

        // Xử lý sản phẩm
        List<WarehouseProductDTO> warehouseProductDTO = warehouseUpdateRequestDTO.getWarehouseProductDTOS();
        Long productId = warehouseProductDTO.get(0).getProductId();
        Integer newTotalQuantity = warehouseUpdateRequestDTO.getTotalQuantity() != null ? warehouseUpdateRequestDTO.getTotalQuantity() : 0;

        Products product = productsRepository.findProductsById(productId);
        Assert.isTrue(product != null, Const.PRODUCTS.PROD_NOT_FOUND);

        Integer currentQuantity = product.getQuantityAvailable() != null ? product.getQuantityAvailable() : 0;

        // Kiểm tra trạng thái thanh toán
        // Kiểm tra trạng thái thanh toán
        if (warehouseUpdateRequestDTO.getPaymentStatus() == PaymentStatus.COMPLETED) {

            Integer newQuantity;

            // Xử lý loại giao dịch
            if (warehouseUpdateRequestDTO.getType() == TransactionType.IMPORT) {
                newQuantity = currentQuantity + (newTotalQuantity - oldTotalQuantity);
            } else if (warehouseUpdateRequestDTO.getType() == TransactionType.EXPORT) {
                Assert.isTrue(currentQuantity >= (newTotalQuantity - oldTotalQuantity), Const.PRODUCTS.INSUFFICIENT_QUANTITY);
                newQuantity = currentQuantity - (newTotalQuantity - oldTotalQuantity);
            } else {
                throw new RuntimeException(Const.WAREHOUSE.INVALID_TRANSACTION_TYPE);
            }

            // Đảm bảo số lượng hợp lệ
            Assert.isTrue(newQuantity >= 0, Const.PRODUCTS.INVALID_QUANTITY);

            // Cập nhật số lượng sản phẩm
            product.setQuantityAvailable(newQuantity);
            productsRepository.save(product);
        } else {
            // Đặt trạng thái thanh toán thành PENDING
            warehouse.setPaymentStatus(PaymentStatus.PENDING);
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
        List<WarehouseProductDTO> warehouseProductDTOS = warehouseRepository.getWarehouseProduct(id);
        warehouseDetail.setWarehouseProductDTOS(warehouseProductDTOS);
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
