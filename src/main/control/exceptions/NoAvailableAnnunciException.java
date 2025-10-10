package main.control.exceptions;

import javafx.scene.control.Alert;
import main.view.viewcli.ViewCliUtils;

public class NoAvailableAnnunciException extends EasyRentException {

    @Override
    public void showMessageGUI() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText("Errore di caricamento");
        alert.setContentText("Impossibile caricare l'\\gli annunco\\i");
        alert.showAndWait();
    }

    @Override
    public void showMessageCLI() {
        ViewCliUtils.printMsgln("!ERRORE! Impossibile caricare l'\\gli annunco\\i");
    }
}
