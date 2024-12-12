package bean;

import java.util.Vector;

public class AnnuncioBean {

    String titolo;
    String indirizzo;
    String descrizione;
    boolean[] servizi;

    Vector<String> annunci;


    public AnnuncioBean(String titolo, String indirizzo, String Descrizione, boolean[] servizi) {
        this.titolo = titolo;
        this.indirizzo = indirizzo;
        this.descrizione = Descrizione;
        this.servizi = servizi;
    }

    public AnnuncioBean(Vector<String> annunci) {
        this.annunci = annunci;
    }

    public String getTitolo() {
        return titolo;
    }
    public void setTitolo(String titolo) {
        this.titolo = titolo;
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

    public Vector<String> getAnnunci() {
        return annunci;
    }
    public void setAnnunci(Vector<String> annunci) {
        this.annunci = annunci;
    }

}
