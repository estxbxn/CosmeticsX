package fr.zebulon.cosmeticsx.models;

public class CosmeticMeta {

    private int quantity;

    public CosmeticMeta(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CosmeticMeta{" + "quantity=" + quantity + '}';
    }
}
