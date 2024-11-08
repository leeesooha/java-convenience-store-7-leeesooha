package store.enums;

public enum Error {
    ERROR_MESSAGE("[ERROR] "),
    INSUFFICIENT_STOCK_MESSAGE("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");

    private final String errorMessage;

    Error(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
