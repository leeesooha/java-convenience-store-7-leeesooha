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
//        }
    }
}
