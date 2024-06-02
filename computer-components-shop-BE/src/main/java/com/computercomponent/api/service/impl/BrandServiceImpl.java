package com.computercomponent.api.service.impl;

import com.computercomponent.api.common.BrandStatus;
import com.computercomponent.api.common.Const;
import com.computercomponent.api.dto.BrandDTO;
import com.computercomponent.api.dto.BrandDropListDTO;
import com.computercomponent.api.dto.BrandManagementDTO;
import com.computercomponent.api.entity.Brand;
import com.computercomponent.api.repository.BrandRepository;
import com.computercomponent.api.request.BrandRequest;
import com.computercomponent.api.service.BrandService;
import com.computercomponent.api.until.DataUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;

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

    // cần viết và check update status của brand
    @Override
    public BrandManagementDTO updateBrand(BrandManagementDTO brandManagementDTO) {
        Brand brand = brandRepository.findBrandById(brandManagementDTO.getId());
        Assert.isTrue(brand != null, Const.BRAND.BRAND_NOT_FOUND);
        validateUpdateBrand(brandManagementDTO);
        BeanUtils.copyProperties(brandManagementDTO, brand);
        brandRepository.save(brand);
        return null;
    }

    // cần check thêm điều kiện của Brand. Nếu trong TH Brand đã có sản phẩm dùng => không được xóa
    @Override
    public String deleteBrand(Long id) {
        Brand brand = brandRepository.findBrandById(id);
        Assert.isTrue(brand != null, Const.BRAND.BRAND_NOT_FOUND);
        brand.setDeleted(true);
        brandRepository.save(brand);
        return null;
    }

    @Override
    public List<BrandDropListDTO> dropList() {
        return brandRepository.dropList();
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
}
