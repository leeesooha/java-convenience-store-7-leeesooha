package store.dto;

public class ReceiptDto {
    int totalQuantity;
    int totalPrice;
    int notApplyPromotionPrice;
    int totalPromotionPrice;
    int applyMembershipDiscount;

    public ReceiptDto() {
        this.totalQuantity = 0;
        this.totalPrice = 0;
        this.notApplyPromotionPrice = 0;
        this.totalPromotionPrice = 0;
        this.applyMembershipDiscount = 0;
    }

    public void addTotalQuantity(int totalQuantity) {
        this.totalQuantity += totalQuantity;
    }

    public void addTotalPrice(int totalPrice) {
        this.totalPrice += totalPrice;
    }

    public void addNotApplyPromotionPrice(int notApplyPromotionPrice) {
        this.notApplyPromotionPrice += notApplyPromotionPrice;
    }

    public void addTotalPromotionPrice(int totalPromotionPrice) {
        this.totalPromotionPrice += totalPromotionPrice;
    }

    public void addApplyMembershipDiscount(int applyMembershipDiscount) {
        this.applyMembershipDiscount += applyMembershipDiscount;
    }

    public int getApplyMembershipDiscount() {
        return applyMembershipDiscount;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getTotalPromotionPrice() {
        return totalPromotionPrice;
    }

    public int getNotApplyPromotionPrice() {
        return notApplyPromotionPrice;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setApplyMembershipDiscount(int applyMembershipDiscount) {
        this.applyMembershipDiscount = applyMembershipDiscount;
    }

    public void setNotApplyPromotionPrice(int notApplyPromotionPrice) {
        this.notApplyPromotionPrice = notApplyPromotionPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setTotalPromotionPrice(int totalPromotionPrice) {
        this.totalPromotionPrice = totalPromotionPrice;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
