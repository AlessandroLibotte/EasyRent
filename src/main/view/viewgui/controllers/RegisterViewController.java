package main.view.viewgui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.bean.LoginBean;
import main.control.LoginController;

import java.io.IOException;
import java.util.Objects;

public class RegisterViewController {

    @FXML
    private TextField emailField, nomeField, cognomeField, telefonoField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private RadioButton affittuarioRadio, locatoreRadio, compagniaRadio;

    @FXML
    private Button registerButton, backButton;

    private ToggleGroup roleGroup;

    private LoginController loginController;

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    @FXML
    public void initialize() {

        roleGroup = new ToggleGroup();
        affittuarioRadio.setToggleGroup(roleGroup);
        locatoreRadio.setToggleGroup(roleGroup);
        compagniaRadio.setToggleGroup(roleGroup);
    }

    public void register(ActionEvent event) throws IOException {

        String email = emailField.getText();
        String password = passwordField.getText();
        String nome = nomeField.getText();
        String cognome = cognomeField.getText();
        String telefono = telefonoField.getText();

        RadioButton selectedRole = (RadioButton) roleGroup.getSelectedToggle();
        String ruolo = selectedRole != null ? selectedRole.getText() : "";

        int role = Objects.equals(ruolo, "Affittuario") ? 0 : 1;

        if (loginController.register(new LoginBean(nome, cognome, email, password, telefono, role))) {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/LoginScene.fxml")));

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.show();
        }
        else {

            mostraErroreRegistrazione("");

        }

    }

    public void back(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/LoginScene.fxml")));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.show();

    }

    private void mostraErroreRegistrazione(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore di Registrazione");
        alert.setHeaderText("Registrazione Fallita");
        alert.setContentText(messaggio);

        // Mostra il popup e aspetta che l'utente prema OK
        alert.showAndWait();
    }
}
