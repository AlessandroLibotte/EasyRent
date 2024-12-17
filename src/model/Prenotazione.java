package model;

import java.time.LocalDate;

public class Prenotazione {

    LocalDate startDate;
    LocalDate endDate;
    int numOspiti;
    String prenotante;

    public Prenotazione(String prenotante) {
        this.prenotante = prenotante;
    }

    public String getPrenotante() {
        return prenotante;
    }

    public void setPrenotante(String prenotante) {
        this.prenotante = prenotante;
    }

    public Prenotazione(LocalDate startDate, LocalDate endDate, int numOspiti) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.numOspiti = numOspiti;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getNumOspiti() {
        return numOspiti;
    }

    public void setNumOspiti(int numOspiti) {
        this.numOspiti = numOspiti;
    }

}