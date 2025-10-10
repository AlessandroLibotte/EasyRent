package main.view.viewgui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import main.bean.AnnuncioBean;
import main.bean.AnnuncioResultBean;
import main.control.AnnuncioController;
import main.control.exceptions.NoAvailableAnnunciException;
import main.control.exceptions.UserDoesNotExistException;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class LocatoreViewController {

    private final String email;
    public AnchorPane rootContainer;
    AnnuncioController annuncioController;
    ViewControllerUtils viewControllerUtils;
    List<String> titles;
    List<String> indirizzi;
    List<Integer> voti;
    List<Double> prezzi;

    public LocatoreViewController(String email){

        annuncioController = new AnnuncioController();
        viewControllerUtils = new ViewControllerUtils();
        this.email = email;

    }

    @FXML
    public TilePane annunciTilePane;

    @FXML
    public void initialize() throws IOException {

        AnnuncioBean bean  = new AnnuncioBean();
        bean.setOwner(email);

        AnnuncioResultBean anns;

        try {
            anns = annuncioController.getAllAnnunci(bean);
        } catch (UserDoesNotExistException e){
            e.showMessageGUI();
            viewControllerUtils.gotoLogin(rootContainer.getScene());
            return;
        } catch (NoAvailableAnnunciException e){
            e.showMessageGUI();
            return;
        }

        titles = anns.getTitoliAnnunci();
        indirizzi = anns.getIndirizziAnnunci();
        voti = anns.getVotiAnnunci();
        prezzi = anns.getPrezziAnnunci();

        for (String title : titles){
            int index = titles.indexOf(title);
            aggiungiNuovoAnnuncio(title, indirizzi.get(index), prezzi.get(index),  voti.get(index));
        }

    }

    @FXML
    private void handleViewProfile(ActionEvent event) throws IOException {

        viewControllerUtils.goToProfilo(event, email);

    }

    @FXML
    private void handleLogOff(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/LoginScene.fxml")));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.show();

    }

    public void handleCreateAnnuncio(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/CreaAnnuncioScene.fxml"));
        Parent root = loader.load();

        CreaAnnuncioViewController controller = loader.getController();
        controller.setEmail(email);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.show();

    }

    public void aggiungiNuovoAnnuncio(String titolo, String indirizzo, double prezzo, int valutazione) {
        VBox nuovaCard = viewControllerUtils.creaCardAnnuncio(titolo, indirizzo, prezzo, valutazione);
        nuovaCard.setOnMouseClicked(event -> {
            try {
                viewControllerUtils.goToAnnuncio(event, email, titolo, null);
            } catch (IOException e) {
                viewControllerUtils.mostraErrore("IO Exception", e.getMessage());
            }
        });
        annunciTilePane.getChildren().add(nuovaCard);
    }
}
