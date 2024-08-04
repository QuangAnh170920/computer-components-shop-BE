package com.computercomponent.api.service.impl;

import com.computercomponent.api.common.BrandStatus;
import com.computercomponent.api.common.Const;
import com.computercomponent.api.dto.BrandDTO;
import com.computercomponent.api.dto.BrandDropListDTO;
import com.computercomponent.api.dto.BrandManagementDTO;
import com.computercomponent.api.dto.BrandManagementStatusDTO;
import com.computercomponent.api.entity.Brand;
import com.computercomponent.api.repository.BrandRepository;
import com.computercomponent.api.repository.ProductsRepository;
import com.computercomponent.api.request.BrandRequest;
import com.computercomponent.api.response.BrandDetail;
import com.computercomponent.api.response.ProductDetail;
import com.computercomponent.api.response.ProductSpecificationDetail;
import com.computercomponent.api.service.BrandService;
import com.computercomponent.api.until.DataUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Override
    public String createBrand(BrandDTO brandDTO) {
        validateBrand(brandDTO);
        Brand brand = new Brand();
        BeanUtils.copyProperties(brandDTO, brand);
        brandRepository.save(brand);
        return Const.MESSAGE_CODE.SUCCESS;
    }

    @Override
    public Page<BrandManagementDTO> getBrandList(BrandRequest brandRequest) {
        PageRequest pageRequest = DataUtil.getPageable(brandRequest.getPageNumber(), brandRequest.getPageSize());
        if (brandRequest.getSearchField() == null) {
            brandRequest.setSearchField("");
        }
        if (brandRequest.getStatus() != null) {
            BrandStatus brandStatus = BrandStatus.fromValue(brandRequest.getStatus());
            Assert.notNull(brandStatus, Const.MESSAGE_CODE.STATUS_NOT_FOUND);
        }
        return brandRepository.findAllAndSearch(brandRequest.getSearchField().trim(), brandRequest.getStatus(), pageRequest);
    }

    @Override
    public String updateBrand(BrandManagementDTO brandManagementDTO) {
        Brand brand = brandRepository.findBrandById(brandManagementDTO.getId());
        Assert.isTrue(brand != null, Const.BRAND.BRAND_NOT_FOUND);

        if (brandManagementDTO.getName() != null && !Objects.equals(brand.getName(), brandManagementDTO.getName())) {
            validateUpdateBrandName(brandManagementDTO.getName());
            brand.setName(brandManagementDTO.getName());
        }

        if (brandManagementDTO.getDescription() != null && !Objects.equals(brand.getDescription(), brandManagementDTO.getDescription())) {
            validateBrandDescription(brandManagementDTO.getDescription());
            brand.setDescription(brandManagementDTO.getDescription());
        }

        if (brandManagementDTO.getCode() != null && !Objects.equals(brand.getCode(), brandManagementDTO.getCode())) {
            validateBrandCode(brandManagementDTO.getCode());
            brand.setCode(brandManagementDTO.getCode());
        }
        brandRepository.save(brand);
        return Const.MESSAGE_CODE.SUCCESS;
    }

    // cần check thêm điều kiện của Brand. Nếu trong TH Brand đã có sản phẩm dùng => không được xóa
    @Override
    public String deleteBrand(Long id) {
        Brand brand = brandRepository.findBrandById(id);
        Assert.isTrue(brand != null, Const.BRAND.BRAND_NOT_FOUND);
        long productCount = productsRepository.countProductsByBrandId(id);
        Assert.isTrue(productCount == 0, Const.BRAND.BRAND_CANNOT_DELETE);
        brand.setDeleted(true);
        brandRepository.save(brand);
        return null;
    }

    @Override
    public List<BrandDropListDTO> dropList() {
        return brandRepository.dropList();
    }

    @Override
    public BrandDetail getDetail(Long id) {
        BrandDetail brandDetail = brandRepository.getDetail(id);
        return brandDetail;
    }

    @Override
    public BrandManagementStatusDTO updateStatus(BrandManagementStatusDTO brandManagementStatusDTO) {
        Brand brand = brandRepository.findBrandById(brandManagementStatusDTO.getId());
        Assert.isTrue(brand != null, Const.BRAND.BRAND_NOT_FOUND);
        BeanUtils.copyProperties(brandManagementStatusDTO, brand);
        brandRepository.save(brand);
        return null;
    }

    private void validateBrand(BrandDTO brandDTO) {
        brandDTO.setName(validateBrandName(brandDTO.getName()));
    }

    private void validateUpdateBrand(BrandManagementDTO brandManagementDTO) {
        brandManagementDTO.setName(validateBrandName(brandManagementDTO.getName()));
    }

    private String validateBrandName(String str) {
        if (DataUtil.isNullOrEmpty(str)) {
            throw new RuntimeException(Const.BRAND.BRAND_NAME_IS_NOT_EMPTY);
        } else {
            String name = DataUtil.replaceSpaceSolr(str);
            if(name.length() > 200){
                throw new RuntimeException(Const.BRAND.BRAND_NAME_MORE_THAN_200_CHAR);
            }
            Brand brand = brandRepository.findBrandByName(name);
            if (brand != null) {
                throw new RuntimeException(Const.BRAND.BRAND_NAME_EXISTED);
            }else {
                return name;
            }
        }
    }

    private String validateUpdateBrandName(String str) {
        String name = DataUtil.replaceSpaceSolr(str);
        if(name.length() > 200){
            throw new RuntimeException(Const.BRAND.BRAND_NAME_MORE_THAN_200_CHAR);
        }
        Brand brand = brandRepository.findBrandByName(name);
        if (brand != null) {
            throw new RuntimeException(Const.BRAND.BRAND_NAME_EXISTED);
        }else {
            return name;
        }
    }

    private String validateBrandCode(String str) {
        String code = DataUtil.replaceSpaceSolr(str);
        if(code.length() > 200){
            throw new RuntimeException(Const.BRAND.BRAND_CODE_MORE_THAN_200_CHAR);
        }
        Brand brand = brandRepository.findBrandByCode(code);
        if (brand != null) {
            throw new RuntimeException(Const.BRAND.BRAND_CODE_EXISTED);
        }else {
            return code;
        }
    }

    private void validateBrandDescription(String description) {
        Assert.isTrue(description == null || description.length() <= 255, Const.BRAND.INVALID_DESCRIPTION_LENGTH);
    }
}
