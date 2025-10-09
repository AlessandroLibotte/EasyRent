package main.view.viewgui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import main.bean.*;
import main.control.AnnuncioController;
import main.control.PrenotazioneController;
import main.control.UserController;
import main.control.exceptions.InputException;
import main.control.exceptions.NoAvailableAnnunciException;
import main.control.exceptions.UserDoesNotExistException;
import main.model.Role;

import java.io.IOException;
import java.util.Objects;

public class ProfiloViewController {

    public Label emailLabel;
    public Label nomeLabel;
    public Label cognomeLabel;
    public Label telefonoLabel;
    public VBox storicoPrenotazioniBox;
    @FXML
    private Button modificaButton;
    private TextField nomeField;
    private TextField cognomeField;
    private TextField telefonoField;

    private boolean inModifica = false;
    private final String email;
    private final Role role;

    private final UserController userController;
    private final ViewControllerUtils viewControllerUtils;
    private final PrenotazioneController  prenotazioneController;
    private final AnnuncioController annuncioController;

    public ProfiloViewController(String email) {

        this.userController = new UserController();
        this.viewControllerUtils = new ViewControllerUtils();
        this.prenotazioneController = new PrenotazioneController();
        this.annuncioController = new AnnuncioController();

        this.email = email;
        this.role = userController.assertUser(new LoginBean(email));

    }

    @FXML
    public void initialize() throws IOException {

        RegistrationBean user;
        try {
            user = userController.getUserInfo(new LoginBean(email));
        } catch(UserDoesNotExistException e) {
            viewControllerUtils.mostraErrore("Errore", "Utente inesistente", "L'utente " + e.email + " non esiste");
            viewControllerUtils.gotoLogin(emailLabel.getScene());
            return;
        }

        String nome = Objects.equals(user.getNome(), "") ? "N.D." :  user.getNome();
        String cognome = Objects.equals(user.getCognome(), "") ? "N.D." : user.getCognome();
        String telefono =  Objects.equals(user.getTelefono(), "") ? "N.D." : user.getTelefono();

        emailLabel.setText(user.getEmail());
        nomeLabel.setText(nome);
        cognomeLabel.setText(cognome);
        telefonoLabel.setText(telefono);

        switch(role) {
            case Role.AFFITTUARIO -> loadStoricoPrenotazioniAffittuario();
            case Role.LOCATORE -> loadStoricoPrenotazioniLocatore();
            case Role.INVALID -> {
                viewControllerUtils.mostraErrore("Errore", "Ruolo non valido", "");
                viewControllerUtils.gotoLogin(emailLabel.getScene());
            }
        }

    }

    private void loadStoricoPrenotazioniAffittuario() throws IOException {

        PrenotazioneBean prens;
        try{
            prens = prenotazioneController.getPrenotazioni(new PrenotazioneBean(email));
        } catch (UserDoesNotExistException e){
            viewControllerUtils.mostraErrore("Errore", "Utente insesistente", "L'utente " + e.email + " non esiste");
            viewControllerUtils.gotoLogin(emailLabel.getScene());
            return;
        }

        for (String title : prens.getTitoli()) {

            AnnuncioBean ann;
            try{
                ann = annuncioController.getAnnuncio(new AnnuncioBean(title));
            } catch (NoAvailableAnnunciException e){
                viewControllerUtils.mostraErrore("Errore", "Errore di caricamento", "L'annuncio non esiste");
                continue;
            }

            String periodo = prens.getDateInizio().get(prens.getTitoli().indexOf(title)).toString() + " - " +
                    prens.getDateFine().get(prens.getTitoli().indexOf(title)).toString();

            int ospiti = prens.getNumeriOspiti().get(prens.getTitoli().indexOf(title));

            storicoPrenotazioniBox.getChildren().add(creaCardPrenotazione(title, "", ann.getIndirizzo(), periodo, ospiti));
        }

    }

    private void loadStoricoPrenotazioniLocatore(){

        AnnuncioBean bean = new AnnuncioBean();
        bean.setOwner(email);

        AnnuncioResultBean anns = annuncioController.getAllAnnunci(bean);

        for(String titolo: anns.getTitoliAnnunci()){

            PrenotazioneBean prens;
            try {
                prens = annuncioController.getPrenotazioniAnnuncio(new AnnuncioBean(titolo));
            } catch (NoAvailableAnnunciException e){
                viewControllerUtils.mostraErrore("Errore", "Errore di caricamento", "L'annuncio non esiste");
                continue;
            }

            String indirizzo = anns.getIndirizziAnnunci().get(anns.getTitoliAnnunci().indexOf(titolo));

            for(String prenotante: prens.getPrenotanti()){

                String periodo = prens.getDateInizio().get(prens.getPrenotanti().indexOf(prenotante)).toString() + " - " +
                        prens.getDateFine().get(prens.getPrenotanti().indexOf(prenotante)).toString();

                int ospiti = prens.getNumeriOspiti().get(prens.getPrenotanti().indexOf(prenotante));

                storicoPrenotazioniBox.getChildren().add(creaCardPrenotazione(titolo, prenotante, indirizzo, periodo, ospiti));

            }

        }

    }

    public void handleModifica(ActionEvent event) {

        if (!inModifica) {

            inModifica = true;
            modificaButton.setText("Salva");

            nomeField = new TextField(nomeLabel.getText());
            cognomeField = new TextField(cognomeLabel.getText());
            telefonoField = new TextField(telefonoLabel.getText());

            viewControllerUtils.replaceNode(nomeLabel, nomeField);
            viewControllerUtils.replaceNode(cognomeLabel, cognomeField);
            viewControllerUtils.replaceNode(telefonoLabel, telefonoField);

        } else {

            RegistrationBean rb = new RegistrationBean(nomeField.getText(), cognomeField.getText(), email, "", telefonoField.getText(), Role.INVALID);

            try {
                userController.editUserInfo(rb);
            } catch (InputException e) {
                viewControllerUtils.mostraErrore("Errore", "Campi non validi", "");
                return;
            } catch (UserDoesNotExistException e) {
                viewControllerUtils.mostraErrore("Errore", "Utente insesistente", "L'utente " + e.email + " non esiste");
                return;
            }

            inModifica = false;
            modificaButton.setText("Modifica Dati");

            nomeLabel.setText(nomeField.getText());
            cognomeLabel.setText(cognomeField.getText());
            telefonoLabel.setText(telefonoField.getText());

            viewControllerUtils.replaceNode(nomeField, nomeLabel);
            viewControllerUtils.replaceNode(cognomeField, cognomeLabel);
            viewControllerUtils.replaceNode(telefonoField, telefonoLabel);

        }
    }

    public void handleIndietro(ActionEvent event) throws IOException {
        viewControllerUtils.handleIndietro(event, role, email);
    }

    @FXML
    private HBox creaCardPrenotazione(String titolo, String prenotante, String indirizzo, String periodo, int ospiti) {
        // Contenitore principale della card
        HBox card = new HBox(15);
        card.setStyle("-fx-background-color: #f9f9f9; -fx-background-radius: 10;" +
                "-fx-border-color: #c6d9f1; -fx-border-radius: 10;" +
                "-fx-padding: 10;");
        card.setPrefHeight(100);

        // Placeholder immagine
        Region immaginePlaceholder = new Region();
        immaginePlaceholder.setPrefWidth(100);
        immaginePlaceholder.setPrefHeight(80);
        immaginePlaceholder.setStyle("-fx-background-color: #b3d9ff; -fx-background-radius: 8;");

        // Box contenente i dettagli
        VBox dettagliBox = new VBox(5);

        Label titoloLabel = new Label();

        if (userController.assertUser(new LoginBean(email)) == Role.AFFITTUARIO) titoloLabel.setText(titolo);
        else titoloLabel.setText(titolo + " - A carico di: " + prenotante);

        titoloLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");

        Label indirizzoLabel = new Label(indirizzo);
        indirizzoLabel.setStyle("-fx-text-fill: #555;");

        Label periodoLabel = new Label("Periodo: " + periodo);
        periodoLabel.setStyle("-fx-text-fill: #777; -fx-font-size: 12;");

        Label ospitiLabel = new Label("Ospiti: " + ospiti);
        ospitiLabel.setStyle("-fx-text-fill: #777; -fx-font-size: 12;");

        dettagliBox.getChildren().addAll(titoloLabel, indirizzoLabel, periodoLabel, ospitiLabel);

        card.getChildren().addAll(immaginePlaceholder, dettagliBox);

        return card;
    }

}
