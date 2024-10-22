package com.computercomponent.api.service.impl;

import com.computercomponent.api.common.Const;
import com.computercomponent.api.common.ProductsStatus;
import com.computercomponent.api.dto.*;
import com.computercomponent.api.entity.Products;
import com.computercomponent.api.repository.ProductsRepository;
import com.computercomponent.api.request.ProductsRequest;
import com.computercomponent.api.response.ProductDetail;
import com.computercomponent.api.service.ProductsService;
import com.computercomponent.api.until.DataUtil;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductsServiceImpl implements ProductsService {
    @Autowired
    private ProductsRepository productsRepository;

    //check tính toán discountAmount và finalTotalPrice
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

    // cần viết và check update status, check tính toán discountAmount và finalTotalPrice
    @Override
    public ProductUpdateRequestDTO updateProduct(ProductUpdateRequestDTO productUpdateRequestDTO) {
        Products products = productsRepository.findProductsById(productUpdateRequestDTO.getId());
        Assert.isTrue(products != null, Const.PRODUCTS.PROD_NOT_FOUND);
        validateUpdateProduct(productUpdateRequestDTO);
        BeanUtils.copyProperties(productUpdateRequestDTO, products);
        productsRepository.save(products);
        return null;
    }

    // cần check thêm điều kiện của Prod. Nếu trong TH Prod đã có thông tin chi tiết, hình ảnh => không được xóa
    @Override
    public String deleteProduct(Long id) {
        Products products = productsRepository.findProductsById(id);
        Assert.isTrue(products != null, Const.PRODUCTS.PROD_NOT_FOUND);
        products.setDeleted(true);
        productsRepository.save(products);
        return null;
    }

    @Override
    public ProductDetail getDetail(Long id) {
        ProductDetail productDetail = productsRepository.getDetail(id);
        return productDetail;
    }

    @Override
    public List<ProductDropListDTO> dropList() {
        return productsRepository.dropList();
    }

    @Override
    public Page<ProductQuantityDTO> getProductsQuantityList(ProductsRequest productsRequest) {
        PageRequest pageRequest = DataUtil.getPageable(productsRequest.getPageNumber(), productsRequest.getPageSize());
        if (productsRequest.getSearchField() == null) {
            productsRequest.setSearchField("");
        }
        if (productsRequest.getStatus() != null) {
            ProductsStatus status = ProductsStatus.fromValue(productsRequest.getStatus());
            Assert.notNull(status, Const.MESSAGE_CODE.STATUS_NOT_FOUND);
        }
        return productsRepository.getProductQuantityList(productsRequest.getSearchField().trim(), productsRequest.getStatus(), pageRequest);
    }

    private void validateProduct(ProductsDTO productsDTO) {
        productsDTO.setName(validateProductName(productsDTO.getName()));
        productsDTO.setCode(validateProductCode(productsDTO.getCode()));
        // Kiểm tra giá sản phẩm
        Assert.notNull(productsDTO.getPrice(), Const.PRODUCTS.PROD_PRICE_IS_NOT_EMPTY);
        Assert.isTrue(productsDTO.getPrice().compareTo(BigDecimal.ZERO) >= 0, Const.PRODUCTS.PROD_PRICE_MUST_BE_GREATER_THAN_OR_EQUAL_TO_ZERO);
        // Kiểm tra số lượng
        Assert.isTrue(productsDTO.getQuantityAvailable() >= 0, Const.PRODUCTS.PROD_QUANTITY_AVAILABLE_MUST_BE_GREATER_THAN_OR_EQUAL_TO_ZERO);
    }

    private void validateUpdateProduct(ProductUpdateRequestDTO productUpdateRequestDTO) {
        productUpdateRequestDTO.setName(validateProductName(productUpdateRequestDTO.getName()));
        Assert.notNull(productUpdateRequestDTO.getPrice(), Const.PRODUCTS.PROD_PRICE_IS_NOT_EMPTY);
        Assert.isTrue(productUpdateRequestDTO.getPrice().compareTo(BigDecimal.ZERO) >= 0, Const.PRODUCTS.PROD_PRICE_MUST_BE_GREATER_THAN_OR_EQUAL_TO_ZERO);
        Assert.isTrue(productUpdateRequestDTO.getQuantityAvailable() >= 0, Const.PRODUCTS.PROD_QUANTITY_AVAILABLE_MUST_BE_GREATER_THAN_OR_EQUAL_TO_ZERO);
        Assert.notNull(productUpdateRequestDTO.getStatus(), Const.PRODUCTS.PROD_STATUS_IS_NOT_EMPTY);
        Assert.isTrue(productUpdateRequestDTO.getDiscountPercentage() >= 0 && productUpdateRequestDTO.getDiscountPercentage() <= 100, Const.PRODUCTS.PROD_DISCOUNT_PERCENTAGE_MUST_BE_BETWEEN_0_AND_100);
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

    private String validateProductCode(String str) {
        if (DataUtil.isNullOrEmpty(str)) {
            throw new RuntimeException(Const.PRODUCTS.PROD_CODE_IS_NOT_EMPTY);
        } else {
            String code = DataUtil.replaceSpaceSolr(str);

            // Kiểm tra độ dài tối thiểu
            if (code.length() < 3) {
                throw new RuntimeException(Const.PRODUCTS.PROD_CODE_MIN_LENGTH);
            }

            // Kiểm tra độ dài tối đa
            if (code.length() > 200) {
                throw new RuntimeException(Const.PRODUCTS.PROD_CODE_MAX_LENGTH);
            }

            Products products = productsRepository.findProductsByCode(code);
            if (products != null) {
                throw new RuntimeException(Const.PRODUCTS.PROD_CODE_EXISTED);
            } else {
                return code;
            }
        }
    }
}
