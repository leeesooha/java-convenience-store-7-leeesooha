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
        outputView.displayPurchaseMessage();
        List<String> productData = inputView.inputProduct();
        ShoppingCart shoppingCart = storeService.createShoppingCart(productData);
        //프로모션인지 확인
        //프로모션이 개수 확인
            //모자르면 적용안됨. 그래도 구매?
                //Y면 멤버십(이 메소드는 끝이다 사실상)
                //N이면 구매를 아예안해?? 아니면 프로모션만 구매햌?

    }
}
