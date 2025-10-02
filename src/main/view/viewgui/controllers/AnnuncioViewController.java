package main.view.viewgui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.bean.AnnuncioBean;
import main.bean.PrenotazioneBean;
import main.control.AnnuncioController;
import main.control.PrenotazioneController;

import java.io.IOException;

public class AnnuncioViewController {

    public Label titoloLabel;
    public Label locatoreLabel;
    public Label indirizzoLabel;
    public Label prezzoLabel;
    public Label stelleLabel;
    public Label serviziLabel;
    public Label descrizioneLabel;

    private String email;

    private AnnuncioController annuncioController;
    private PrenotazioneController prenotazioneController;
    private AnnuncioBean annBean;
    private PrenotazioneBean prenBean;
    private ViewControllerUtils viewControllerUtils;

    public AnnuncioViewController(String titolo, String email, PrenotazioneBean prenBean) {

        this.email = email;
        annuncioController = new AnnuncioController();
        viewControllerUtils = new ViewControllerUtils();
        prenotazioneController = new PrenotazioneController();
        this.prenBean = prenBean;

        annBean = annuncioController.getAnnuncio(new AnnuncioBean(titolo, email));

    }

    @FXML
    public void initialize() {
        titoloLabel.setText(annBean.getTitolo());
        indirizzoLabel.setText(annBean.getIndirizzo());
        prezzoLabel.setText("€ " + annBean.getPrice());
        locatoreLabel.setText(annBean.getOwner());
        serviziLabel.setText(String.join(", ", annBean.getServizi()));
        descrizioneLabel.setText(annBean.getDescrizione());
        StringBuilder stelle = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            stelle.append(i < annBean.getVoto() ? "★" : "☆");
        }
        stelleLabel.setText(stelle.toString());
    }

    public void handleAnnulla(ActionEvent event) throws IOException {
        viewControllerUtils.goToAffittuario(event, email);
    }

    public void handlePrenota(ActionEvent event) {
        prenotazioneController.prenota(annBean, prenBean);
    }
}
