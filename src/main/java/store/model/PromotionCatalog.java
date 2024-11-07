package store.model;

import java.util.ArrayList;
import java.util.List;

public class PromotionCatalog {
    List<Promotion> promotions;

    public PromotionCatalog() {
        this.promotions = new ArrayList<>();
    }

    public void addPromotion(Promotion promotion) {
        this.promotions.add(promotion);
    }

}
