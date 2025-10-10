package main.control.exceptions;

import javafx.scene.control.Alert;
import main.view.viewcli.ViewCliUtils;

public class UserDoesNotExistException extends EasyRentException {

    private final String email;

    public UserDoesNotExistException(String email) {
        this.email = email;
    }

    @Override
    public void showMessageGUI() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText("Errore di caricamento");
        alert.setContentText("L'utente " + email + " non esiste");
        alert.showAndWait();
    }

    @Override
    public void showMessageCLI() {
        ViewCliUtils.printMsgln("!ERRORE! L'utente " + email + "non esiste");
    }
}
