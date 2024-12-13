package bean;

import java.util.ArrayList;

public class AnnuncioBean {

    String titolo;
    String newTitolo;
    String indirizzo;
    String descrizione;
    boolean[] servizi;

    ArrayList<String> annunci;


    public AnnuncioBean(String titolo, String indirizzo, String Descrizione, boolean[] servizi) {
        this.titolo = titolo;
        this.indirizzo = indirizzo;
        this.descrizione = Descrizione;
        this.servizi = servizi;
    }

    public AnnuncioBean(ArrayList<String> annunci) {
        this.annunci = annunci;
    }

    public AnnuncioBean(String titolo) {
        this.titolo = titolo;
    }

    public AnnuncioBean(String oldTitolo, String newTitolo, String indirizzo, String descrizione, boolean[] servizi) {
        this.titolo = oldTitolo;
        this.newTitolo = newTitolo;
        this.indirizzo = indirizzo;
        this.descrizione = descrizione;
        this.servizi = servizi;
    }

    public String getTitolo() {
        return titolo;
    }
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getNewTitolo() {
        return newTitolo;
    }
    public void setNewTitolo(String newTitolo) {
        this.newTitolo = newTitolo;
    }

    public String getIndirizzo() {
        return indirizzo;
    }
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getDescrizione() {
        return descrizione;
    }
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public boolean[] getServizi() {
        return servizi;
    }
    public void setServizi(boolean[] servizi) {
        this.servizi = servizi;
    }

    public ArrayList<String> getAnnunci() {
        if(annunci != null) return annunci;
        return new ArrayList<>();
    }
    public void setAnnunci(ArrayList<String> annunci) {
        this.annunci = annunci;
    }

}
