package store.constant;

public class Message {
    public static final String WELCOME_MESSAGE = "안녕하세요. W편의점입니다.\n"
            + "현재 보유하고 있는 상품입니다.\n";
    public static final String PURCHASE_MESSAGE = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])\n";
    public static final String MEMBERSHIP_MESSAGE = "멤버십 할인을 받으시겠습니까? (Y/N)\n";
    public static final String ASK_CONTINUE_SHOPPING = "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)\n";
    public static final String CONFIRM_PURCHASE_PROMOTION_MESSAGE = "현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)";
    public static final String FREE_PRODUCT_CONFIRMATION_MESSAGE = "현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)";
    public static final String PRODUCT_OUT_OF_STOCK_FORMAT = "- %s %,d원 재고 없음%s\n";
    public static final String PRODUCT_AVAILABLE_FORMAT = "- %s %,d원 %,d개%s\n";
    public static final String SPACE_CHARACTER = " ";
    public static final String EMPTY = "";
    public static final int ZERO = 0;
}
