package bean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class PrenotazioneBean {

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    String localita;
    LocalDate startDate;
    LocalDate endDate;
    int numOspiti;

    List<String> searchResults;

    public PrenotazioneBean(String localita, String startDate, String endDate, int numOspiti) throws DateTimeParseException{
        this.localita = localita;
        this.startDate = formatDate(startDate);
        this.endDate = formatDate(endDate);
        this.numOspiti = numOspiti;
    }

    public LocalDate formatDate(String strDate) throws DateTimeParseException {
        return LocalDate.parse(strDate, dateFormatter);
    }

    public PrenotazioneBean(List<String> searchResults) {
        this.searchResults = searchResults;
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
