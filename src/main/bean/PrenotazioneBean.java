package main.bean;

import main.model.Prenotazione;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class PrenotazioneBean {

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private String currentUser;
    private String localita;
    private LocalDate startDate;
    private LocalDate endDate;
    private int numOspiti;
    private List<String> searchResults;

    public PrenotazioneBean(String localita, String startDate, String endDate, int numOspiti) throws DateTimeParseException{
        this.localita = localita;
        this.startDate = formatDate(startDate);
        this.endDate = formatDate(endDate);
        this.numOspiti = numOspiti;
    }

    public PrenotazioneBean(List<String> searchResults) {
        this.searchResults = searchResults;
    }

    public PrenotazioneBean(Prenotazione p){
        this.startDate = p.getStartDate();
        this.endDate = p.getEndDate();
        this.numOspiti = p.getNumOspiti();
    }

    public PrenotazioneBean(String currentUser){
        this.currentUser = currentUser;
    }

    private LocalDate formatDate(String strDate) throws DateTimeParseException {
        return LocalDate.parse(strDate, dateFormatter);
    }

    public String getCurrentUser() {
        return currentUser;
    }
    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public List<String> getSearchResults() {
        return searchResults;
    }
    public void setSearchResults(List<String> searchResults) {
        this.searchResults = searchResults;
    }

    public String getLocalita() {
        return localita;
    }
    public void setLocalita(String localita) {
        this.localita = localita;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) throws DateTimeParseException {
        this.startDate = formatDate(startDate);
    }

    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) throws DateTimeParseException {
        this.endDate = formatDate(endDate);
    }

    public int getNumOspiti() {
        return numOspiti;
    }
    public void setNumOspiti(int numOspiti) {
        this.numOspiti = numOspiti;
    }
}
