package store.view;

import static store.constant.Message.*;

import store.dto.ReceiptDto;
import store.model.ShoppingCart;
import store.model.ShoppingItem;
import store.model.StockInventory;
import store.model.ProductBox;

public class OutputView {

    public void displayReceipt(StockInventory stockInventory, ShoppingCart shoppingCart,
                               double discountRate) {
        ReceiptDto receiptDto = new ReceiptDto();

        displayReceiptHeader();
        displayPurchaseRecord(shoppingCart, stockInventory, receiptDto);
        displayBonusRecord(shoppingCart);
        displayReceipResult(receiptDto, discountRate);

        shoppingCart.clearCart();
    }

    private void displayReceipResult(ReceiptDto receiptDto, double discountRate) {
        System.out.printf("==============================\n");
        System.out.printf("총구매액\t\t%,d\t%,d\n", receiptDto.getTotalQuantity(), receiptDto.getTotalPrice());
        System.out.printf("행사할인\t\t\t-%,d\n", receiptDto.getTotalPromotionPrice());
        if (discountRate != 0) {
            calculateMembership(receiptDto, discountRate);
        }
        System.out.printf("멤버십할인\t\t\t-%,d\n", receiptDto.getApplyMembershipDiscount());
        System.out.printf("내실돈\t\t\t %,d\n\n", receiptDto.getTotalPrice() -
                receiptDto.getApplyMembershipDiscount() - receiptDto.getTotalPromotionPrice());
    }

    private void calculateMembership(ReceiptDto receiptDto, double discountRate) {
        receiptDto.setApplyMembershipDiscount((int) (discountRate * receiptDto.getNotApplyPromotionPrice()));
        if (receiptDto.getApplyMembershipDiscount() > 8000) {
            receiptDto.setApplyMembershipDiscount(8000);
        }
    }

    private void displayBonusRecord(ShoppingCart shoppingCart) {
        System.out.printf("===========증\t정=============\n");
        for (ShoppingItem shoppingItem : shoppingCart.getShoppingItems()) {
            if (shoppingItem.getPromotionQuantity() != 0) {
                System.out.printf("%s\t\t%,d\n", shoppingItem.getName(), shoppingItem.getPromotionQuantity());
            }
        }
    }

    private void displayPurchaseRecord(ShoppingCart shoppingCart, StockInventory stockInventory,
                                       ReceiptDto receiptDto) {
        for (ShoppingItem shoppingItem : shoppingCart.getShoppingItems()) {
            int price = stockInventory.findPriceByProductName(shoppingItem.getName()) * (shoppingItem.getQuantity()
                    + shoppingItem.getPromotionQuantity());

            updateReceipDto(receiptDto, shoppingItem, stockInventory);
            displayReceipResultDetail(shoppingItem, price);
        }
    }

    private void displayReceipResultDetail(ShoppingItem shoppingItem, int price) {
        System.out.printf("%s\t\t%,d\t%,d\n", shoppingItem.getName(),
                shoppingItem.getQuantity() + shoppingItem.getPromotionQuantity(), price);
    }

    private void updateReceipDto(ReceiptDto receiptDto, ShoppingItem shoppingItem, StockInventory stockInventory) {
        receiptDto.addTotalQuantity(shoppingItem.getQuantity() + shoppingItem.getPromotionQuantity());
        receiptDto.addTotalPrice(
                stockInventory.findPriceByProductName(shoppingItem.getName()) * ((shoppingItem.getQuantity()
                        + shoppingItem.getPromotionQuantity())));
        receiptDto.addNotApplyPromotionPrice(
                stockInventory.findPriceByProductName(shoppingItem.getName()) * (shoppingItem.getQuantity()
                        - shoppingItem.getPromotionQuantity()));
        receiptDto.addTotalPromotionPrice(stockInventory.findPriceByProductName(shoppingItem.getName())
                * shoppingItem.getPromotionQuantity());
    }

    private void displayReceiptHeader() {
        System.out.printf("===========W 편의점=============\n");
        System.out.printf("상품명\t\t수량\t금액\n");
    }

    public void printWelcomeMessage() {
        System.out.println(WELCOME_MESSAGE);
    }

    public void displayPurchaseMessage() {
        System.out.printf(PURCHASE_MESSAGE);
    }

    public void displayInventory(StockInventory stockInventory) {
        this.printWelcomeMessage();
        for (ProductBox productBox : stockInventory.getProductBoxes()) {
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

    public void display(String message) {
        System.out.print(message);
    }

    public void displayConfirmPurchase(String productName, int notApplyPromotionQuantity) {
        System.out.printf("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)",
                productName, notApplyPromotionQuantity);
    }

    public void displayFreeProductConfirm(String productName, int ableBonusQuantity) {
        System.out.printf("현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)",
                productName, ableBonusQuantity);
    }
}
