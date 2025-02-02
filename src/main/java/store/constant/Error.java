package store.constant;

public enum Error {
    ERROR_MESSAGE("[ERROR] "),
    INSUFFICIENT_STOCK_MESSAGE("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    NONE_EXIST_STOCK_MESSAGE("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요."),
    PURCHASE_FORMAT_MESSAGE("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요."),
    FILE_ERROR("[ERROR] 파일 읽기에 실패하였습니다."),
    EMPTY_INPUT("[ERROR] 빈 입력입니다."),
    DUPLICATE_PRODUCT_NAME("[ERROR] 중복된 상품명은 입력할 수 없습니다.");

    private final String errorMessage;

    Error(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
