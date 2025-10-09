package main.view.viewgui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.bean.AnnuncioBean;
import main.control.AnnuncioController;
import main.control.exceptions.AnnuncioExistsException;
import main.control.exceptions.InputException;

import java.io.IOException;

public class CreaAnnuncioViewController {

    @FXML
    public TextField titoloField, serviziField, indirizzoField, prezzoField;
    @FXML
    public TextArea descrizioneArea;
    @FXML
    private Spinner ospitiSpinner;

    @FXML
    public void initialize() {
        ospitiSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1)
        );
    }

    private final ViewControllerUtils viewControllerUtils;
    private final AnnuncioController annuncioController;

    public CreaAnnuncioViewController() {
        annuncioController = new AnnuncioController();
        viewControllerUtils =  new ViewControllerUtils();
    }

    private String email;

    public void setEmail(String email) {
        this.email = email;
    }


    public void handleAnnulla(ActionEvent event) throws IOException {
        viewControllerUtils.goToLocatore(event, email);
    }

    public void handleCrea(ActionEvent event) throws IOException {

        String  titolo = titoloField.getText();
        String  indirizzo = indirizzoField.getText();
        String  descrizione = descrizioneArea.getText();
        String  servizi = serviziField.getText();
        int maxOspiti = (int) ospitiSpinner.getValue();
        double prezzo = Double.parseDouble(prezzoField.getText());

        AnnuncioBean newAnnuncio = new AnnuncioBean(titolo);

        newAnnuncio.setOwner(email);
        newAnnuncio.setIndirizzo(indirizzo);
        newAnnuncio.setDescrizione(descrizione);
        newAnnuncio.setServiziString(servizi);
        newAnnuncio.setMaxOspiti(maxOspiti);
        newAnnuncio.setPrice(prezzo);

        try {
            annuncioController.creaAnnuncio(newAnnuncio);
        } catch (AnnuncioExistsException e){
            viewControllerUtils.mostraErrore("Errore Creazione Annuncio", "Errore", "Annuncio gia esistente");
            return;
        } catch (InputException e){
            viewControllerUtils.mostraErrore("Errore Creazione Annuncio", "Errore", "Campi non validi");
            return;
        }

        viewControllerUtils.goToLocatore(event, email);

}


}
