package main.view.viewgui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.bean.AnnuncioResultBean;
import main.bean.PrenotazioneBean;
import main.control.AnnuncioController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class AffittuarioViewController {

    public TextField localitaField;
    public DatePicker dataInizioField;
    public DatePicker dataFineField;
    public TextField numeroOspitiField;
    public TilePane annunciTilePane;
    private final String email;
    private final AnnuncioController  annuncioController;
    private final ViewControllerUtils viewControllerUtils;

    private String localita;
    private LocalDate startDate;
    private LocalDate endDate;
    private int numOspiti;

    public AffittuarioViewController(String email) {
        this.email = email;
        annuncioController = new AnnuncioController();
        viewControllerUtils = new ViewControllerUtils();
    }


    public void handleLogOff(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/LoginScene.fxml")));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.show();

    }

    public void handleViewProfile(ActionEvent event) throws IOException {

        viewControllerUtils.goToProfilo(event, email);

    }

    public void handleSearchAnnuncio(ActionEvent event) {

        localita = localitaField.getText();

        if (localita.isEmpty()) {
            viewControllerUtils.mostraErrore("Erroe di ricerca", "Errore", "Località non valida");
            return;
        }

        startDate = dataInizioField.getValue();
        endDate = dataFineField.getValue();
        numOspiti = Integer.parseInt(Objects.equals(numeroOspitiField.getText(), "") ? "1" : numeroOspitiField.getText());

        AnnuncioResultBean anns = annuncioController.searchAnnunci(new PrenotazioneBean(localita, startDate, endDate, numOspiti));

        List<String> titles = anns.getTitoliAnnunci();
        List<String> indirizzi = anns.getIndirizziAnnunci();
        List<Integer> voti = anns.getVotiAnnunci();
        List<Double> prezzi = anns.getPrezziAnnunci();

        annunciTilePane.getChildren().clear();

        for (String title : titles){
            int index = titles.indexOf(title);
            aggiungiNuovoAnnuncio(title, indirizzi.get(index), prezzi.get(index),  voti.get(index));
        }

    }

    public void aggiungiNuovoAnnuncio(String titolo, String indirizzo, double prezzo, int valutazione) {
        VBox nuovaCard = viewControllerUtils.creaCardAnnuncio(titolo, indirizzo, prezzo, valutazione);
        nuovaCard.setOnMouseClicked(event -> {
            try {
                goToAnnuncio(event, titolo);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        annunciTilePane.getChildren().add(nuovaCard);
    }

    private void goToAnnuncio(MouseEvent event, String titolo) throws IOException {

        PrenotazioneBean prenBean = new PrenotazioneBean(localita, startDate, endDate, numOspiti);
        prenBean.setCurrentUser(email);

        viewControllerUtils.goToAnnuncio(event, email, titolo, prenBean);

    }
}
