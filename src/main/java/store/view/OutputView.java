package store.view;

import static store.enums.Message.*;

import store.model.Inventory;
import store.model.ProductBox;

public class OutputView {

    public void printWelcomeMessage() {
        System.out.println(WELCOME);
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
