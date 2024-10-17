package com.computercomponent.api.service.impl;

import com.computercomponent.api.common.CategoriesStatus;
import com.computercomponent.api.common.Const;
import com.computercomponent.api.dto.CategoriesDTO;
import com.computercomponent.api.dto.CategoriesManagementDTO;
import com.computercomponent.api.dto.CategoriesManagementStatusDTO;
import com.computercomponent.api.dto.CategoryDropListDTO;
import com.computercomponent.api.entity.Categories;
import com.computercomponent.api.repository.CategoriesRepository;
import com.computercomponent.api.repository.ProductsRepository;
import com.computercomponent.api.request.CategoriesRequest;
import com.computercomponent.api.response.CategoriesDetail;
import com.computercomponent.api.service.CategoriesService;
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
public class CategoriesServiceImpl implements CategoriesService {
    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Override
    public String createCate(CategoriesDTO categoriesDTO) {
        validateCreateCate(categoriesDTO);
        Categories categories = new Categories();
        BeanUtils.copyProperties(categoriesDTO, categories);
        categoriesRepository.save(categories);
        return Const.MESSAGE_CODE.SUCCESS;
    }

    @Override
    public Page<CategoriesManagementDTO> getCateList(CategoriesRequest categoriesRequest) {
        PageRequest pageRequest = DataUtil.getPageable(categoriesRequest.getPageNumber(), categoriesRequest.getPageSize());
        if (categoriesRequest.getSearchField() == null) {
            categoriesRequest.setSearchField("");
        }
        if (categoriesRequest.getStatus() != null) {
            CategoriesStatus categoriesStatus = CategoriesStatus.fromValue(categoriesRequest.getStatus());
            Assert.notNull(categoriesStatus, Const.MESSAGE_CODE.STATUS_NOT_FOUND);
        }
        return categoriesRepository.findAllAndSearch(categoriesRequest.getSearchField().trim(), categoriesRequest.getStatus(), categoriesRequest.getParentId(), pageRequest);
    }

    // cần viết và check update status của cate
    @Override
    public String updateCate(CategoriesManagementDTO categoriesManagementDTO) {
        Categories categories = categoriesRepository.findCategoriesById(categoriesManagementDTO.getId());
        Assert.isTrue(categories != null, Const.CATEGORIES.CATE_NOT_FOUND);

        if (categoriesManagementDTO.getName() != null && !Objects.equals(categories.getName(), categoriesManagementDTO.getName())) {
            validateUpdateCateName(categoriesManagementDTO.getName());
            categories.setName(categoriesManagementDTO.getName());
        }

        if (categoriesManagementDTO.getDescription() != null && !Objects.equals(categories.getDescription(), categoriesManagementDTO.getDescription())) {
            validateCateDescription(categoriesManagementDTO.getDescription());
            categories.setDescription(categoriesManagementDTO.getDescription());
        }

        if (categoriesManagementDTO.getCode() != null && !Objects.equals(categories.getCode(), categoriesManagementDTO.getCode())) {
            validateCateCode(categoriesManagementDTO.getCode());
            categories.setCode(categoriesManagementDTO.getCode());
        }

        if (categoriesManagementDTO.getParentId() != null && !Objects.equals(categories.getParentId(), categoriesManagementDTO.getParentId())) {
            if (!categoriesRepository.existsById(categoriesManagementDTO.getParentId())) {
                throw new CategoryValidationException(Const.CATEGORIES.PARENT_CATE_NOT_FOUND);
            }
            categories.setCode(categoriesManagementDTO.getCode());
        }
        categoriesRepository.save(categories);
        return Const.MESSAGE_CODE.SUCCESS;
    }

    // cần check thêm điều kiện của Cate. Nếu trong TH Cate đã có sản phẩm dùng => không được xóa
    @Override
    public String deleteCate(Long id) {
        Categories categories = categoriesRepository.findCategoriesById(id);
        Assert.isTrue(categories != null, Const.CATEGORIES.CATE_NOT_FOUND);
        boolean hasProducts = productsRepository.existsByCategoryId(id);
        if (hasProducts) {
            throw new CategoryValidationException(Const.CATEGORIES.CATE_HAS_PRODUCTS);
        }
        categories.setDeleted(true);
        categoriesRepository.save(categories);
        return null;
    }

    @Override
    public List<CategoryDropListDTO> dropList() {
        return categoriesRepository.dropList();
    }

    @Override
    public CategoriesManagementStatusDTO updateStatus(CategoriesManagementStatusDTO categoriesManagementStatusDTO) {
        Categories categories = categoriesRepository.findCategoriesById(categoriesManagementStatusDTO.getId());
        Assert.isTrue(categories != null, Const.CATEGORIES.CATE_NOT_FOUND);
        if (categoriesManagementStatusDTO.getStatus() == null) {
            throw new IllegalArgumentException(Const.CATEGORIES.CATE_STATUS_IS_NOT_VALID);
        }
        categories.setStatus(categoriesManagementStatusDTO.getStatus());
        if (categories.getStatus() == CategoriesStatus.DEACTIVATE && productsRepository.existsByCategoryId(categories.getId())) {
            throw new CategoryValidationException(Const.CATEGORIES.CATE_HAS_PRODUCTS);
        }
        categoriesRepository.save(categories);
        return new CategoriesManagementStatusDTO(categories.getId(), categories.getStatus());
    }

    private void validateCreateCate(CategoriesDTO categoriesDTO) {
        categoriesDTO.setName(validateCateName(categoriesDTO.getName()));
        categoriesDTO.setCode(validateCateCode(categoriesDTO.getCode()));
        if (categoriesDTO.getParentId() != null && !categoriesRepository.existsById(categoriesDTO.getParentId())) {
            throw new CategoryValidationException(Const.CATEGORIES.PARENT_CATE_NOT_FOUND);
        }
    }

    private String validateCateName(String str) {
        if (DataUtil.isNullOrEmpty(str)) {
            throw new CategoryValidationException(Const.CATEGORIES.CATE_NAME_IS_NOT_EMPTY);
        } else {
            String name = DataUtil.replaceSpaceSolr(str);
            if(name.length() > 200){
                throw new CategoryValidationException(Const.CATEGORIES.CATE_NAME_MORE_THAN_200_CHAR);
            }
            Categories categories = categoriesRepository.findCategoriesByName(name);
            if (categories != null) {
                throw new CategoryValidationException(Const.CATEGORIES.CATE_NAME_EXISTED);
            }else {
                return name;
            }
        }
    }

    private String validateUpdateCateName(String str) {
        String name = DataUtil.replaceSpaceSolr(str);
        if(name.length() > 200){
            throw new RuntimeException(Const.BRAND.BRAND_NAME_MORE_THAN_200_CHAR);
        }
        Categories categories = categoriesRepository.findCategoriesByName(name);
        if (categories != null) {
            throw new RuntimeException(Const.BRAND.BRAND_NAME_EXISTED);
        }else {
            return name;
        }
    }

    private String validateCateCode(String str) {
        String code = DataUtil.replaceSpaceSolr(str);
        if(code.length() > 200){
            throw new CategoryValidationException(Const.CATEGORIES.CATE_CODE_MORE_THAN_200_CHAR);
        }
        Categories categories = categoriesRepository.findCategoriesByCode(code);
        if (categories != null) {
            throw new CategoryValidationException(Const.CATEGORIES.CATE_CODE_EXISTED);
        }else {
            return code;
        }
    }

    private void validateCateDescription(String description) {
        Assert.isTrue(description == null || description.length() <= 255, Const.CATEGORIES.INVALID_DESCRIPTION_LENGTH);
    }

    public static class CategoryValidationException extends RuntimeException {
        public CategoryValidationException(String message) {
            super(message);
        }
    }
}
