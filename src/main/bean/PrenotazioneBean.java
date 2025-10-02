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

    private List<String> prenotanti;
    private List<String> titoli;
    private List<LocalDate> dateInizio;
    private List<LocalDate> dateFine;
    private List<Integer> numeriOspiti;

    public PrenotazioneBean(List<String> prenotanti, List<String> titoli, List<LocalDate> dateInizio, List<LocalDate> dateFine, List<Integer> numeriOspiti) {
        this.prenotanti = prenotanti;
        this.titoli = titoli;
        this.dateInizio = dateInizio;
        this.dateFine = dateFine;
        this.numeriOspiti = numeriOspiti;
    }

    public PrenotazioneBean(String localita, String startDate, String endDate, int numOspiti) throws DateTimeParseException{
        this.localita = localita;
        this.startDate = formatDate(startDate);
        this.endDate = formatDate(endDate);
        this.numOspiti = numOspiti;
    }

    public PrenotazioneBean(String localita, LocalDate startDate, LocalDate endDate, int numOspiti) {
        this.localita = localita;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numOspiti = numOspiti;
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

    public List<String> getPrenotanti() {
        return prenotanti;
    }

    public void setPrenotanti(List<String> prenotanti) {
        this.prenotanti = prenotanti;
    }

    public List<LocalDate> getDateInizio() {
        return dateInizio;
    }

    public void setDateInizio(List<LocalDate> dateInizio) {
        this.dateInizio = dateInizio;
    }

    public List<LocalDate> getDateFine() {
        return dateFine;
    }

    public void setDateFine(List<LocalDate> dateFine) {
        this.dateFine = dateFine;
    }

    public List<Integer> getNumeriOspiti() {
        return numeriOspiti;
    }

    public void setNumeriOspiti(List<Integer> numeriOspiti) {
        this.numeriOspiti = numeriOspiti;
    }

    public List<String> getTitoli() {
        return titoli;
    }

    public void setTitoli(List<String> titoli) {
        this.titoli = titoli;
    }
}
