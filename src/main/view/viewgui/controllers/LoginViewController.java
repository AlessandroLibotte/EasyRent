package main.view.viewgui.controllers;

import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import main.bean.LoginBean;
import main.control.LoginController;

public class LoginViewController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private LoginController loginController;
    private ViewControllerUtils viewControllerUtils;

    public LoginViewController() {
        loginController = new LoginController();
        viewControllerUtils =  new ViewControllerUtils();
    }

    public void login(ActionEvent event) throws Exception {

        String email = emailField.getText();
        String password = passwordField.getText();

        int val = loginController.validate(new LoginBean(email, password));
        if (val == 1){
            viewControllerUtils.goToAffittuario(event, email);
        }
        else if (val == 2){
            viewControllerUtils.goToLocatore(event, email);
        }
        else {
            viewControllerUtils.mostraErrore("Errore Login", "Errore", "Impossibile eseguire il login");
        }

    }

    public void goToRegister(ActionEvent event) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/RegisterScene.fxml"));
        Parent root = loader.load();

        RegisterViewController  controller = loader.getController();
        controller.setLoginController(loginController);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.show();

    }

}
