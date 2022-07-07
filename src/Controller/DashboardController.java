package Controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class DashboardController {
    public AnchorPane mainPane;
    public Label txtTime;
    public Label txtDate;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtUserName;

    public void initialize(){

        loadDateAndTime();

    }

    private void loadDateAndTime() {
        /*set Date*/
        txtDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        /*set Time*/
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            txtTime.setText(currentTime.getHour() + ":" +
                    currentTime.getMinute() + ":" +
                    currentTime.getSecond());
        }),
                new KeyFrame(javafx.util.Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void managementOnAction(ActionEvent actionEvent) throws IOException {
        Stage Dashboard = (Stage) mainPane.getScene().getWindow();
        Dashboard.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../Main/LoginForm.fxml")));;
        Dashboard.setScene(scene);
        Dashboard.show();
    }

    public void cashierLogOnAction(ActionEvent actionEvent) throws IOException {
        if (txtUserName.getText().equals("admin") & txtPassword.getText().equals("1234")) {
            Stage Dashboard = (Stage) mainPane.getScene().getWindow();
            Dashboard.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../Main/cashierForm.fxml")));
            Dashboard.setScene(scene);
            Dashboard.show();
        } else {
            txtPassword.setText(null);
            txtUserName.setText(null);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Sorry! Something went wrong");
            alert.show();
        }
    }

}

