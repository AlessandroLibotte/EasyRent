package main.view.viewgui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewControllerUtils {

    public void goToLocatore(ActionEvent event, String email) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LocatoreScene.fxml"));

        loader.setControllerFactory(param -> {
            if (param == LocatoreViewController.class) {
                return new LocatoreViewController(email);
            } else {
                try {
                    return param.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.show();

    }

    public void goToAffittuario(ActionEvent event, String email) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffittuarioScene.fxml"));

        loader.setControllerFactory(param -> {
            if (param == AffittuarioViewController.class) {
                return new AffittuarioViewController(email);
            } else {
                try {
                    return param.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.show();

    }

    public void mostraErrore(String title, String header, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }

    public VBox creaCardAnnuncio(String titolo, String indirizzo, double prezzo, int valutazione) {
        VBox card = new VBox(8);
        card.setPrefWidth(250);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; " +
                "-fx-border-radius: 10; -fx-border-color: #c6d9f1;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 6, 0, 0, 2);" +
                "-fx-padding: 10;");

        // Immagine
        Region immaginePlaceholder = new Region();
        immaginePlaceholder.setPrefHeight(120);
        immaginePlaceholder.setStyle("-fx-background-color: #b3d9ff; -fx-background-radius: 8;");

        // Titolo e Prezzo
        Label titoloLabel = new Label(titolo);
        titoloLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");

        Label prezzoLabel = new Label(String.format("€ %.2f / notte", prezzo));
        prezzoLabel.setStyle("-fx-font-size: 13; -fx-font-weight: bold; -fx-text-fill: #2b5797;");

        HBox titoloPrezzoBox = new HBox();
        titoloPrezzoBox.setAlignment(Pos.CENTER_LEFT);
        titoloPrezzoBox.setSpacing(10);
        titoloPrezzoBox.getChildren().addAll(titoloLabel, new Region(), prezzoLabel);
        HBox.setHgrow(titoloPrezzoBox.getChildren().get(1), Priority.ALWAYS); // spinge il prezzo a destra

        // Indirizzo e Stelle
        Label indirizzoLabel = new Label(indirizzo);
        indirizzoLabel.setStyle("-fx-text-fill: #555;");

        StringBuilder stelle = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            stelle.append(i < valutazione ? "★" : "☆");
        }
        Label stelleLabel = new Label(stelle.toString());
        stelleLabel.setStyle("-fx-text-fill: #f1c40f; -fx-font-size: 13;");

        HBox indirizzoStelleBox = new HBox();
        indirizzoStelleBox.setAlignment(Pos.CENTER_LEFT);
        indirizzoStelleBox.setSpacing(10);
        indirizzoStelleBox.getChildren().addAll(indirizzoLabel, new Region(), stelleLabel);
        HBox.setHgrow(indirizzoStelleBox.getChildren().get(1), Priority.ALWAYS); // spinge le stelle a destra

        // Composizione finale della card
        card.getChildren().addAll(immaginePlaceholder, titoloPrezzoBox, indirizzoStelleBox);

        return card;
    }

    public void replaceNode(Node oldNode, Node newNode) {
        Pane parent = (Pane) oldNode.getParent();
        int index = parent.getChildren().indexOf(oldNode);
        parent.getChildren().set(index, newNode);
    }

    public void goToProfilo(ActionEvent event, String email) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProfiloScene.fxml"));

        loader.setControllerFactory(param -> {
            if (param == ProfiloViewController.class) {
                return new ProfiloViewController(email);
            } else {
                try {
                    return param.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.show();

    }

}
