package main.control.exceptions;

import javafx.scene.control.Alert;
import main.view.viewcli.ViewCliUtils;

public class AnnuncioExistsException extends EasyRentException {

    private final String titolo;

    public AnnuncioExistsException(String titolo) {
        this.titolo = titolo;
    }

    @Override
    public void showMessageGUI() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText("Errore di caricamento");
        alert.setContentText("L'annuncio " + titolo + " è gia esistente");
        alert.showAndWait();
    }

    @Override
    public void showMessageCLI() {
        ViewCliUtils.printMsgln("!ERRORE! L'annuncio " + titolo + " è gia esistente");
    }
}
