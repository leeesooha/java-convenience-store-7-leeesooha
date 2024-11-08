package store.controller;

import java.util.List;
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
            } catch (IllegalArgumentException e){
                outputView.display(e.getMessage());
                continue;
            }
            return;
        }
    }
}
