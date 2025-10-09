package main.control.exceptions;

import javafx.scene.control.Alert;
import main.view.viewcli.ViewCliUtils;

public class InputException extends RuntimeException {

    public void showMessageGUI() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText("Errore di compilamento");
        alert.setContentText("Uno o piu campi non sono stati compilati correttamente");
        alert.showAndWait();
    }

    public void showMessageCLI() {
        ViewCliUtils.printMsgln("!ERRORE! Uno o piu campi non sono stati compilati correttamente");
    }

}
