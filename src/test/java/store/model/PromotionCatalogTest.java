package store.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PromotionCatalogTest {
    private static final String PROMOTION_NAME_1 = "반짝할인";
    private static final String PROMOTION_NAME_2 = "빼뺴로데이";


    @Test
    @DisplayName("프로모션 이름으로 프로모션 객체 찾아오는 기능 테스트")
    void findPromotion() {
        Promotion promotion1 = new Promotion(PROMOTION_NAME_1);
        Promotion promotion2 = new Promotion(PROMOTION_NAME_2);
        PromotionCatalog promotionCatalog = new PromotionCatalog();

        promotionCatalog.addPromotion(promotion1);
        promotionCatalog.addPromotion(promotion2);

        assertEquals(promotionCatalog.findPromotion(PROMOTION_NAME_2), promotion2);
    }
}
