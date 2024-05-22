package com.computercomponent.api.common;

public class Const {
    public static class VALIDATE_INPUT {
        public static final String regexEmail = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        public static final String regexPhone = "^[0-9]{10}$";
        public static final String regexPass = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{6,255}$";
        public static final String regexDate = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$";
        public static final String regexImageFile = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";
    }

    public static class RESET_PASSWORD {
        public static final String DEFAULT_RESET_PASSWORD = "ariba@2024";
    }

    public static class MESSAGE_CODE {
        public static final String OK = "OK";
        public static final String INPUT_NOT_CORRECT = "Input data incorrect";
        public static final String FULL_NAME_IS_EMPTY = "FullName not empty";
        public static final String INVALID_EMAIL = "Email invalid";

        public static final String OTP_EXPIRED = "OTP expired";

        public static final String OTP_5_TIMES = "OTP will expire after 5 minutes";

        public static final String OTP_INCORRECT = "OTP incorrect";

        public static final String ACCOUNT_DISABLED = "Account is disabled";

        public static final String INVALID_CREDENTIALS = "Invalid credentials";
        public static final String EMAIL_EXISTED = "Email existed";
        public static final String EMAIL_NOT_REGISTER = "Email not register";
        public static final String ACCOUNT_EXISTED = "Account existed";
        public static final String INVALID_MOBILE = "Invalid mobile";
        public static final String CONFIRMING_PASS_NOT_MATCH = "Confirm password not match";
        public static final String MOBILE_EXISTED = "Mobile existed";
        public static final String TOKEN_INVALID = "Token invalid";
        public static final String PARAM_NOT_EMPTY = "Field required not empty";
        public static final String ACCOUNT_ALREADY_ACTIVATED = "Account already activated";
        public static final String DATA_LENGTH_INVALID = "Data length inalid";
        public static final String SUCCESS = "Success";
        public static final String ERROR = "Fail";
        public static final String EMAIL_MORE_THAN_255_CHAR = "Email no more than 255 character";
        // validate fullname
        public static final String FULL_NAME_MORE_THAN_255_CHAR = "FullName no more than 255 character";

        public static final String USER_NOT_FOUND = "User Not found";
        public static final String WRONG_PASSWORD = "wrong password";

        public static final String DATE_OF_BIRTH_INVALID = "Date of birth invalid";

        public static final String PAGE_NUMBER_AND_PAGE_SIZE_START_AT_1 = "Pagenumber và Pagesize không được nhỏ hơn 1";
        public static final String QUOTE_TEMPLATE_NOT_FOUND = "Quote template not found";
        public static final String CONTRACTS_TEMPLATE_NOT_FOUND = "Contracts template not found";
        public static final String PR_NOT_FOUND = "PR not found";
        public static final String STATUS_NOT_FOUND = "status not found";
        public static final String DATE_INVALID ="from date - to date invalid";
        public static final String MEDIA_TYPE_NOT_FOUND = "Media not found";
        public static final String TOKEN_DEVICE_NOTFOUND = "Token device not found";
        public static final String SEND_MESSAGE_FAIL = "Send message fail";
        public static final String ID_PR_INVALID = "PR ID invalid";
        public static final String MEDIA_TYPE_INVALID = "media type invalid";
        public static final String MESSAGE_CONTENT_NOT_EMPTY = "Message content cannot be empty";
        public static final String VENDOR_NOT_FOUND = "Vendor not found";
        public static final String RANK_IS_EMPTY = "Rank not empty";
        public static final String RANK_START_AT_0_AND_END_AT_10 = "Rank không được nhỏ hơn 0 và lớn hơn 10";
        public static final String DEPARTMENT_INVALID = "Department invalid";
        public static final String DEPARTMENT_NOT_FOUND = "Department invalid";
        public static final String PR_ID_IS_EMPTY = "Pr id không được để trống";

    }

    public static class SYSTEM_VALUE {
        public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5 * 60 * 60;
        public static final String SIGNING_KEY = "com123tnr";
        public static final String AUTHORITIES_KEY = "scopes";
        public static final String X_TOTAL_COUNT = "X-Total-Count";
    }

    public static class BRAND {
        public static final String BRAND_NAME_IS_NOT_EMPTY = "Tên thương hiệu không được để trống";
        public static final String BRAND_IS_NOT_EMPTY = "Thương hiệu không được để trống";
        public static final String BRAND_NAME_EXISTED = "Tên thương hiệu đã tồn tại";
        public static final String BRAND_NAME_MORE_THAN_200_CHAR = "Tên thương hiệu không được vượt quá 200 ký tự";
        public static final String BRAND_NOT_FOUND = "Không tìm thấy thương hiệu";
    }

    public static class CATEGORIES {
        public static final String CATE_NAME_IS_NOT_EMPTY = "Tên loại sản phẩm không được để trống";
        public static final String CATE_IS_NOT_EMPTY = "Loại sản phẩm không được để trống";
        public static final String CATE_NAME_EXISTED = "Tên loại sản phẩm đã tồn tại";
        public static final String CATE_NAME_MORE_THAN_200_CHAR = "Tên loại sản phẩm không được vượt quá 200 ký tự";
        public static final String CATE_NOT_FOUND = "Không tìm thấy loại sản phẩm";
    }

    public static class PRODUCTS {
        public static final String PROD_NAME_IS_NOT_EMPTY = "Tên sản phẩm không được để trống";
        public static final String PROD_IS_NOT_EMPTY = "Sản phẩm không được để trống";
        public static final String PROD_NAME_EXISTED = "Tên sản phẩm đã tồn tại";
        public static final String PROD_NAME_MORE_THAN_200_CHAR = "Tên sản phẩm không được vượt quá 200 ký tự";
        public static final String PROD_NOT_FOUND = "Không tìm thấy sản phẩm";
        public static final String PROD_PRICE_IS_NOT_EMPTY = "Giá sản phẩm không được để trống";
        public static final String PROD_PRICE_MUST_BE_GREATER_THAN_OR_EQUAL_TO_ZERO = "Giá sản phẩm phải lớn hơn hoặc bằng 0";
        public static final String PROD_QUANTITY_AVAILABLE_MUST_BE_GREATER_THAN_OR_EQUAL_TO_ZERO = "Số lượng tồn hàng của sản phẩm phải lớn hơn hoặc bằng 0";
        public static final String PROD_STATUS_IS_NOT_EMPTY = "Trạng thái của sản phẩm không được để trống";
        public static final String PROD_DISCOUNT_AMOUNT_MUST_BE_GREATER_THAN_OR_EQUAL_TO_ZERO = "Số tiền chiết khấu của sản phẩm phải lớn hơn hoặc bằng 0";
        public static final String PROD_DISCOUNT_PERCENTAGE_MUST_BE_BETWEEN_0_AND_100 = "Tỷ lệ chiết khấu của sản phẩm phải lớn hơn 0 và nhỏ hơn 100";
        public static final String PROD_FINAL_TOTAL_PRICE_MUST_BE_GREATER_THAN_OR_EQUAL_TO_ZERO = "Tổng giá sau khi chiết khấu của sản phẩm phải lớn hơn hoặc bằng 0";
    }

    public static class PRODUCT_SPEC {
        public static final String PRODUCT_SPEC_NAME_IS_NOT_EMPTY = "Tên thông số sản phẩm không được để trống";
        public static final String PRODUCT_SPEC_IS_NOT_EMPTY = "Thông số sản phẩm không được để trống";
        public static final String PRODUCT_SPEC_NAME_EXISTED = "Tên thông số sản phẩm đã tồn tại";
        public static final String PRODUCT_SPEC_NAME_MORE_THAN_200_CHAR = "Tên thông số sản phẩm không được vượt quá 200 ký tự";
        public static final String PRODUCT_SPEC_NOT_FOUND = "Không tìm thấy thông số sản phẩm";
    }

    public static class PRODUCT_IMAGE {
        public static final String PRODUCT_IMAGE_URL_IS_NOT_EMPTY = "Url ảnh sản phẩm không được để trống";
        public static final String PRODUCT_IMAGE_IS_NOT_EMPTY = "Ảnh sản phẩm không được để trống";
        public static final String PRODUCT_IMAGE_NOT_FOUND = "Không tìm thấy ảnh sản phẩm";
    }
}
