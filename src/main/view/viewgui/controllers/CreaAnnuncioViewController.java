package main.view.viewgui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.bean.AnnuncioBean;
import main.control.AnnuncioController;

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

    private ViewControllerUtils viewControllerUtils;
    private AnnuncioController annuncioController;

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


        if (annuncioController.creaAnnuncio(new AnnuncioBean(email, titolo, indirizzo, descrizione, servizi, maxOspiti, prezzo, 0))) {

            viewControllerUtils.goToLocatore(event, email);

        } else {
            viewControllerUtils.mostraErrore("Errore Creazione Annuncio", "Errore", "Impossibile creare annuncio");
        }

    }


}
