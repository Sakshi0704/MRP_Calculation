package org.example;

public class Part {

    private String name;
    private int quantityRequiredPerBicycle;

    public Part(String name, int qty) {
        this.name = name;
        this.quantityRequiredPerBicycle = qty;
    }

    public String getName() {
        return name;
    }

    public int getQuantityRequiredPerBicycle() {
        return quantityRequiredPerBicycle;
    }
}
