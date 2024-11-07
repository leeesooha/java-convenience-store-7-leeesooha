package store.controller;

import java.io.IOException;
import java.util.List;
import store.model.Inventory;
import store.service.StoreService;
import store.view.InputView;

public class StoreController {
    InputView inputView;
    StoreService storeService;

    public StoreController(InputView inputView, StoreService storeService) {
        this.inputView = inputView;
        this.storeService = storeService;
    }

    public void run() throws IOException {
//        while (true) {
        List<String> input = inputView.inputProductToPurchase();
        Inventory inventory = storeService.addItemToInventory(input);
//        }
    }
}
