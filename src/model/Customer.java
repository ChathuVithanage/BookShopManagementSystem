package model;

import javafx.scene.control.Button;

public class Customer {
    private String nic;
    private String name;
    private String address;
    private String Contact;
    private Button delete = new Button("delete");

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }


    public Customer() {
    }

    public Customer(String nic, String name, String address, String contact) {
        this.setNic(nic);
        this.setName(name);
        this.setAddress(address);
        this.setContact(contact);
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "nic='" + nic + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", Contact='" + Contact + '\'' +
                '}';
    }
}
