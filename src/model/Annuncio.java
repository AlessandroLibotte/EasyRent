package model;

public class Annuncio {

    String titolo;
    String descrizione;
    int voto;

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

    public int getVoto() {
        return voto;
    }
    public void setVoto(int voto) {
        this.voto = voto;
    }
}
