package store.validator;

import java.util.List;
import javax.swing.event.HyperlinkEvent;
import store.enums.Error;

public class Validator {
    public static void checkPurchaseFormat(List<String> productData) {
        for (String oneProductData : productData) {
            String[] oneProductDataSplit = oneProductData.split(",");
            checkCoverBracket(oneProductDataSplit[0]);
            checkInnerFormat(oneProductDataSplit[0]);
        }
    }

    private static void checkInnerFormat(String str) {
        String cleanStr = str.replaceAll("^\\[", "").replaceAll("\\]$", "");
        String[] strSplit = cleanStr.split("-");
        if (strSplit.length != 2) {
            throw new IllegalArgumentException(Error.PURCHASE_FORMAT_MESSAGE.getErrorMessage());
        }
        if (!strSplit[1].matches("\\d+")) {
            throw new IllegalArgumentException(Error.PURCHASE_FORMAT_MESSAGE.getErrorMessage());
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
}
