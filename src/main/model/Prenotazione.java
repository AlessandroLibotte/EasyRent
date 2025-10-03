package main.model;

import java.time.LocalDate;

public class Prenotazione {

    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
    private int numOspiti;
    private String prenotante;
    private String titoloAnnuncio;

    public Prenotazione(int id) {
        this.id = id;
    }

    public Prenotazione(String titoloAnnuncio){
        this.titoloAnnuncio = titoloAnnuncio;
    }

    public String getPrenotante() {
        return prenotante;
    }
    public void setPrenotante(String prenotante) {
        this.prenotante = prenotante;
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

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitoloAnnuncio() {
        return titoloAnnuncio;
    }
    public void setTitoloAnnuncio(String titoloAnnuncio) {
        this.titoloAnnuncio = titoloAnnuncio;
    }
}
