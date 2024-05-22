package com.computercomponent.api.service.impl;

import com.computercomponent.api.common.Const;
import com.computercomponent.api.common.ProductsStatus;
import com.computercomponent.api.dto.ProductSpecificationsDTO;
import com.computercomponent.api.dto.ProductSpecificationsManagementDTO;
import com.computercomponent.api.dto.ProductSpecificationsUpdateRequestDTO;
import com.computercomponent.api.entity.ProductSpecifications;
import com.computercomponent.api.repository.ProductSpecificationsRepository;
import com.computercomponent.api.request.ProductSpecificationsRequest;
import com.computercomponent.api.service.ProductSpecificationsService;
import com.computercomponent.api.until.DataUtil;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductSpecificationsServiceImpl implements ProductSpecificationsService {
    @Autowired
    private ProductSpecificationsRepository productSpecificationsRepository;

    // cần check thêm vaildate các trường còn lại
    @Override
    public String createProductSpec(ProductSpecificationsDTO productSpecificationsDTO) {
        validateProductSpec(productSpecificationsDTO);
        ProductSpecifications productSpecifications = new ProductSpecifications();
        BeanUtils.copyProperties(productSpecificationsDTO, productSpecifications);
        productSpecificationsRepository.save(productSpecifications);
        return Const.MESSAGE_CODE.SUCCESS;
    }

    @Override
    public Page<ProductSpecificationsManagementDTO> getProductSpecList(ProductSpecificationsRequest productSpecificationsRequest) {
        PageRequest pageRequest = DataUtil.getPageable(productSpecificationsRequest.getPageNumber(), productSpecificationsRequest.getPageSize());
        if (productSpecificationsRequest.getSearchField() == null) {
            productSpecificationsRequest.setSearchField("");
        }
        if (productSpecificationsRequest.getStatus() != null) {
            ProductsStatus status = ProductsStatus.fromValue(productSpecificationsRequest.getStatus());
            Assert.notNull(status, Const.MESSAGE_CODE.STATUS_NOT_FOUND);
        }
        return productSpecificationsRepository.findAllAndSearch(productSpecificationsRequest.getSearchField().trim(), productSpecificationsRequest.getStatus(), pageRequest);
    }

    // cần viết và check update status và các trường còn lại
    @Override
    public ProductSpecificationsUpdateRequestDTO updateProductSpec(ProductSpecificationsUpdateRequestDTO productSpecificationsUpdateRequestDTO) {
        ProductSpecifications productSpecifications = productSpecificationsRepository.findProductSpecificationsById(productSpecificationsUpdateRequestDTO.getId());
        Assert.isTrue(productSpecifications != null, Const.PRODUCT_SPEC.PRODUCT_SPEC_NOT_FOUND);
        validateUpdateProductSpec(productSpecificationsUpdateRequestDTO);
        BeanUtils.copyProperties(productSpecificationsUpdateRequestDTO, productSpecifications);
        productSpecificationsRepository.save(productSpecifications);
        return null;
    }

    @Override
    public String deleteProductSpec(Long id) {
        ProductSpecifications productSpecifications = productSpecificationsRepository.findProductSpecificationsById(id);
        Assert.isTrue(productSpecifications != null, Const.PRODUCT_SPEC.PRODUCT_SPEC_NOT_FOUND);
        productSpecifications.setDeleted(true);
        productSpecificationsRepository.save(productSpecifications);
        return null;
    }

    private void validateProductSpec(ProductSpecificationsDTO productSpecificationsDTO) {
        productSpecificationsDTO.setName(validateProductSpecName(productSpecificationsDTO.getName()));
    }

    private void validateUpdateProductSpec(ProductSpecificationsUpdateRequestDTO productSpecificationsUpdateRequestDTO) {
        productSpecificationsUpdateRequestDTO.setName(validateProductSpecName(productSpecificationsUpdateRequestDTO.getName()));
    }

    private String validateProductSpecName(String str) {
        if (DataUtil.isNullOrEmpty(str)) {
            throw new RuntimeException(Const.PRODUCT_SPEC.PRODUCT_SPEC_NAME_IS_NOT_EMPTY);
        } else {
            String name = DataUtil.replaceSpaceSolr(str);
            if(name.length() > 200){
                throw new RuntimeException(Const.PRODUCT_SPEC.PRODUCT_SPEC_NAME_MORE_THAN_200_CHAR);
            }
            ProductSpecifications productSpecifications = productSpecificationsRepository.findProductSpecificationsByName(name);
            if (productSpecifications != null) {
                throw new RuntimeException(Const.PRODUCT_SPEC.PRODUCT_SPEC_NAME_EXISTED);
            }else {
                return name;
            }
        }
    }
}
