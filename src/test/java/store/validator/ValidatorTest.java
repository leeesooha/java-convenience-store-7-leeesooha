package store.validator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ValidatorTest {
    private static final String FIRST_PURCHASE_PRODUCT = "[콜라-1]";
    private static final String SECOND_PURCHASE_PRODUCT = "[콜라-2]";
    private static final String THIRD_PURCHASE_PRODUCT = "[사이다-2]";
    private static final String INVALID_FRONT_BRACKET_PURCHASE_PRODUCT = "사이다-2]";
    private static final String INVALID_END_BRACKET_PURCHASE_PRODUCT = "[감자칩-2";
    private static final String VALID_FRONT_BRACKET_PURCHASE_PRODUCT = "[도시락정식-2]";
    private static final String INVALID_FORMAT_PURCHASE_PRODUCT_1 = "[비타민워터2]";
    private static final String INVALID_FORMAT_PURCHASE_PRODUCT_2 = "[비타민-워터2]";
    private static final String INVALID_FORMAT_PURCHASE_PRODUCT_3 = "[비타민워터_2]";

    @Test
    @DisplayName("구매상품내역에 같은 상품명이 여러개이면 예외 발생하는 테스트")
    void checkDuplicateProductNameExpectException() {
        List<String> productData = new ArrayList<>();
        productData.add(FIRST_PURCHASE_PRODUCT);
        productData.add(SECOND_PURCHASE_PRODUCT);

        assertThatThrownBy(() -> Validator.checkPurchaseFormat(productData))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("구매상품내역에 같은 상품명이 유일하면 예외 발생 안해야 하는 테스트")
    void checkDuplicateProductName() {
        List<String> productData = new ArrayList<>();
        productData.add(FIRST_PURCHASE_PRODUCT);
        productData.add(THIRD_PURCHASE_PRODUCT);

        assertDoesNotThrow(() -> Validator.checkPurchaseFormat(productData));
    }

    @DisplayName("구매상품내역이 대괄호로 감싸지지 않으면 예외 발생하는 테스트")
    @ParameterizedTest
    @ValueSource(strings = {INVALID_FRONT_BRACKET_PURCHASE_PRODUCT, INVALID_END_BRACKET_PURCHASE_PRODUCT})
    void checkCoverBracketExpectException(String invalidBracketProduct) {
        List<String> productData = new ArrayList<>();
        productData.add(invalidBracketProduct);
        assertThatThrownBy(() -> Validator.checkPurchaseFormat(productData))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("구매상품내역이 대괄호로 감싸지면 예외 발생 안해야 하는 테스트")
    void checkCoverBracket() {
        List<String> productData1 = new ArrayList<>();
        productData1.add(VALID_FRONT_BRACKET_PURCHASE_PRODUCT);

        assertDoesNotThrow(() -> Validator.checkPurchaseFormat(productData1));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            INVALID_FORMAT_PURCHASE_PRODUCT_1,
            INVALID_FORMAT_PURCHASE_PRODUCT_2,
            INVALID_FORMAT_PURCHASE_PRODUCT_3})
    @DisplayName("구매상품내역이 대괄호안의 상품과 수량의 포맷이 유효하지 않으면 예외 발생하는 테스트")
    void checkInnerFormatExpectException(String InvalidFormatPurchaseProduct) {
        List<String> productData = new ArrayList<>();
        productData.add(InvalidFormatPurchaseProduct);
        assertThatThrownBy(() -> Validator.checkPurchaseFormat(productData))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("구매상품내역이 대괄호안의 상품과 수량의 포맷이 유효하면 예외 발생 안하는 테스트")
    void checkInnerFormat() {
        List<String> productData1 = new ArrayList<>();
        productData1.add(VALID_FRONT_BRACKET_PURCHASE_PRODUCT);

        assertDoesNotThrow(() -> Validator.checkPurchaseFormat(productData1));
    }
}
