package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodTextRun;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class LoginFormController {
    public AnchorPane mainPane;
    public Button mnLoginBtn;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPwd;

    public void initialize(){
    }

    public void logOnAction(ActionEvent actionEvent)  throws IOException {
        if (txtName.getText().equals("chathu") & txtPwd.getText().equals("1234")) {
            Stage LoginForm = (Stage) mainPane.getScene().getWindow();
            LoginForm.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../Main/managementForm.fxml")));
            LoginForm.setScene(scene);
            LoginForm.show();
        }
        else{
            txtPwd.setText(null);
            txtName.setText(null);
            Alert alert = new Alert(Alert.AlertType.ERROR,"Sorry! Something went wrong");
            alert.show();
        }
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Stage LoginForm = (Stage) mainPane.getScene().getWindow();
        LoginForm.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../Main/Dashboard.fxml")));
        LoginForm.setScene(scene);
        LoginForm.show();
    }
}
