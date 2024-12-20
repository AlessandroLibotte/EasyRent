package model;

import java.util.List;

public class Annuncio {

    private String titolo;
    private String descrizione;
    private Immobile immobile;
    private int voto;
    private List<Prenotazione> prenotazioni;

    public Annuncio(String titolo){
        this.titolo = titolo;
    }

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

    public List<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }
    public void setPrenotazioni(List<Prenotazione> prenotazioni) {
        this.prenotazioni = prenotazioni;
    }

}
