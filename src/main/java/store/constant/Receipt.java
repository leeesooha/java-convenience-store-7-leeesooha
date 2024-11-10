package store.constant;

public class Receipt {
    public static final String RECEIPT_SEPARATOR = "====================================\n";
    public static final String TOTAL_QUANTITY_PRICE_FORMAT = "총구매액\t\t%,d\t%,d\n";
    public static final String PROMOTION_DISCOUNT_FORMAT = "행사할인\t\t\t-%,d\n";
    public static final String MEMBERSHIP_DISCOUNT_FORMAT = "멤버십할인\t\t\t-%,d\n";
    public static final String FINAL_AMOUNT_FORMAT = "내실돈\t\t\t %,d\n\n";
    public static final String BONUS_HEADER = "===========증\t정=============\n";
    public static final String BONUS_DETAIL_FORMAT = "%s\t\t%,d\n";
    public static final String RECEIPT_HEADER = "===========W 편의점=============\n";
    public static final String RECEIPT_COLUMN_HEADER = "상품명\t\t수량\t금액\n";
    public static final String ITEM_DETAIL_FORMAT = "%s\t\t%,d\t%,d\n";

    public static final int MAX_MEMBERSHIP_AMOUNT = 8000;
}
