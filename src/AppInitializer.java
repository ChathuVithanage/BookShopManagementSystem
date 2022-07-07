import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static javafx.fxml.FXMLLoader.load;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    /*public void start(Stage Dashboard) throws IOException {
            Dashboard.setScene(new Scene(load(getClass().getResource("../Main/Dashboard.fxml"))));
            Dashboard.setResizable(false);
            Dashboard.show();

    }*/
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setScene ( new Scene ( FXMLLoader.load (Objects.requireNonNull(getClass().getResource("Main/Dashboard.fxml"))) ) );
        primaryStage.show ();
    }
}
