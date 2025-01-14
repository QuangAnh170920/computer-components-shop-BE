package com.computercomponent.api.service.impl;

import com.computercomponent.api.common.CategoriesStatus;
import com.computercomponent.api.common.Const;
import com.computercomponent.api.dto.*;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.*;
import java.util.stream.Collectors;

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

    // Ánh xạ các danh mục con vào danh mục cha
    private void mapChildrenRecursively(List<CategoriesManagementDTO> children, CategoriesChildrenDTO parentDTO) {
        for (CategoriesManagementDTO child : children) {
            CategoriesChildrenDTO childDTO = new CategoriesChildrenDTO(
                    child.getId(),
                    child.getCode(),
                    child.getName(),
                    child.getDescription(),
                    child.getStatus(),
                    child.getParentId(),
                    new ArrayList<>()
            );

            // Thêm child vào danh sách children của parentDTO
            parentDTO.getChildren().add(childDTO);

            // Tìm các danh mục con của child
            List<CategoriesManagementDTO> grandchildren = children.stream()
                    .filter(grandchild -> grandchild.getParentId().equals(child.getId()))
                    .collect(Collectors.toList());

            // Đệ quy nếu child có danh mục con
            if (!grandchildren.isEmpty()) {
                mapChildrenRecursively(grandchildren, childDTO);
            }
        }
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

        // Lấy tất cả danh mục (không lọc theo parentId)
        List<CategoriesManagementDTO> allCategories = categoriesRepository.findAllAndSearch(
                categoriesRequest.getSearchField().trim(),
                categoriesRequest.getStatus(),
                null,
                pageRequest
        ).getContent();

        Map<Long, CategoriesManagementDTO> parentCategoriesMap = new HashMap<>();

        List<CategoriesManagementDTO> orphans = new ArrayList<>();

        for (CategoriesManagementDTO category : allCategories) {
            if (category.getParentId() == null) {
                // Danh mục không có parentId là danh mục cha
                parentCategoriesMap.put(category.getId(), category);
                category.setChildren(new ArrayList<>());
            } else {
                orphans.add(category);
            }
        }

        // Ánh xạ các danh mục con vào danh mục cha
        for (CategoriesManagementDTO child : orphans) {
            CategoriesManagementDTO parent = parentCategoriesMap.get(child.getParentId());
            if (parent != null) {
                // Tạo DTO cho danh mục con
                CategoriesChildrenDTO childDTO = new CategoriesChildrenDTO(
                        child.getId(),
                        child.getCode(),
                        child.getName(),
                        child.getDescription(),
                        child.getStatus(),
                        child.getParentId(),
                        new ArrayList<>()
                );

                // Thêm childDTO vào danh sách children của parent
                if (parent.getChildren() == null) {
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(childDTO);

                // Tìm danh mục con của childDTO và ánh xạ đệ quy
                List<CategoriesManagementDTO> childrenOfChild = orphans.stream()
                        .filter(grandchild -> grandchild.getParentId().equals(child.getId()))
                        .collect(Collectors.toList());

                if (!childrenOfChild.isEmpty()) {
                    mapChildrenRecursively(childrenOfChild, childDTO);
                }
            }
        }

        // Chuyển Map danh mục cha thành List để trả về
        List<CategoriesManagementDTO> finalCategories = new ArrayList<>(parentCategoriesMap.values());

        // Nếu bạn vẫn cần trả về dưới dạng Page, có thể bọc lại:
        return new PageImpl<>(finalCategories, pageRequest, finalCategories.size());
    }

    @Override
    public CategoriesManagementDTO getDetail(Long id) {
        // Lấy danh mục chính theo id
        CategoriesManagementDTO mainCategory = categoriesRepository.findById(id)
                .map(category -> new CategoriesManagementDTO(
                        category.getId(),
                        category.getCode(),
                        category.getName(),
                        category.getDescription(),
                        category.getStatus(),
                        category.getParentId(),
                        new ArrayList<>() // Khởi tạo danh sách children rỗng
                ))
                .orElse(null); // Trả về null nếu không tìm thấy

        if (mainCategory == null) {
            return null; // Nếu không tìm thấy danh mục chính, trả về null
        }

        // Tìm danh sách các danh mục con
        List<CategoriesManagementDTO> children = categoriesRepository.findByParentId(id);

        // Ánh xạ các danh mục con
        for (CategoriesManagementDTO child : children) {
            CategoriesChildrenDTO childDTO = new CategoriesChildrenDTO(
                    child.getId(),
                    child.getCode(),
                    child.getName(),
                    child.getDescription(),
                    child.getStatus(),
                    child.getParentId(),
                    new ArrayList<>()
            );

            // Thêm childDTO vào danh sách children của danh mục chính
            mainCategory.getChildren().add(childDTO);

            // Tìm và ánh xạ các danh mục con cho childDTO
            List<CategoriesManagementDTO> grandchildren = categoriesRepository.findByParentId(child.getId());
            mapChildrenRecursively(grandchildren, childDTO);
        }

        return mainCategory;
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

        // Kiểm tra parentId
        if (categoriesManagementDTO.getParentId() != null && !Objects.equals(categories.getParentId(), categoriesManagementDTO.getParentId())) {
            // Kiểm tra xem danh mục cha mới có tồn tại không
            if (!categoriesRepository.existsById(categoriesManagementDTO.getParentId())) {
                throw new CategoryValidationException(Const.CATEGORIES.PARENT_CATE_NOT_FOUND);
            }

            // Lấy danh sách các ID của danh mục con
            List<Long> childIds = getAllChildIds(categories.getId());

            // Kiểm tra xem parentId mới có nằm trong danh sách ID của danh mục con không
            if (childIds.contains(categoriesManagementDTO.getParentId())) {
                throw new CategoryValidationException(Const.CATEGORIES.PARENT_ID_CATE_CANNOT_BE_CHILD);
            }

            categories.setParentId(categoriesManagementDTO.getParentId());
        }

        categoriesRepository.save(categories);
        return Const.MESSAGE_CODE.SUCCESS;
    }

    // Phương thức để lấy tất cả các ID danh mục con
    private List<Long> getAllChildIds(Long parentId) {
        List<Long> childIds = new ArrayList<>();
        List<CategoriesManagementDTO> children = categoriesRepository.findByParentId(parentId);

        // Thêm các ID của danh mục con vào danh sách
        for (CategoriesManagementDTO child : children) {
            childIds.add(child.getId());
            // Gọi đệ quy để lấy các danh mục con của danh mục con
            childIds.addAll(getAllChildIds(child.getId()));
        }

        return childIds;
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

    @Override
    public List<CategoryDropListDTO> dropListExcludingChildren(Long categoryId) {
        return categoriesRepository.dropListExcludingChildren(categoryId);
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

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public static class CategoryValidationException extends RuntimeException {
        public CategoryValidationException(String message) {
            super(message);
        }
    }
}
