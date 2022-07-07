package model;

import javafx.scene.control.Button;

public class Cart {
    private String id;
    private String name;
    private Double price;
    private Integer Quantity;
    private String buyQuantity;
    private Double totalCost;
    private Button delete = new Button("delete");

    public Cart(String id, String name, Double price, Integer quantity, String buyQuantity, double totalCost) {
        this.id = id;
        this.name = name;
        this.price = price;
        Quantity = quantity;
        this.buyQuantity = buyQuantity;
        this.totalCost = totalCost;

    }

    @Override
    public String toString() {
        return "Cart{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", Quantity=" + Quantity +
                ", buyQuantity='" + buyQuantity + '\'' +
                ", totalCost=" + totalCost +
                ", delete=" + delete +
                '}';
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public Cart(String id, String name, Double price, Integer quantity, String buyQuantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        Quantity = quantity;
        this.buyQuantity = buyQuantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public String getBuyQuantity() {
        return buyQuantity;
    }

    public void setBuyQuantity(String buyQuantity1) {
        buyQuantity = buyQuantity1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }
}
