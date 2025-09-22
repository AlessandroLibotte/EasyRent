package main.drivers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DriverGUI extends Application {

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/testScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200,800);
        stage.setTitle("EasyRent");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
