package com.computercomponent.api.service.impl;

import com.computercomponent.api.common.Const;
import com.computercomponent.api.common.ProductsStatus;
import com.computercomponent.api.dto.ProductImageDTO;
import com.computercomponent.api.dto.ProductImageManagementDTO;
import com.computercomponent.api.entity.ProductImage;
import com.computercomponent.api.entity.Products;
import com.computercomponent.api.repository.ProductImageRepository;
import com.computercomponent.api.request.ProductImageRequest;
import com.computercomponent.api.service.ProductImageService;
import com.computercomponent.api.until.DataUtil;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductImageServiceImpl implements ProductImageService {
    @Autowired
    private ProductImageRepository productImageRepository;

    @Override
    public String createProductImage(ProductImageDTO productImageDTO) {
        validateProductImage(productImageDTO);
        ProductImage productImage = new ProductImage();
        BeanUtils.copyProperties(productImageDTO, productImage);
        productImageRepository.save(productImage);
        return Const.MESSAGE_CODE.SUCCESS;
    }

    @Override
    public Page<ProductImageManagementDTO> getProductImageList(ProductImageRequest productImageRequest) {
        PageRequest pageRequest = DataUtil.getPageable(productImageRequest.getPageNumber(), productImageRequest.getPageSize());
        if (productImageRequest.getSearchField() == null) {
            productImageRequest.setSearchField("");
        }
        if (productImageRequest.getStatus() != null) {
            ProductsStatus status = ProductsStatus.fromValue(productImageRequest.getStatus());
            Assert.notNull(status, Const.MESSAGE_CODE.STATUS_NOT_FOUND);
        }
        return productImageRepository.findAllAndSearch(productImageRequest.getSearchField().trim(), productImageRequest.getStatus(), pageRequest);
    }

    // cần viết và check update status
    @Override
    public ProductImageManagementDTO updateProductImage(ProductImageManagementDTO productImageManagementDTO) {
        ProductImage productImage = productImageRepository.findProductImageById(productImageManagementDTO.getId());
        Assert.isTrue(productImage != null, Const.PRODUCT_IMAGE.PRODUCT_IMAGE_NOT_FOUND);
        validateUpdateProductImage(productImageManagementDTO);
        BeanUtils.copyProperties(productImageManagementDTO, productImage);
        productImageRepository.save(productImage);
        return null;
    }

    @Override
    public String deleteProductImage(Long id) {
        ProductImage productImage = productImageRepository.findProductImageById(id);
        Assert.isTrue(productImage != null, Const.PRODUCT_IMAGE.PRODUCT_IMAGE_NOT_FOUND);
        productImage.setDeleted(true);
        productImageRepository.save(productImage);
        return null;
    }

    private void validateProductImage(ProductImageDTO productImageDTO) {
        productImageDTO.setImageUrl(validateProductImageUrl(productImageDTO.getImageUrl()));
    }

    private void validateUpdateProductImage(ProductImageManagementDTO productImageManagementDTO) {
        productImageManagementDTO.setImageUrl(validateProductImageUrl(productImageManagementDTO.getImageUrl()));
    }

    private String validateProductImageUrl(String str) {
        if (DataUtil.isNullOrEmpty(str)) {
            throw new RuntimeException(Const.PRODUCT_IMAGE.PRODUCT_IMAGE_URL_IS_NOT_EMPTY);
        }
        return str;
    }
}
