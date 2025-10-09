package main.control.exceptions;

import javafx.scene.control.Alert;
import main.view.viewcli.ViewCliUtils;

public class UserDoesNotExistException extends RuntimeException {

    public String email;

    public UserDoesNotExistException(String email) {
        this.email = email;
    }

    public void showMessageGUI() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText("Errore di caricamento");
        alert.setContentText("L'utente " + email + " non esiste");
        alert.showAndWait();
    }

    public void showMessageCLI() {
        ViewCliUtils.printMsgln("!ERRORE! L'utente " + email + "non esiste");
    }
}
