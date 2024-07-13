package com.computercomponent.api.service.impl;

import com.computercomponent.api.common.Const;
import com.computercomponent.api.common.ProductsStatus;
import com.computercomponent.api.dto.WarehouseDTO;
import com.computercomponent.api.dto.WarehouseManagementDTO;
import com.computercomponent.api.dto.WarehouseManagementStatusDTO;
import com.computercomponent.api.dto.WarehouseUpdateRequestDTO;
import com.computercomponent.api.entity.Brand;
import com.computercomponent.api.entity.Products;
import com.computercomponent.api.entity.Warehouse;
import com.computercomponent.api.repository.ProductsRepository;
import com.computercomponent.api.repository.WarehouseRepository;
import com.computercomponent.api.request.WarehouseRequest;
import com.computercomponent.api.response.ProductDetail;
import com.computercomponent.api.response.ProductSpecificationDetail;
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
import java.util.Date;
import java.util.List;

@Service
public class WarehouseServiceImpl implements WarehouseService {
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private ProductsRepository productsRepository;

    @Override
    @Transactional
    public String create(WarehouseDTO warehouseDTO) {
        Warehouse warehouse = new Warehouse();
        warehouse.setTransactionDate(new Date());
        BeanUtils.copyProperties(warehouseDTO, warehouse);
        warehouseRepository.save(warehouse);
        Long productId = warehouseDTO.getProductId();
        Integer totalQuantity = warehouseDTO.getTotalQuantity();

        Products product = productsRepository.findProductsById(productId);
        Assert.isTrue(product != null, Const.PRODUCTS.PROD_NOT_FOUND);

        Integer currentQuantity = product.getQuantityAvailable();
        Integer newQuantity = currentQuantity + totalQuantity;
        product.setQuantityAvailable(newQuantity);
        product.setUpdatedAt(new Date());

        productsRepository.save(product);
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
        Warehouse warehouse = warehouseRepository.findWarehouseById(warehouseUpdateRequestDTO.getId());
        Assert.isTrue(warehouse != null, Const.WAREHOUSE.WAREHOUSE_NOT_FOUND);
        Integer oldTotalQuantity = warehouse.getTotalQuantity();
        warehouse.setTransactionDate(new Date());
        BeanUtils.copyProperties(warehouseUpdateRequestDTO, warehouse);
        warehouseRepository.save(warehouse);

        Long productId = warehouseUpdateRequestDTO.getProductId();
        Integer newTotalQuantity = warehouseUpdateRequestDTO.getTotalQuantity();
        Products product = productsRepository.findProductsById(productId);
        Assert.isTrue(product != null, Const.PRODUCTS.PROD_NOT_FOUND);
        Integer currentQuantity = product.getQuantityAvailable();
        Integer newQuantity = currentQuantity - oldTotalQuantity + newTotalQuantity; // Điều chỉnh lại dựa trên thay đổi số lượng
        product.setQuantityAvailable(newQuantity);
        product.setUpdatedAt(new Date());
        productsRepository.save(product);
        return warehouseUpdateRequestDTO;
    }

    @Override
    public String delete(Long id) {
        Warehouse warehouse = warehouseRepository.findWarehouseById(id);
        Assert.isTrue(warehouse != null, Const.WAREHOUSE.WAREHOUSE_NOT_FOUND);
        warehouse.setDeleted(true);
        warehouseRepository.save(warehouse);
        return null;
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
