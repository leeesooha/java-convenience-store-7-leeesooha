package store.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PromotionTest {
    public static final String PROMOTION_NAME = "반짝할인";
    public static final int BUY = 2;
    public static final int GET = 1;
    public static final LocalDate START_DATE = LocalDate.parse("2024-10-01");
    public static final LocalDate INVALID_END_DATE = LocalDate.parse("2024-11-10");
    public static final LocalDate VALID_END_DATE = LocalDate.parse("2025-11-10");

    @Test
    @DisplayName("지난 프로모션이 활성화 안됬음을 확인하는 테스트")
    void isPromotionNotActiveTest() {
        Promotion promotion = new Promotion(PROMOTION_NAME, BUY, GET, START_DATE, INVALID_END_DATE);

        assertFalse(promotion.isPromotionActive());
    }

    @Test
    @DisplayName("유효한 기간의 프로모션이 활성화 됬음을 확인하는 테스트")
    void isPromotionActiveTest() {
        Promotion promotion = new Promotion(PROMOTION_NAME, BUY, GET, START_DATE, VALID_END_DATE);

        assertTrue(promotion.isPromotionActive());
    }

}
