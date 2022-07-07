package model;

public class Order {
    private String bookId;
    private String customerNic;
    private String bookName;
    private String customerName;
    private Double price;
    private int Quantity;
    private String date;
    private Double totalCost;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getCustomerNic() {
        return customerNic;
    }

    public void setCustomerNic(String customerNic) {
        this.customerNic = customerNic;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Order(String bookId, String customerNic, String bookName, String customerName, Double price, int quantity, Double totalCost, String date) {
        this.bookId = bookId;
        this.customerNic = customerNic;
        this.bookName = bookName;
        this.customerName = customerName;
        this.price = price;
        Quantity = quantity;
        this.totalCost = totalCost;
        this.date = date;
    }

}
