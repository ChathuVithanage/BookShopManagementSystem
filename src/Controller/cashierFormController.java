package Controller;

import db.CrudUtil;
import db.DataSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Cart;
import model.Customer;
import model.Order;
import model.employee;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class cashierFormController {

    public AnchorPane mainCashier;
    public Label lblDate;
    public Button addToCartBtn;

    Stage casheir;

    static ObservableList<Cart> cartLst = FXCollections.observableArrayList();

    String NameRegex = "^[A-Z]\\.[A-Z]\\.[A-Z][a-z]{3,15}$";
    String idRegex = "^[0-9]{12}$";
    String tpRegex = "^[0-9]{10}$";
    String addressRegex = "(^[0-9]{1,3}\\/[A-Z][a-z]{3,15}\\/[A-Z][a-z]{3,15}$)|(^[0-9]{1,3}[A-Z]\\/[A-Z][a-z]{3,15}\\/[A-Z][a-z]{3,15}$)";

    private String forValid = "-fx-border-color: green;";
    private String forInvalid = "-fx-border-color: red;";
    
    //customer details
    public TextField txtCustomerNic;
    public TextField txtCustomerName;
    public TextField txtCustomerAddress;
    public TextField txtSContact;

    //order table
    public TableView<Order> tblOrder;
    public TableColumn colCusNic;
    public TableColumn colCusName;
    public TableColumn colBookId;
    public TableColumn colBookName;
    public TableColumn colSellingPrice;
    public TableColumn colBuyQuantity;
    public TableColumn colDate;
    public TableColumn colTotalCost;
    
    //book details order form
    public ComboBox cmbBookId;
    public TextField txtBookName;
    public TextField txtSellingPrice;
    public TextField txtQuantity;
    public TextField txtBuyQuantity;

    //Customer table
    public TableView<Customer> tblCustomer;
    public TableColumn colCtNic;
    public TableColumn colCtName;
    public TableColumn colCtAddress;
    public TableColumn colCtContact;
    public TableColumn colCtOptions;

    // cart table
    public TableView<Cart> tblCart;
    public TableColumn cartId;
    public TableColumn cartName;
    public TableColumn cartPrice;
    public TableColumn cartQuantity;
    public TableColumn cartOptions;

    private boolean isNicValidated = false;
    private boolean isNameValidated = false;
    private boolean isAddressValidated = false;
    private boolean isContactValidated = false;

    public boolean validator(String pattern, String matcher){
        Pattern pat = Pattern.compile(pattern);
        Matcher mat = pat.matcher(matcher);
        boolean matchFound = mat.find();
        return matchFound;
    }


    public void initialize() throws SQLException, ClassNotFoundException {

        ObservableList<String> bookIds = FXCollections.observableArrayList();
        ResultSet ids = CrudUtil.execute("SELECT * FROM store");
        while(ids.next()){
            bookIds.add(ids.getString(1));
        }
        cmbBookId.setItems(bookIds);


        colCtNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colCtName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCtAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCtContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colCtOptions.setCellValueFactory(new PropertyValueFactory<>("delete"));

        cartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        cartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        cartQuantity.setCellValueFactory(new PropertyValueFactory<>("buyQuantity"));
        cartOptions.setCellValueFactory(new PropertyValueFactory<>("delete"));

        colCusNic.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colCusName.setCellValueFactory(new PropertyValueFactory<>("customerNic"));
        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        colBookName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colSellingPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colBuyQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTotalCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));

        loadAllCustomers();
        loadAllOrders();
        loadDate();
    }

    public void placeOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        CrudUtil.execute("INSERT INTO customer VALUES(?,?,?,?)",
                txtCustomerNic.getText(),
                txtCustomerName.getText(),
                txtCustomerAddress.getText(),
                txtSContact.getText()
                );


        for (Cart c: cartLst) {
            Double price = c.getPrice();
            int qty =  Integer.parseInt(c.getBuyQuantity());
            Double total = price * qty;
            CrudUtil.execute("INSERT INTO `order` VALUES(?,?,?,?,?,?,?,?)",
                    c.getId(),
                    txtCustomerNic.getText(),
                    c.getName(),
                    txtCustomerName.getText(),
                    price,
                    qty,
                    total,
                    lblDate.getText()
            );
            // decreasing quantity
            CrudUtil.execute("UPDATE Store SET Quantity=? WHERE Book_ID=?",c.getQuantity()-qty,c.getId());
        }
            loadAllCustomers();
            loadAllOrders();
            loadDate();
            cartLst.clear();
            txtCustomerNic.clear();
            txtCustomerName.clear();
            txtCustomerAddress.clear();
            txtSContact.clear();

    }
    //ObservableList<Cart> tmList = FXCollections.observableArrayList();

    public void addToCartOnAction(ActionEvent actionEvent){
        if(Integer.parseInt(txtQuantity.getText())>=Integer.parseInt(txtBuyQuantity.getText())){
            Cart cart = new Cart(
                    cmbBookId.getValue().toString(),
                    txtBookName.getText(),
                    Double.parseDouble(txtSellingPrice.getText()),
                    Integer.parseInt(txtQuantity.getText()),
                    txtBuyQuantity.getText(),
                    Integer.parseInt(txtBuyQuantity.getText())* Double.parseDouble(txtSellingPrice.getText())
            );
            cart.getDelete().setOnAction(e->{
                cartLst.remove(cart);
                tblCart.refresh();
            });
            cartLst.add(cart);

            tblCart.setItems(cartLst);
        }else {
            new Alert(Alert.AlertType.WARNING,"Your order exceeds books in the store");
        }


    }

    public void loadAllOrders() throws SQLException, ClassNotFoundException {
        ObservableList<Order> orderLst = FXCollections.observableArrayList();
        ResultSet orders = CrudUtil.execute("SELECT * FROM `order`");
        while(orders.next()){
            Order od = new Order(
              orders.getString(2),
                    orders.getString(4),
                    orders.getString(1),
                    orders.getString(3),
                    orders.getDouble(5),
                    orders.getInt(6),
                    orders.getDouble(7),
                    orders.getString(8)
                    );
            orderLst.add(od);
        }
        tblOrder.setItems(orderLst);
    }


    public void loadAllCustomers() throws SQLException, ClassNotFoundException {
        ResultSet r = CrudUtil.execute("SELECT * FROM customer");
        ObservableList<Customer> cusList = FXCollections.observableArrayList();
        while(r.next()){
            Customer c = new Customer(
                    r.getString(1),
                    r.getString(2),
                    r.getString(3),
                    r.getString(4)

            );
            c.getDelete().setOnAction(e->{
                try {
                    CrudUtil.execute("DELETE FROM customer WHERE NIC=?",c.getNic());
                    loadAllCustomers();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
            });

            cusList.add(c);

        }

        tblCustomer.setItems(cusList);

    }

    public void bookSelectedOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ResultSet bk = CrudUtil.execute("SELECT * FROM store WHERE Book_ID=?",cmbBookId.getValue());
        if(bk.next()){
            txtBookName.setText(bk.getString(2));
            txtSellingPrice.setText(String.valueOf(bk.getDouble(6)));
            txtQuantity.setText(String.valueOf(bk.getInt(4)));
        }
    }

    public void cashierLogOutOnAction(ActionEvent actionEvent) throws IOException {
        casheir = (Stage) mainCashier.getScene().getWindow();
        casheir.close();
        casheir.setScene(new Scene(FXMLLoader.load(getClass().getResource("../Main/Dashboard.fxml"))));
        casheir.setResizable(false);
        casheir.show();
    }

    //Set Date
    private void loadDate(){
        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Order order = tblOrder.getSelectionModel().getSelectedItem();
        tblOrder.getItems().remove(order);

        CrudUtil.execute("DELETE FROM `order` WHERE Book_ID=? AND Customer_NIC=?",
                order.getBookId(),
                order.getCustomerNic()
                );
    }

    public void printBillOnAction(ActionEvent actionEvent) {


        try {
            JasperReport compiledReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/Main/Report/Prarthana_Book_Shop_Report.jasper"));
            ObservableList<Cart> items = tblCart.getItems();
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, new JRBeanCollectionDataSource(items));
            JasperViewer.viewReport(jasperPrint, false);


        } catch (JRException e) {
            e.printStackTrace();
        }
    }


    public void cusNicReleasedOnAction(KeyEvent keyEvent) {
        if(validator(idRegex,txtCustomerNic.getText())){
            txtCustomerNic.setStyle(forValid);
            isNicValidated=true;
        }else{
            txtCustomerNic.setStyle(forInvalid);
            isNicValidated=false;
        }
        checker();
    }

    public void cusNameReleasedOnAction(KeyEvent keyEvent) {
        if(validator(NameRegex,txtCustomerName.getText())){
            txtCustomerName.setStyle(forValid);
            isNameValidated=true;
        }else{
            txtCustomerName.setStyle(forInvalid);
            isNameValidated=false;
        }
        checker();
    }

    public void cusAddressReleasedOnAction(KeyEvent keyEvent) {
        if(validator(addressRegex,txtCustomerAddress.getText())){
            txtCustomerAddress.setStyle(forValid);
            isAddressValidated=true;
        }else{
            txtCustomerAddress.setStyle(forInvalid);
            isAddressValidated=false;
        }
        checker();
    }

    public void cusContactReleasedOnAction(KeyEvent keyEvent) {
        if(validator(tpRegex,txtSContact.getText())){
            txtSContact.setStyle(forValid);
            isContactValidated=true;
        }else{
            txtSContact.setStyle(forInvalid);
            isContactValidated=false;
        }
        checker();
    }

    private void checker(){
        if(isAddressValidated & isNameValidated & isContactValidated & isNicValidated){
            addToCartBtn.setDisable(false);
        }else {
            addToCartBtn.setDisable(true);
        }
    }
}
