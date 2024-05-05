package com.computercomponent.api.service.impl;

import com.computercomponent.api.common.Const;
import com.computercomponent.api.dto.BrandDTO;
import com.computercomponent.api.dto.BrandManagementDTO;
import com.computercomponent.api.dto.CategoriesDTO;
import com.computercomponent.api.dto.CategoriesManagementDTO;
import com.computercomponent.api.entity.Brand;
import com.computercomponent.api.entity.Categories;
import com.computercomponent.api.repository.CategoriesRepository;
import com.computercomponent.api.request.CategoriesRequest;
import com.computercomponent.api.service.CategoriesService;
import com.computercomponent.api.until.DataUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class CategoriesServiceImpl implements CategoriesService {
    @Autowired
    private CategoriesRepository categoriesRepository;

    @Override
    public String createCate(CategoriesDTO categoriesDTO) {
        validateCate(categoriesDTO);
        Categories categories = new Categories();
        BeanUtils.copyProperties(categoriesDTO, categories);
        categoriesRepository.save(categories);
        return Const.MESSAGE_CODE.SUCCESS;
    }

    @Override
    public Page<CategoriesManagementDTO> getCateList(CategoriesRequest categoriesRequest) {
        PageRequest pageRequest = DataUtil.getPageable(categoriesRequest.getPageNumber(), categoriesRequest.getPageSize());
        if (categoriesRequest.getName() == null) {
            categoriesRequest.setName("");
        }
        return categoriesRepository.findAllAndSearch(categoriesRequest.getName().trim(), pageRequest);
    }

    // cần viết và check update status của cate
    @Override
    public CategoriesManagementDTO updateCate(CategoriesManagementDTO categoriesManagementDTO) {
        Categories categories = categoriesRepository.findCategoriesById(categoriesManagementDTO.getId());
        Assert.isTrue(categories != null, Const.CATEGORIES.CATE_NOT_FOUND);
        validateUpdateCate(categoriesManagementDTO);
        BeanUtils.copyProperties(categoriesManagementDTO, categories);
        categoriesRepository.save(categories);
        return null;
    }

    // cần check thêm điều kiện của Cate. Nếu trong TH Cate đã có sản phẩm dùng => không được xóa
    @Override
    public String deleteCate(Long id) {
        Categories categories = categoriesRepository.findCategoriesById(id);
        Assert.isTrue(categories != null, Const.CATEGORIES.CATE_NOT_FOUND);
        categories.setDeleted(true);
        categoriesRepository.save(categories);
        return null;
    }

    private void validateCate(CategoriesDTO categoriesDTO) {
        categoriesDTO.setName(validateCateName(categoriesDTO.getName()));
    }

    private void validateUpdateCate(CategoriesManagementDTO categoriesManagementDTO) {
        categoriesManagementDTO.setName(validateCateName(categoriesManagementDTO.getName()));
    }

    private String validateCateName(String str) {
        if (DataUtil.isNullOrEmpty(str)) {
            throw new RuntimeException(Const.CATEGORIES.CATE_NAME_IS_NOT_EMPTY);
        } else {
            String name = DataUtil.replaceSpaceSolr(str);
            if(name.length() > 200){
                throw new RuntimeException(Const.CATEGORIES.CATE_NAME_MORE_THAN_200_CHAR);
            }
            Categories categories = categoriesRepository.findCategoriesByName(name);
            if (categories != null) {
                throw new RuntimeException(Const.CATEGORIES.CATE_NAME_EXISTED);
            }else {
                return name;
            }
        }
    }
}
