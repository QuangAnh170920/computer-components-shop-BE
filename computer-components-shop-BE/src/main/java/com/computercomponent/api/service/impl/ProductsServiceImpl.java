package com.computercomponent.api.service.impl;

import com.computercomponent.api.common.Const;
import com.computercomponent.api.common.ProductsStatus;
import com.computercomponent.api.dto.*;
import com.computercomponent.api.entity.ProductFeatures;
import com.computercomponent.api.entity.Products;
import com.computercomponent.api.entity.Promotion;
import com.computercomponent.api.repository.ProductFeaturesRepository;
import com.computercomponent.api.repository.ProductsRepository;
import com.computercomponent.api.repository.PromotionRepository;
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
import java.math.RoundingMode;
import java.util.List;

@Service
public class ProductsServiceImpl implements ProductsService {
    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ProductFeaturesRepository productFeaturesRepository;

    @Autowired
    private PromotionRepository promotionRepository;


    @Override
    public String createProduct(ProductsDTO productsDTO) {
        validateProduct(productsDTO); // Validate sản phẩm trước khi lưu

        // Tạo đối tượng Products từ ProductsDTO
        Products products = new Products();
        BeanUtils.copyProperties(productsDTO, products);

        // Thêm chữ "W" vào sau giá trị power nếu có
        if (productsDTO.getPower() != null) {
            products.setPower(productsDTO.getPower() + "W");
        }

        // Nếu có promotionId, tính toán finalTotalPrice
        if (productsDTO.getPromotionId() != null) {
            // Tìm thông tin khuyến mãi dựa trên promotionId
            Promotion promotion = promotionRepository.findById(productsDTO.getPromotionId())
                    .orElseThrow(() -> new RuntimeException(Const.PROMOTION.PROMOTION_NOT_FOUND));

            // Tính giá sau khi giảm (finalTotalPrice = price - (price * discount / 100))
            BigDecimal discountAmount = productsDTO.getPrice()
                    .multiply(BigDecimal.valueOf(promotion.getDiscountPercentage()))
                    .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);

            BigDecimal finalPrice = productsDTO.getPrice().subtract(discountAmount);

            products.setFinalTotalPrice(finalPrice); // Set giá sau khi áp dụng khuyến mãi
        } else {
            products.setFinalTotalPrice(productsDTO.getPrice()); // Nếu không có khuyến mãi, finalTotalPrice là giá gốc
        }

        // Lưu sản phẩm vào bảng 'Products'
        Products savedProduct = productsRepository.save(products);

        // Lưu các tính năng vào bảng 'ProductFeatures'
        if (productsDTO.getProductFeatures() != null) {
            for (ProductFeaturesDTO featureDTO : productsDTO.getProductFeatures()) {
                ProductFeatures feature = new ProductFeatures();
                feature.setProductId(savedProduct.getId()); // Lấy ID của sản phẩm vừa lưu
                feature.setFeature(featureDTO.getFeature());
                feature.setPriority(featureDTO.getPriority());
                productFeaturesRepository.save(feature); // Lưu vào bảng 'ProductFeatures'
            }
        }

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

    @Override
    public ProductUpdateRequestDTO updateProduct(ProductUpdateRequestDTO productUpdateRequestDTO) {
        // Kiểm tra xem sản phẩm có tồn tại không
        Products products = productsRepository.findProductsById(productUpdateRequestDTO.getId());
        Assert.isTrue(products != null, Const.PRODUCTS.PROD_NOT_FOUND);

        // Validate sản phẩm trước khi cập nhật
        validateUpdateProduct(productUpdateRequestDTO);

        // Thêm chữ "W" vào sau giá trị power nếu có
        if (productUpdateRequestDTO.getPower() != null) {
            products.setPower(productUpdateRequestDTO.getPower() + "W");
        }

        // Nếu có promotionId, tính toán finalTotalPrice
        if (productUpdateRequestDTO.getPromotionId() != null) {
            // Tìm thông tin khuyến mãi dựa trên promotionId
            Promotion promotion = promotionRepository.findById(productUpdateRequestDTO.getPromotionId())
                    .orElseThrow(() -> new RuntimeException(Const.PROMOTION.PROMOTION_NOT_FOUND));

            // Tính giá sau khi giảm (finalTotalPrice = price - (price * discount / 100))
            BigDecimal discountAmount = products.getPrice()
                    .multiply(BigDecimal.valueOf(promotion.getDiscountPercentage()))
                    .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);

            BigDecimal finalPrice = products.getPrice().subtract(discountAmount);
            products.setFinalTotalPrice(finalPrice); // Set giá sau khi áp dụng khuyến mãi
        } else {
            products.setFinalTotalPrice(products.getPrice()); // Nếu không có khuyến mãi, finalTotalPrice là giá gốc
        }

        // Lưu sản phẩm vào bảng 'Products'
        productsRepository.save(products);

        // Lưu các tính năng vào bảng 'ProductFeatures'
        if (productUpdateRequestDTO.getProductFeatures() != null) {
            // Xóa các tính năng cũ trước khi thêm mới
            productFeaturesRepository.deleteByProductId(products.getId());

            // Thêm mới các tính năng
            for (ProductFeaturesDTO featureDTO : productUpdateRequestDTO.getProductFeatures()) {
                ProductFeatures feature = new ProductFeatures();
                feature.setProductId(products.getId()); // Lấy ID của sản phẩm vừa cập nhật
                feature.setFeature(featureDTO.getFeature());
                feature.setPriority(featureDTO.getPriority());
                productFeaturesRepository.save(feature); // Lưu vào bảng 'ProductFeatures'
            }
        }

        return productUpdateRequestDTO; // Trả về đối tượng DTO sau khi cập nhật
    }

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
        ProductDetail detail = productsRepository.getDetail(id);
        List<ProductFeaturesDTO> features = productsRepository.getProductFeatures(id);
        detail.setProductFeatures(features);
        return detail;
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

        // Kiểm tra power
        if (productsDTO.getPower() != null) {
            try {
                int powerValue = Integer.parseInt(productsDTO.getPower()); // Chuyển chuỗi thành số nguyên
                Assert.isTrue(powerValue >= 0, Const.PRODUCTS.PROD_POWER_MUST_BE_GREATER_THAN_OR_EQUAL_TO_ZERO);
            } catch (NumberFormatException e) {
                throw new RuntimeException(Const.PRODUCTS.PROD_POWER_MUST_BE_A_NUMBER); // Nếu power không phải là số hợp lệ
            }
        }
    }

    private void validateUpdateProduct(ProductUpdateRequestDTO productUpdateRequestDTO) {
        productUpdateRequestDTO.setName(validateProductName(productUpdateRequestDTO.getName()));
        productUpdateRequestDTO.setCode(validateProductCode(productUpdateRequestDTO.getCode()));
        Assert.notNull(productUpdateRequestDTO.getPrice(), Const.PRODUCTS.PROD_PRICE_IS_NOT_EMPTY);
        Assert.isTrue(productUpdateRequestDTO.getPrice().compareTo(BigDecimal.ZERO) >= 0, Const.PRODUCTS.PROD_PRICE_MUST_BE_GREATER_THAN_OR_EQUAL_TO_ZERO);
        // Kiểm tra power
        if (productUpdateRequestDTO.getPower() != null) {
            try {
                int powerValue = Integer.parseInt(productUpdateRequestDTO.getPower()); // Chuyển chuỗi thành số nguyên
                Assert.isTrue(powerValue >= 0, Const.PRODUCTS.PROD_POWER_MUST_BE_GREATER_THAN_OR_EQUAL_TO_ZERO);
            } catch (NumberFormatException e) {
                throw new RuntimeException(Const.PRODUCTS.PROD_POWER_MUST_BE_A_NUMBER); // Nếu power không phải là số hợp lệ
            }
        }
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
