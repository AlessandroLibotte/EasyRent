package main.view.viewgui.controllers;

import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import main.bean.LoginBean;
import main.control.LoginController;
import main.model.Role;

import java.io.IOException;

public class LoginViewController {

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    private final LoginController loginController;
    private final ViewControllerUtils viewControllerUtils;

    public LoginViewController() {
        loginController = new LoginController();
        viewControllerUtils =  new ViewControllerUtils();
    }

    public void login(ActionEvent event) throws Exception {

        String email = emailField.getText();
        String password = passwordField.getText();

        Role result = loginController.validate(new LoginBean(email, password));
        switch(result) {
            case AFFITTUARIO -> viewControllerUtils.goToAffittuario(event, email);
            case LOCATORE -> viewControllerUtils.goToLocatore(event, email);
            case INVALID -> viewControllerUtils.mostraErrore("Errore", "Impossibile eseguire il login");
        }

    }

    public void goToRegister(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/RegisterScene.fxml"));

        loader.setControllerFactory(param -> {
            if (param == RegisterViewController.class) {
                return new RegisterViewController(loginController);
            } else {
                try {
                    return param.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    viewControllerUtils.mostraErrore("Constructor Instancing", e.getMessage());
                    return null;
                }
            }
        });

        viewControllerUtils.loadSetStage(loader, event.getSource());

    }

}
