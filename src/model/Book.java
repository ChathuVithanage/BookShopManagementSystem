package model;

import javafx.scene.control.Button;

public class Book {
    private String bookId;
    private String name;
    private String author;
    private int quantity;
    private String costPrice;
    private String sellingPrice;
    private String category;
    private Button delete = new Button("delete");

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Book(String bookId, String name, String author, int quantity, String costPrice, String sellingPrice, String category) {
        this.bookId = bookId;
        this.name = name;
        this.author = author;
        this.quantity = quantity;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId='" + bookId + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", quantity=" + quantity +
                ", costPrice='" + costPrice + '\'' +
                ", sellingPrice='" + sellingPrice + '\'' +
                ", category='" + category + '\'' +
                ", delete=" + delete +
                '}';
    }
}
