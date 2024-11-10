package store.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductBoxTest {

    @Test
    @DisplayName("상품 박스의 이름이 맞는지 확인하는 메소드 테스트")
    void isMatchProduct() {
        Product product = new Product("콜라", 1);
        Promotion promotion = new Promotion("반짝할인");
        ProductBox productBox = new ProductBox(product, promotion, 5);

        assertTrue(productBox.isMatchProduct("콜라"));
        assertFalse(productBox.isMatchProduct("틀린 콜라"));
    }

}
