package main.model;

import java.util.List;

public class Annuncio {

    private String owner;
    private String titolo;
    private String descrizione;
    private Immobile immobile;
    private int voto;
    private double prezzoPerNotte;
    private List<Prenotazione> prenotazioni;

    public Annuncio(String titolo){
        this.titolo = titolo;
    }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public String getTitolo() {
        return titolo;
    }
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Immobile getImmobile() {
        return immobile;
    }
    public void setImmobile(Immobile immobile) {
        this.immobile = immobile;
    }

    public int getVoto() {
        return voto;
    }
    public void setVoto(int voto) {
        this.voto = voto;
    }

    public double getPrezzoPerNotte() { return prezzoPerNotte; }
    public void setPrezzoPerNotte(double prezzoPerNotte) { this.prezzoPerNotte = prezzoPerNotte; }

    public List<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }
    public void setPrenotazioni(List<Prenotazione> prenotazioni) {
        this.prenotazioni = prenotazioni;
    }

}
