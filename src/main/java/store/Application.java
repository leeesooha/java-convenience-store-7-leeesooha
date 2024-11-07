package store;

import java.io.IOException;
import store.controller.StoreController;
import store.service.StoreService;
import store.view.InputView;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        StoreService storeService = new StoreService();
        StoreController store = new StoreController(inputView, outputView, storeService);
        store.run();
    }
}
