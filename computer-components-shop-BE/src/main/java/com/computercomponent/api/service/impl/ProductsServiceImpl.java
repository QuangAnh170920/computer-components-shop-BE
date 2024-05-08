package com.computercomponent.api.service.impl;

import com.computercomponent.api.common.Const;
import com.computercomponent.api.common.ProductsStatus;
import com.computercomponent.api.dto.ProductsDTO;
import com.computercomponent.api.dto.ProductsManagementDTO;
import com.computercomponent.api.entity.Categories;
import com.computercomponent.api.entity.Products;
import com.computercomponent.api.repository.ProductsRepository;
import com.computercomponent.api.request.ProductsRequest;
import com.computercomponent.api.service.ProductsService;
import com.computercomponent.api.until.DataUtil;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductsServiceImpl implements ProductsService {
    @Autowired
    private ProductsRepository productsRepository;

    @Override
    public String createProduct(ProductsDTO productsDTO) {
        validateProduct(productsDTO);
        Products products = new Products();
        BeanUtils.copyProperties(productsDTO, products);
        productsRepository.save(products);
        return Const.MESSAGE_CODE.SUCCESS;
    }

    @Override
    public Page<ProductsManagementDTO> getProductsList(ProductsRequest productsRequest) {
        PageRequest pageRequest = DataUtil.getPageable(productsRequest.getPageNumber(), productsRequest.getPageSize());
        if (productsRequest.getSearchField() == null) {
            productsRequest.setSearchField("");
        }
        if (productsRequest.getStatus() != null) {
            ProductsStatus status = ProductsStatus.fromValue(productsRequest.getStatus());
            Assert.notNull(status, Const.MESSAGE_CODE.STATUS_NOT_FOUND);
        }
        return productsRepository.findAllAndSearch(productsRequest.getSearchField().trim(), productsRequest.getStatus(), pageRequest);
    }

    // cần viết và check update status
    @Override
    public ProductsManagementDTO updateProduct(ProductsManagementDTO productsManagementDTO) {
        Products products = productsRepository.findProductsById(productsManagementDTO.getId());
        Assert.isTrue(products != null, Const.PRODUCTS.PROD_NOT_FOUND);
        validateUpdateProduct(productsManagementDTO);
        BeanUtils.copyProperties(productsManagementDTO, products);
        productsRepository.save(products);
        return null;
    }

    // cần check thêm điều kiện của Prod. Nếu trong TH Prod đã có thông tin chi tiết, hình ảnh => không được xóa
    @Override
    public String deleteProduct(Long id) {
        Products products = productsRepository.findProductsById(id);
        Assert.isTrue(products != null, Const.CATEGORIES.CATE_NOT_FOUND);
        products.setDeleted(true);
        productsRepository.save(products);
        return null;
    }

    private void validateProduct(ProductsDTO productsDTO) {
        productsDTO.setName(validateProductName(productsDTO.getName()));
    }

    private void validateUpdateProduct(ProductsManagementDTO productsManagementDTO) {
        productsManagementDTO.setName(validateProductName(productsManagementDTO.getName()));
    }

    private String validateProductName(String str) {
        if (DataUtil.isNullOrEmpty(str)) {
            throw new RuntimeException(Const.PRODUCTS.PROD_NAME_IS_NOT_EMPTY);
        } else {
            String name = DataUtil.replaceSpaceSolr(str);
            if(name.length() > 200){
                throw new RuntimeException(Const.PRODUCTS.PROD_NAME_MORE_THAN_200_CHAR);
            }
            Products products = productsRepository.findProductsByName(name);
            if (products != null) {
                throw new RuntimeException(Const.PRODUCTS.PROD_NAME_EXISTED);
            }else {
                return name;
            }
        }
    }
}
