package main.view.viewgui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.bean.AnnuncioBean;
import main.bean.LoginBean;
import main.bean.PrenotazioneBean;
import main.control.AnnuncioController;
import main.control.PrenotazioneController;
import main.control.UserController;
import main.control.exceptions.NoAvailableAnnunciException;
import main.control.exceptions.UserDoesNotExistException;
import main.model.Role;

import java.io.IOException;

public class AnnuncioViewController {

    public Label titoloLabel;
    public Label locatoreLabel;
    public Label indirizzoLabel;
    public Label prezzoLabel;
    public Label stelleLabel;
    public Label serviziLabel;
    public Label descrizioneLabel;
    @FXML
    private Button  prenotaButton;

    private final String email;
    private final Role role;

    private final AnnuncioController annuncioController;
    private final PrenotazioneController prenotazioneController;
    private AnnuncioBean annBean;
    private final PrenotazioneBean prenBean;
    private final ViewControllerUtils viewControllerUtils;

    public AnnuncioViewController(String titolo, String email, PrenotazioneBean prenBean) throws IOException {

        annuncioController = new AnnuncioController();
        viewControllerUtils = new ViewControllerUtils();
        prenotazioneController = new PrenotazioneController();
        UserController userController = new UserController();
        this.prenBean = prenBean;

        this.email = email;
        this.role = userController.assertUser(new LoginBean(email));

        try {
            annBean = annuncioController.getAnnuncio(new AnnuncioBean(titolo));
        } catch (NoAvailableAnnunciException e){
            viewControllerUtils.mostraErrore("Errore", "Errore di caricmaneto","L'annuncio non esiste");
            switch (role){
                case Role.AFFITTUARIO -> {
                    Parent root = viewControllerUtils.loadAffittuarioScene(email).load();
                    viewControllerUtils.setShowScene(root, titoloLabel.getScene());
                }
                case Role.LOCATORE ->  {
                    Parent root = viewControllerUtils.loadLocatoreScene(email).load();
                    viewControllerUtils.setShowScene(root, titoloLabel.getScene());
                }
                case Role.INVALID -> {
                    viewControllerUtils.mostraErrore("Errore", "Ruolo non valido", "");
                    viewControllerUtils.gotoLogin(titoloLabel.getScene());
                }
            }
        }

    }

    @FXML
    public void initialize() throws IOException {

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

        switch(role){
            case Role.AFFITTUARIO -> prenotaButton.setText("Prenota");
            case Role.LOCATORE -> prenotaButton.setText("Elimina");
            case Role.INVALID -> {
                viewControllerUtils.mostraErrore("Errore", "Ruolo non valido", "");
                viewControllerUtils.gotoLogin(titoloLabel.getScene());
            }
        }

    }

    public void handleAnnulla(ActionEvent event) throws IOException {
        viewControllerUtils.handleIndietro(event, role, email);
    }

    public void handlePrenota(ActionEvent event) throws IOException {
        switch(role){
            case Role.AFFITTUARIO -> {
                try{
                    prenotazioneController.prenota(annBean, prenBean);
                } catch(NoAvailableAnnunciException e){
                    viewControllerUtils.mostraErrore("Errore", "Errore di caricamento", "L'annuncio non esiste");
                    viewControllerUtils.goToAffittuario(event, email);
                } catch (UserDoesNotExistException e){
                    viewControllerUtils.mostraErrore("Errore", "Utente insesistente", "L'utente " + e.email + " non esiste");
                    viewControllerUtils.gotoLogin(titoloLabel.getScene());
                }
                viewControllerUtils.goToAffittuario(event, email);
            }
            case  Role.LOCATORE -> {
                try {
                    annuncioController.eliminaAnnuncio(new AnnuncioBean(titoloLabel.getText(), email));
                } catch (UserDoesNotExistException e){
                    viewControllerUtils.mostraErrore("Errore", "Utente insesistente", "L'utente " + e.email + " non esiste");
                    viewControllerUtils.gotoLogin(titoloLabel.getScene());
                } catch(NoAvailableAnnunciException e){
                    viewControllerUtils.mostraErrore("Errore", "Errore di caricmaneto", "L'annuncio non esiste");
                }
                viewControllerUtils.goToLocatore(event, email);
            }
            case Role.INVALID -> {
                viewControllerUtils.mostraErrore("Errore", "Ruolo non valido", "");
                viewControllerUtils.gotoLogin(titoloLabel.getScene());
            }
        }

    }
}
