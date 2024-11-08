package store.controller;

import java.util.List;
import store.model.ProductBox;
import store.model.ShoppingItem;
import store.model.StockInventory;
import store.model.PromotionCatalog;
import store.model.ShoppingCart;
import store.service.StoreService;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {
    InputView inputView;
    OutputView outputView;
    StoreService storeService;

    public StoreController(InputView inputView, OutputView outputView, StoreService storeService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.storeService = storeService;
    }

    public void run() {
        PromotionCatalog promotionCatalog = storeService.createPromotion();
        StockInventory stockInventory = storeService.createInventory(promotionCatalog);
//        while (true) {
        outputView.displayInventory(stockInventory);
        purchaseProduct(stockInventory);
//        }
    }

    private void purchaseProduct(StockInventory stockInventory) {
        while (true) {
            outputView.displayPurchaseMessage();
            List<String> productData = inputView.inputProduct();
            ShoppingCart shoppingCart = storeService.createShoppingCart(productData);
            try {
                storeService.checkAllExistProductName(stockInventory, shoppingCart);
                storeService.checkAllStockAvailable(stockInventory, shoppingCart);
                reduceStockByPurchase(stockInventory, shoppingCart);

            } catch (IllegalArgumentException e){
                outputView.display(e.getMessage());
                continue;
            }
            return;
        }
    }

    public void reduceStockByPurchase(StockInventory stockInventory, ShoppingCart shoppingCart) {
        for (ShoppingItem shoppingItem : shoppingCart.getShoppingItems()) {
            ProductBox promotionProductBox = stockInventory.findPromotionProductBoxByProductName(shoppingItem.getName());
            //일반재고 일시
            if (promotionProductBox == null) { //프로모션 아니면
                //일반재고 차감
                ProductBox normalProductBox = stockInventory.findNormalProductBoxByProductName(shoppingItem.getName());
                normalProductBox.decreaseQuantityBy(shoppingItem.getQuantity());
                continue;
            }
            //프로모션상품 일시
            //프로모션 재고 충분하면
            if (shoppingItem.getQuantity() <= promotionProductBox.getQuantity()) {
                promotionProductBox.decreaseQuantityBy(shoppingItem.getQuantity());
                continue;
            }
            //프로모션 재고가 부족하면
            //그래도 구매하시겠습니까? 띄우고
            int notApplyPromotionQuantity = shoppingItem.getQuantity() - promotionProductBox.getQuantity();
            outputView.displayConfirmPurchase(shoppingItem.getName(), notApplyPromotionQuantity);
            boolean isConfirm = inputView.inputYesOrNo();
            //if ("구매한다면") {
            if (isConfirm) {
                //남은 수량 만큼 일반재고 차감
                ProductBox normalProductBox = stockInventory.findNormalProductBoxByProductName(shoppingItem.getName());
                normalProductBox.decreaseQuantityBy(notApplyPromotionQuantity);
                promotionProductBox.decreaseQuantityBy(promotionProductBox.getQuantity());
                continue;
            }
            //구매안한다면
            //장바구니에 구매 수량 변경해줌. 구매 안하는 수량만큼 빼줌.
            shoppingItem.decreaseQuantityBy(notApplyPromotionQuantity);
        }
    }
}
