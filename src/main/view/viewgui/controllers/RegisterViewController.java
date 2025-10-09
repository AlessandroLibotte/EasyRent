package main.view.viewgui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import main.bean.RegistrationBean;
import main.control.LoginController;
import main.control.RegisterResult;
import main.model.Role;

import java.io.IOException;

public class RegisterViewController {

    @FXML
    private TextField emailField, nomeField, cognomeField, telefonoField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private RadioButton affittuarioRadio, locatoreRadio;
    private ToggleGroup roleGroup;

    private final LoginController loginController;
    private final ViewControllerUtils viewControllerUtils;

    public RegisterViewController(LoginController loginController){
        this.loginController = loginController;
        this.viewControllerUtils = new ViewControllerUtils();
    }

    @FXML
    public void initialize() {
        roleGroup = new ToggleGroup();
        affittuarioRadio.setToggleGroup(roleGroup);
        locatoreRadio.setToggleGroup(roleGroup);
    }

    public void register(ActionEvent event) throws IOException {

        String email = emailField.getText();
        String password = passwordField.getText();
        String nome = nomeField.getText();
        String cognome = cognomeField.getText();
        String telefono = telefonoField.getText();

        RadioButton selectedRole = (RadioButton) roleGroup.getSelectedToggle();
        String ruoloStr = selectedRole != null ? selectedRole.getText() : "";

        Role role = Role.fromString(ruoloStr);

        RegisterResult result = loginController.register(new RegistrationBean(nome, cognome, email, password, telefono, role));
        switch (result) {

            case SUCCESS:
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginScene.fxml"));
                viewControllerUtils.loadSetStage(loader, event.getSource());
                break;
            case INVALID:
                viewControllerUtils.mostraErrore("Errore", "Campi invalidi");
                break;
            case EXISTS:
                viewControllerUtils.mostraErrore("Errore", "Utente gia esistente");

        }

    }

    public void back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginScene.fxml"));
        viewControllerUtils.loadSetStage(loader, event.getSource());
    }
}
