package store.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductBoxTest {
    Product product;
    Promotion promotion;
    ProductBox productBox ;

    @BeforeEach
    void initProductBox() {
        product = new Product("콜라", 1000);
        promotion = new Promotion("반짝할인");
        productBox = new ProductBox(product, promotion, 5);
    }

    @Test
    @DisplayName("상품 박스의 이름이 맞는지 확인하는 메소드 테스트")
    void isMatchProduct() {
        assertTrue(productBox.isMatchProduct("콜라"));
        assertFalse(productBox.isMatchProduct("틀린 콜라"));
    }

    @Test
    @DisplayName("수량이 잘 감소하는지 확인하는 테스트")
    void decreaseQuantityBy() {
        productBox.decreaseQuantityBy(3);

        Assertions.assertEquals(productBox.getQuantity(), 2);
    }
}
