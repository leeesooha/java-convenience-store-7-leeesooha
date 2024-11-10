package store.view;

import static store.constant.Message.*;
import static store.constant.Receipt.BONUS_DETAIL_FORMAT;
import static store.constant.Receipt.BONUS_HEADER;
import static store.constant.Receipt.FINAL_AMOUNT_FORMAT;
import static store.constant.Receipt.ITEM_DETAIL_FORMAT;
import static store.constant.Receipt.MAX_MEMBERSHIP_AMOUNT;
import static store.constant.Receipt.MEMBERSHIP_DISCOUNT_FORMAT;
import static store.constant.Receipt.PROMOTION_DISCOUNT_FORMAT;
import static store.constant.Receipt.RECEIPT_COLUMN_HEADER;
import static store.constant.Receipt.RECEIPT_HEADER;
import static store.constant.Receipt.RECEIPT_SEPARATOR;
import static store.constant.Receipt.TOTAL_QUANTITY_PRICE_FORMAT;

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
        System.out.printf(RECEIPT_SEPARATOR);
        System.out.printf(TOTAL_QUANTITY_PRICE_FORMAT, receiptDto.getTotalQuantity(), receiptDto.getTotalPrice());
        System.out.printf(PROMOTION_DISCOUNT_FORMAT, receiptDto.getTotalPromotionPrice());
        if (discountRate != 0) {
            calculateMembership(receiptDto, discountRate);
        }
        System.out.printf(MEMBERSHIP_DISCOUNT_FORMAT, receiptDto.getApplyMembershipDiscount());
        System.out.printf(FINAL_AMOUNT_FORMAT, receiptDto.getTotalPrice() -
                receiptDto.getApplyMembershipDiscount() - receiptDto.getTotalPromotionPrice());
    }

    private void calculateMembership(ReceiptDto receiptDto, double discountRate) {
        receiptDto.setApplyMembershipDiscount((int) (discountRate * receiptDto.getNotApplyPromotionPrice()));
        if (receiptDto.getApplyMembershipDiscount() > MAX_MEMBERSHIP_AMOUNT) {
            receiptDto.setApplyMembershipDiscount(MAX_MEMBERSHIP_AMOUNT);
        }
    }

    private void displayBonusRecord(ShoppingCart shoppingCart) {
        System.out.printf(BONUS_HEADER);
        for (ShoppingItem shoppingItem : shoppingCart.getShoppingItems()) {
            if (shoppingItem.getPromotionQuantity() != ZERO) {
                System.out.printf(BONUS_DETAIL_FORMAT, shoppingItem.getName(), shoppingItem.getPromotionQuantity());
            }
        }
    }

    private void displayPurchaseRecord(ShoppingCart shoppingCart, StockInventory stockInventory,
                                       ReceiptDto receiptDto) {
        for (ShoppingItem shoppingItem : shoppingCart.getShoppingItems()) {
            int price = stockInventory.findPriceByProductName(shoppingItem.getName()) * (shoppingItem.getQuantity()
                    + shoppingItem.getPromotionQuantity());

            updateReceiptDto(receiptDto, shoppingItem, stockInventory);
            displayReceiptResultDetail(shoppingItem, price);
        }
    }

    private void displayReceiptResultDetail(ShoppingItem shoppingItem, int price) {
        System.out.printf(ITEM_DETAIL_FORMAT, shoppingItem.getName(),
                shoppingItem.getQuantity() + shoppingItem.getPromotionQuantity(), price);
    }

    private void updateReceiptDto(ReceiptDto receiptDto, ShoppingItem shoppingItem, StockInventory stockInventory) {
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
        System.out.printf(RECEIPT_HEADER);
        System.out.printf(RECEIPT_COLUMN_HEADER);
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
        promotionName = SPACE_CHARACTER + promotionName;
        if (promotionName.equals(SPACE_CHARACTER)) {
            promotionName = EMPTY;
        }
        if (quantity == 0) {
            System.out.printf(PRODUCT_OUT_OF_STOCK_FORMAT, productName, price, promotionName);
            return;
        }
        System.out.printf(PRODUCT_AVAILABLE_FORMAT, productName, price, quantity, promotionName);
    }

    public void display(String message) {
        System.out.print(message);
    }

    public void displayConfirmPurchase(String productName, int notApplyPromotionQuantity) {
        System.out.printf(CONFIRM_PURCHASE_PROMOTION_MESSAGE, productName, notApplyPromotionQuantity);
    }

    public void displayFreeProductConfirm(String productName, int ableBonusQuantity) {
        System.out.printf(FREE_PRODUCT_CONFIRMATION_MESSAGE, productName, ableBonusQuantity);
    }
}
