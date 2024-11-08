package store.controller;

import store.model.Inventory;
import store.model.PromotionCatalog;
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
        Inventory inventory = storeService.createInventory(promotionCatalog);
//        while (true) {
        outputView.displayInventory(inventory);
        purchaseProduct(inventory);
//        }
    }

    private void purchaseProduct(Inventory inventory) {
        outputView.displayPurchaseMessage();
        //구매할 상품 입력
        //프로모션인지 확인
        //프로모션이 개수 확인
            //모자르면 적용안됨. 그래도 구매?
                //Y면 멤버십(이 메소드는 끝이다 사실상)
                //N이면 구매를 아예안해?? 아니면 프로모션만 구매햌?

    }
}
