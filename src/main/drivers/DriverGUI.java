package main.drivers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import main.persistence.PersistenceProvider;

public class DriverGUI extends Application {

    public static void main(String[] args) {

        PersistenceProvider.setPersistenceProvider("in memory");

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/LoginScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900,600);

        stage.setTitle("EasyRent");
        stage.setResizable(false);

        stage.setScene(scene);
        stage.show();
    }
}
