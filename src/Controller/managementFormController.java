package Controller;

import db.CrudUtil;
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
import model.Book;
import model.employee;
import org.jfree.data.statistics.DefaultMultiValueCategoryDataset;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class managementFormController {

    public AnchorPane mainPane;
    public Label lblNoBooks;

    //employee validation
    public Button saveEmBtn;

    String nicRegex = "^[0-9]{10}$";
    String nameRegex = "^[A-Z]\\.[A-Z]\\.[A-Z][a-z]{3,15}$";
    String contactRegex = "^[0-9]{10}$";
    String addressRegex = "(^[0-9]{1,3}\\/[A-Z][a-z]{3,15}\\/[A-Z][a-z]{3,15}$)|(^[0-9]{1,3}[A-Z]\\/[A-Z][a-z]{3,15}\\/[A-Z][a-z]{3,15}$)";
    String ageRegex = "([1-9][0-9]|[1-9])";
    String salaryRegex =  "[0-9]+\\.[0-9]{2}";

    //book validation
    public Button bookAddBtn;

    String bookIdRegex = "B[0-9]{3}";
    String bookNameRegex = "[A-Z][a-z|']{2,15}\\s[A-Z][a-z]{2,15}";
    String authorRegex = "[A-Z][a-z|']{3,20}\\s[A-Z][a-z]{3,20}";
    String quantityRegex = "[0-9]+";
    String costPriceRegex = "[0-9]+\\.[0-9]{2}";
    String sellingPriceRegex = "[0-9]+\\.[0-9]{2}";


    private String forValid = "-fx-border-color: green;";
    private String forInvalid = "-fx-border-color: red;";

    public Label lblCountEmployee;
    public Label lblCountCategory;
    public Label lblCountBook;
    Stage management;

    // tab employee
    public TextField txtEmNic;
    public TextField txtEmName;
    public TextField txtContact;
    public TextField txtAddress;
    public TextField txtAge;
    public TextField txtSalary;

    // employee table
    public TableView<employee> employeeTbl;
    public TableColumn nicClm;
    public TableColumn nameClm;
    public TableColumn contactClm;
    public TableColumn addressClm;
    public TableColumn ageClm;
    public TableColumn salaryClm;
    public TableColumn optionsClm;

    // tab - add book
    public TextField txtId;
    public TextField txtBookName;
    public TextField txtAuthor;
    public TextField txtQty;
    public TextField txtCostPrice;
    public TextField txtSellPrice;
    public ComboBox<String> cmbCategory;

    // table-store
    public TableView<Book> booksTbl;
    public TableColumn bookIdClm;
    public TableColumn bookNameClm;
    public TableColumn bookAuthorClm;
    public TableColumn bookQtyClm;
    public TableColumn bookCpClm;
    public TableColumn bookSpClm;
    public TableColumn bookCategoryClm;
    public TableColumn bookOptionsClm;

    //tab-income
    public TextField txtDate;
    public TextField txtNoOfBooks;
    public TextField txtTotalSalary;
    public TextField txtTotalIncome;
    public TextField txtProfit;

    private boolean isNicValidated = false;
    private boolean isNameValidated = false;
    private boolean isAddressValidated = false;
    private boolean isContactValidated = false;
    private boolean isAgeValidated = false;
    private boolean isSalaryValidated = false;

    private boolean isIdValidated = false;
    private boolean isBookNameValidated = false;
    private boolean isAuthorValidated = false;
    private boolean isQtyValidated = false;
    private boolean isCostPriceValidated = false;
    private boolean isSellPriceValidated = false;

    public boolean validator(String pattern, String matcher){
        Pattern pat = Pattern.compile(pattern);
        Matcher mat = pat.matcher(matcher);
        boolean matchFound = mat.find();
        return matchFound;
    }


    public void initialize() throws SQLException, ClassNotFoundException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        txtDate.setText(dtf.format(LocalDate.now()));

        nicClm.setCellValueFactory(new PropertyValueFactory<>("nic"));
        nameClm.setCellValueFactory(new PropertyValueFactory<>("name"));
        contactClm.setCellValueFactory(new PropertyValueFactory<>("contact"));
        addressClm.setCellValueFactory(new PropertyValueFactory<>("address"));
        ageClm.setCellValueFactory(new PropertyValueFactory<>("age"));
        salaryClm.setCellValueFactory(new PropertyValueFactory<>("salary"));
        optionsClm.setCellValueFactory(new PropertyValueFactory<>("delete"));

        bookIdClm.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        bookNameClm.setCellValueFactory(new PropertyValueFactory<>("name"));
        bookAuthorClm.setCellValueFactory(new PropertyValueFactory<>("author"));
        bookQtyClm.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        bookCpClm.setCellValueFactory(new PropertyValueFactory<>("costPrice"));
        bookSpClm.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        bookCategoryClm.setCellValueFactory(new PropertyValueFactory<>("category"));
        bookOptionsClm.setCellValueFactory(new PropertyValueFactory<>("delete"));

        // setting up category combo
        ObservableList<String> category = FXCollections.observableArrayList();
        category.add("Literature");
        category.add("Romance");
        category.add("Horror");
        category.add("Fantacy");
        category.add("Mystery");
        category.add("Crime");
        category.add("Fiction");

        cmbCategory.setItems(category);

        loadAllEmployees();
        loadAllBooks();
        loadCountEmployee();
        //loadCountCategory();
        //loadCountBook();
        }

    public void loadAllEmployees() throws SQLException, ClassNotFoundException {
            ResultSet rs = CrudUtil.execute("SELECT * FROM employee");
            ObservableList<employee> emList = FXCollections.observableArrayList();
            while(rs.next()){
                employee em = new employee(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)
                );
                em.getDelete().setOnAction(e->{
                    try {
                        CrudUtil.execute("DELETE FROM employee WHERE NIC=?",em.getNic());
                        loadAllEmployees();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                });

                emList.add(em);

            }

            employeeTbl.setItems(emList);

        }

        public void EmSaveOnAction(ActionEvent actionEvent) {
            employee em = new employee(
                    txtEmNic.getText(),txtEmName.getText(), txtContact.getText(), txtAddress.getText(), txtAge.getText(), txtSalary.getText()
                    );


            try {
                if (CrudUtil.execute("INSERT INTO employee VALUES (?,?,?,?,?,?)",em.getNic(),em.getName(),em.getContact(),em.getAddress(),em.getAge(),em.getSalary())){
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").show();
                    loadAllEmployees();

                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }

        public void loadAllBooks() throws SQLException, ClassNotFoundException {
            ResultSet books = CrudUtil.execute("SELECT * FROM store");
            ObservableList<Book> bookList = FXCollections.observableArrayList();
            while(books.next()){
                Book bk = new Book(
                        books.getString(1),
                        books.getString(2),
                        books.getString(3),
                        books.getInt(4),
                        String.valueOf(books.getDouble(5)),
                        String.valueOf(books.getDouble(6)),
                        books.getString(7)
                );
                bk.getDelete().setOnAction(e->{
                    try {
                        CrudUtil.execute("DELETE FROM store WHERE Book_ID=?",bk.getBookId());
                        loadAllBooks();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                });
                bookList.add(bk);
            }
            booksTbl.setItems(bookList);
        }

    //adding book
    public void bookAddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        boolean isSaved = CrudUtil.execute("INSERT INTO store VALUES(?,?,?,?,?,?,?)",
                txtId.getText(),
                txtBookName.getText(),
                txtAuthor.getText(),
                Integer.parseInt(txtQty.getText()),
                Double.parseDouble(txtCostPrice.getText()),
                Double.parseDouble(txtSellPrice.getText()),
                cmbCategory.getValue()
                );

        if(isSaved){
            loadAllBooks();
            new Alert(Alert.AlertType.INFORMATION,"saved successfully").show();
            txtId.clear();
            txtBookName.clear();
            txtAuthor.clear();
            txtQty.clear();
            txtCostPrice.clear();
            txtSellPrice.clear();
        }
    }

    public void mnLogOutOnAction(ActionEvent actionEvent) throws IOException {
        management = (Stage) mainPane.getScene().getWindow();
        management.close();
        management.setScene(new Scene(FXMLLoader.load(getClass().getResource("../Main/Dashboard.fxml"))));
        management.setResizable(false);
        management.show();
    }

    public void bookSelectedOnAction(ActionEvent actionEvent)  {
       /* ResultSet result = CrudUtil.execute("SELECT * FROM store WHERE Book_ID=?",txtId.getText());
        if (result.next()) {
            txtBookName.setText(result.getString(2));
            txtAuthor.setText(result.getString(3));
            txtQty.setText(String.valueOf(result.getDouble(4)));
            txtCostPrice.setText(String.valueOf(result.getDouble(5)));
            txtSellPrice.setText(String.valueOf(result.getDouble(6)));
            cmbCategory.setValue(String.valueOf(result.getString(7)));
        } else {
            new Alert(Alert.AlertType.WARNING, "Empty Result").show();
        }*/
        try {
            ResultSet resultSet=CrudUtil.execute("SELECT * FROM store WHERE Book_ID=? ",txtId.getText());
            if(resultSet.next()){
                txtBookName.setText(resultSet.getString(2));
                txtAuthor.setText(resultSet.getString(3));
                txtQty.setText(resultSet.getString(4));
                txtCostPrice.setText(resultSet.getString(5));
                txtSellPrice.setText(resultSet.getString(6));
                cmbCategory.setValue(resultSet.getString(7));
            }else{
                new Alert(Alert.AlertType.WARNING,"Empty set").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void bookUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        update();

    }

    private void update() throws SQLException, ClassNotFoundException {
        Book bu = new Book(
                txtId.getText(),
                txtBookName.getText(),
                txtAuthor.getText(),
                Integer.parseInt(txtQty.getText()),
                txtCostPrice.getText(),
                txtSellPrice.getText(),
                cmbCategory.getValue()
        );


        try {
            boolean isUpdated= CrudUtil.execute("UPDATE store SET Name=? , Author=? , Quantity=?, Cost_Price=?, Selling_Price=?, Category=? WHERE Book_ID=?",bu.getName(),bu.getAuthor(),
                    bu.getQuantity(),bu.getCostPrice(),bu.getSellingPrice(),bu.getCategory(),bu.getBookId());
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
                txtId.clear();
                txtBookName.clear();
                txtAuthor.clear();
                txtQty.clear();
                txtCostPrice.clear();
                txtSellPrice.clear();
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        finally {
            loadAllBooks();
        }

    }


    public void EmUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        employee eu=new employee(
                txtEmNic.getText(), txtEmName.getText(), txtContact.getText(), txtAddress.getText(), txtAge.getText(), txtSalary.getText()
        );
            boolean isEmUpdated = CrudUtil.execute("UPDATE employee SET Name=?, Contact_No=?, Address=?, Age=?, Salary=? WHERE NIC=?",
                    eu.getName(), eu.getContact(), eu.getAddress(), eu.getAge(), eu.getSalary(), eu.getNic());
            if (isEmUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }

        }

    public void EmNicOnAction(ActionEvent actionEvent) {
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM employee WHERE NIC=? ", txtEmNic.getText());
            if (result.next()) {
                txtEmName.setText(result.getString(2));
                txtContact.setText(result.getString(3));
                txtAddress.setText(result.getString(4));
                txtAge.setText(result.getString(5));
                txtSalary.setText(result.getString(6));

            } else {
                new Alert(Alert.AlertType.WARNING, "Empty set").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void EmRefreshOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        loadAllEmployees();
    }


    public void dailyReportOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Double income = 0.0;
        Double expenses = 0.0;
        Double profit = 0.0;
        int bookCount = 0;
        ResultSet rs = CrudUtil.execute("SELECT Book_ID,Total,Quantity FROM `Order` WHERE Date=?",txtDate.getText());
        while(rs.next()){
            bookCount += rs.getInt(3);
            income += rs.getDouble(2);
            ResultSet rs2 = CrudUtil.execute("SELECT Cost_Price FROM Store WHERE Book_ID=?",rs.getString(1));
            if(rs2.next()){
                expenses += rs.getInt(3)*rs2.getDouble(1);
            }
        }
//        ResultSet rs3 = CrudUtil.execute("SELECT Salary FROM Employee");
//        while(rs3.next()){
//            expenses += Double.parseDouble(rs3.getString(1));
//        }
        profit = income-expenses;
        txtNoOfBooks.setText(String.valueOf(bookCount));
        txtTotalSalary.setText(String.valueOf(expenses));
        txtTotalIncome.setText(String.valueOf(income));
        txtProfit.setText(String.valueOf(profit));

    }

    public void weeklyReportOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Double income = 0.0;
        Double expenses = 0.0;
        Double profit = 0.0;
        int bookCount = 0;
        ResultSet rs = CrudUtil.execute("SELECT Book_ID,Total,Quantity FROM `Order` WHERE DATEDIFF(Date,?)<=7",txtDate.getText());
        while(rs.next()){
            bookCount += rs.getInt(3);
            income += rs.getDouble(2);
            ResultSet rs2 = CrudUtil.execute("SELECT Cost_Price FROM Store WHERE Book_ID=?",rs.getString(1));
            if(rs2.next()){
                expenses += rs.getInt(3)*rs2.getDouble(1);
            }
        }
//        ResultSet rs3 = CrudUtil.execute("SELECT Salary FROM Employee");
//        while(rs3.next()){
//            expenses += Double.parseDouble(rs3.getString(1));
//        }
        profit = income-expenses;
        txtNoOfBooks.setText(String.valueOf(bookCount));
        txtTotalSalary.setText(String.valueOf(expenses));
        txtTotalIncome.setText(String.valueOf(income));
        txtProfit.setText(String.valueOf(profit));
    }

    public void loadCountEmployee() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Prarthana_Book_Shop ", "root", "1234");
        String sql="SELECT COUNT(*) FROM employee";
        Statement stm=con.createStatement();
        ResultSet resultSet=stm.executeQuery(sql);
        if(resultSet.next()) {
            lblCountEmployee.setText(String.valueOf(resultSet.getInt(1)));
        }
    }


    public void emNicReleasedOnAction(KeyEvent keyEvent) {
        if(validator(nicRegex,txtEmNic.getText())){
            txtEmNic.setStyle(forValid);
            isNicValidated=true;
        }else{
            txtEmNic.setStyle(forInvalid);
            isNicValidated=false;
        }
        checker();
    }

    public void emNameReleasedOnAction(KeyEvent keyEvent) {
        if(validator(nameRegex,txtEmName.getText())){
            txtEmName.setStyle(forValid);
            isNameValidated=true;
        }else{
            txtEmName.setStyle(forInvalid);
            isNameValidated=false;
        }
        checker();
    }

    public void emContactReleasedOnAction(KeyEvent keyEvent) {
        if(validator(contactRegex,txtContact.getText())){
            txtContact.setStyle(forValid);
            isContactValidated=true;
        }else{
            txtContact.setStyle(forInvalid);
            isContactValidated=false;
        }
        checker();
    }

    public void emAddressReleasedOnAction(KeyEvent keyEvent) {
        if(validator(addressRegex,txtAddress.getText())){
            txtAddress.setStyle(forValid);
            isAddressValidated=true;
        }else{
            txtAddress.setStyle(forInvalid);
            isAddressValidated=false;
        }
        checker();
    }

    public void emAgeReleasedOnAction(KeyEvent keyEvent) {
        if(validator(ageRegex,txtAge.getText())){
            txtAge.setStyle(forValid);
            isAgeValidated=true;
        }else{
            txtAge.setStyle(forInvalid);
            isAgeValidated=false;
        }
        checker();
    }

    public void emSalaryReleasedOnAction(KeyEvent keyEvent) {
        if(validator(salaryRegex,txtSalary.getText())){
            txtSalary.setStyle(forValid);
            isSalaryValidated=true;
        }else{
            txtSalary.setStyle(forInvalid);
            isSalaryValidated=false;
        }
        checker();
    }

    private void checker(){
        if(isAddressValidated & isNameValidated & isContactValidated & isNicValidated & isAgeValidated & isSalaryValidated){
            saveEmBtn.setDisable(false);
        }else {
            saveEmBtn.setDisable(true);
        }
    }

    public void bookIdReleasedOnAction(KeyEvent keyEvent) {
        if(validator(bookIdRegex,txtId.getText())){
            txtId.setStyle(forValid);
            isIdValidated=true;
        }else{
            txtId.setStyle(forInvalid);
            isIdValidated=false;
        }
        bookChecker();
    }

    public void bookNameReleasedOnAction(KeyEvent keyEvent) {
        if(validator(bookNameRegex,txtBookName.getText())){
            txtBookName.setStyle(forValid);
            isBookNameValidated=true;
        }else{
            txtEmNic.setStyle(forInvalid);
            isBookNameValidated=false;
        }
        bookChecker();
    }

    public void authorReleasedOnAction(KeyEvent keyEvent) {
        if(validator(authorRegex,txtAuthor.getText())){
            txtAuthor.setStyle(forValid);
            isAuthorValidated=true;
        }else{
            txtAuthor.setStyle(forInvalid);
            isAuthorValidated=false;
        }
        bookChecker();
    }

    public void quantityReleasedOnAction(KeyEvent keyEvent) {
        if(validator(quantityRegex,txtQty.getText())){
            txtQty.setStyle(forValid);
            isQtyValidated=true;
        }else{
            txtQty.setStyle(forInvalid);
            isQtyValidated=false;
        }
        bookChecker();
    }

    public void costPriceReleasedOnAction(KeyEvent keyEvent) {
        if(validator(costPriceRegex,txtCostPrice.getText())){
            txtCostPrice.setStyle(forValid);
            isCostPriceValidated=true;
        }else{
            txtCostPrice.setStyle(forInvalid);
            isCostPriceValidated=false;
        }
        bookChecker();
    }

    public void sellPriceReleasedOnAction(KeyEvent keyEvent) {
        if(validator(sellingPriceRegex,txtSellPrice.getText())){
            txtSellPrice.setStyle(forValid);
            isSellPriceValidated=true;
        }else{
            txtSellPrice.setStyle(forInvalid);
            isSellPriceValidated=false;
        }
        bookChecker();
    }

    private void bookChecker(){
        if(isIdValidated & isBookNameValidated & isAuthorValidated & isQtyValidated & isCostPriceValidated & isSellPriceValidated){
            bookAddBtn.setDisable(false);
        }else {
            bookAddBtn.setDisable(true);
        }
    }
}
