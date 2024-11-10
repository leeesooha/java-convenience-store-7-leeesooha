package store.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static store.model.PromotionTest.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StockInventoryTest {
    public static final String PRODUCT_NAME_1 = "콜라";
    public static final String PRODUCT_NAME_2 = "사이다";
    public static final String PRODUCT_NAME_3 = "감자칩";
    public static final String NONE_EXIST_PRODUCT_NAME = "햇반";
    public static final String PROMOTION_NAME_2 = "삼계탕데이";
    public static final String PROMOTION_NAME_3 = "호박데이";
    public static final String NOT_PROMOTION_PRODUCT = "";
    public static final int PRODUCT_PRICE_1 = 1000;
    public static final int PRODUCT_PRICE_2 = 2000;
    public static final int PRODUCT_QUANTITY = 5;

    private StockInventory stockInventory = new StockInventory();
    private Product product1;
    private Product product2;
    private Product product3;
    private Promotion promotion1;
    private Promotion promotion2;
    private Promotion promotion3;
    private ProductBox promotionProductBox1;
    private ProductBox productBox2;
    private ProductBox productBox3;
    private ProductBox normalProductBox1;

    @BeforeEach
    void initStockInventory() {
        product1 = new Product(PRODUCT_NAME_1, PRODUCT_PRICE_1);
        promotion1 = new Promotion(NOT_PROMOTION_PRODUCT);
        normalProductBox1 = new ProductBox(product1, promotion1, PRODUCT_QUANTITY);

        product2 = new Product(PRODUCT_NAME_2, PRODUCT_PRICE_2);
        promotion2 = new Promotion(PROMOTION_NAME_2, BUY, GET, START_DATE, VALID_END_DATE);
        productBox2 = new ProductBox(product2, promotion2, PRODUCT_QUANTITY);

        product3 = new Product(PRODUCT_NAME_3, PRODUCT_PRICE_1);
        promotion3 = new Promotion(PROMOTION_NAME_3, BUY, GET, START_DATE, VALID_END_DATE);
        productBox3 = new ProductBox(product3, promotion3, PRODUCT_QUANTITY);

        promotionProductBox1 = new ProductBox(product1, promotion2, PRODUCT_QUANTITY);

        stockInventory.addProductBox(promotionProductBox1);
        stockInventory.addProductBox(normalProductBox1);
        stockInventory.addProductBox(productBox2);
        stockInventory.addProductBox(productBox3);
    }

    @Test
    @DisplayName("상품 이름으로 상품객체 찾아오는 기능 테스트")
    void findProductByProductName() {
        assertEquals(stockInventory.findProductByProductName(PRODUCT_NAME_2), product2);
        assertEquals(stockInventory.findProductByProductName(PRODUCT_NAME_3), product3);
    }

    @Test
    @DisplayName("상품이름으로 프로모션인 상품 박스 찾아오는 기능 테스트")
    void findPromotionProductBoxByProductName() {
        assertEquals(stockInventory.findPromotionProductBoxByProductName(PRODUCT_NAME_1), promotionProductBox1);
        assertEquals(stockInventory.findPromotionProductBoxByProductName(PRODUCT_NAME_2), productBox2);
        assertEquals(stockInventory.findPromotionProductBoxByProductName(PRODUCT_NAME_3), productBox3);
    }

    @Test
    @DisplayName("상품이름으로 일반 상품 박스 찾아오는 기능 테스트")
    void findNormalProductBoxByProductName() {
        assertEquals(stockInventory.findNormalProductBoxByProductName(PRODUCT_NAME_1), normalProductBox1);
    }

    @Test
    @DisplayName("프로모션 재고와 일반재고가 상품구매수만큼 충분한지 확인하는 기능 테스트")
    void isStockAvailable() {
        assertTrue(stockInventory.isStockAvailable(PRODUCT_NAME_2, PRODUCT_QUANTITY));
        assertFalse(stockInventory.isStockAvailable(PRODUCT_NAME_2, PRODUCT_QUANTITY + 1));

        assertTrue(stockInventory.isStockAvailable(PRODUCT_NAME_1, PRODUCT_QUANTITY * 2));
    }
}
