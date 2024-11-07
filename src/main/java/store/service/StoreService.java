package store.service;

import java.util.Arrays;
import java.util.List;
import store.model.Product;
import store.model.Inventory;

public class StoreService {


    public Inventory addItemToInventory(List<String> input) {
        Inventory inventory = new Inventory();

        for (String oneProduct : input) {
            List<String> productInfo = Arrays.asList(oneProduct.split(","));
            String name = productInfo.get(0);
            int price = Integer.parseInt(productInfo.get(1));
            int quantity = Integer.parseInt(productInfo.get(2));
            String promotion = productInfo.get(3);
            inventory.addProduct(new Product(name, price, quantity, promotion));
        }
        return inventory;
    }
}
