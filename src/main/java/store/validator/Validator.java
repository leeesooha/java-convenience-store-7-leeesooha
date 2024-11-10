package store.validator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import store.constant.Error;

public class Validator {
    public static void checkPurchaseFormat(List<String> productData) {
        for (String oneProductData : productData) {
            checkCoverBracket(oneProductData);
            checkInnerFormat(oneProductData);
        }
        checkDuplicateProductName(productData);
    }

    private static void checkDuplicateProductName(List<String> productData) {
        Set<String> uniqueProductName = new HashSet<>();

        for (String oneProductData : productData) {
            String[] productName = oneProductData.
                    replaceAll("^\\[", "").
                    replaceAll("\\]$", "").
                    split("-");
            uniqueProductName.add(productName[0]);
        }
        if (uniqueProductName.size() != productData.size()) {
            throw new IllegalArgumentException(Error.DUPLICATE_PRODUCT_NAME.getErrorMessage());
        }
    }

    public static void checkCoverBracket(String str) {
        if (str.charAt(0) != '[') {
            throw new IllegalArgumentException(Error.PURCHASE_FORMAT_MESSAGE.getErrorMessage());
        }
        if (str.charAt(str.length() - 1) != ']') {
            throw new IllegalArgumentException(Error.PURCHASE_FORMAT_MESSAGE.getErrorMessage());
        }
    }

    private static void checkInnerFormat(String str) {
        String cleanStr = str.
                replaceAll("^\\[", "").
                replaceAll("\\]$", "");
        String[] strSplit = cleanStr.split("-");
        if (strSplit.length != 2) {
            throw new IllegalArgumentException(Error.PURCHASE_FORMAT_MESSAGE.getErrorMessage());
        }
        if (!strSplit[1].matches("\\d+")) {
            throw new IllegalArgumentException(Error.PURCHASE_FORMAT_MESSAGE.getErrorMessage());
        }
    }
}
