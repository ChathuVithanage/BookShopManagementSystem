package model;

import javafx.scene.control.Button;

public class employee {
        private String nic;
        private String name;
        private String contact;
        private String address;
        private String age;
        private String salary;
        private Button delete = new Button("delete");

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }

    public employee(String nic, String name, String contact, String address, String age, String salary) {
            this.nic = nic;
            this.name = name;
            this.contact = contact;
            this.address = address;
            this.age = age;
            this.salary = salary;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }


    @Override
    public String toString() {
        return "employee{" +
                "nic='" + nic + '\'' +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", address='" + address + '\'' +
                ", age='" + age + '\'' +
                "salary='" + salary + '\'' +
                '}';
    }
}
