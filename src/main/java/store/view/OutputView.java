package store.view;

import static store.enums.Message.*;

import store.model.Inventory;
import store.model.ProductBox;

public class OutputView {

    public void printWelcomeMessage() {
        System.out.println(WELCOME);
    }

    public void displayPurchaseMessage() {
        System.out.printf("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])\n");
    }

    public void displayInventory(Inventory inventory) {
        this.printWelcomeMessage();
        for (ProductBox productBox : inventory.getProductBoxes()) {
            String productName = productBox.getProduct().getName();
            int price = productBox.getProduct().getPrice();
            int quantity = productBox.getQuantity();
            String promotionName = productBox.getPromotion().getName();

            displayProductLine(productName, price, quantity, promotionName);
        }
        System.out.println();
    }

    public void displayProductLine(String productName, int price, int quantity, String promotionName) {
        promotionName = " " + promotionName;
        if (promotionName.equals(" ")) {
            promotionName = "";
        }
        if (quantity == 0) {
            System.out.printf("- %s %,d원 재고 없음%s\n", productName, price, promotionName);
            return;
        }
        System.out.printf("- %s %,d원 %,d개%s\n", productName, price, quantity, promotionName);
    }
}
