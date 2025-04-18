package org.example;

public class Inventory {

    private String partName;
    private int quantityAvailable;

    public Inventory(String name, int qty) {
        this.partName = name;
        this.quantityAvailable = qty;
    }

    public String getPartName() {
        return partName;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }
}
