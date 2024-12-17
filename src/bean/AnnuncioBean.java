package bean;

import java.util.ArrayList;
import java.util.List;

public class AnnuncioBean {

    String titolo;
    String oldTitolo;
    String indirizzo;
    String descrizione;
    boolean[] servizi;

    int maxOspiti;

    List<String> annunci;


    public AnnuncioBean(String titolo, String indirizzo, String descrizione, boolean[] servizi, int maxOspiti) {
        this.titolo = titolo;
        this.indirizzo = indirizzo;
        this.descrizione = descrizione;
        this.servizi = servizi;
        this.maxOspiti = maxOspiti;
    }

    public AnnuncioBean(List<String> annunci) {
        this.annunci = annunci;
    }

    public AnnuncioBean(String titolo) {
        this.titolo = titolo;
    }

    public AnnuncioBean(String oldTitolo, String newTitolo, String indirizzo, String descrizione, boolean[] servizi, int maxOspiti) {
        this.oldTitolo = oldTitolo;
        this.titolo = newTitolo;
        this.indirizzo = indirizzo;
        this.descrizione = descrizione;
        this.servizi = servizi;
        this.maxOspiti = maxOspiti;
    }

    public String getTitolo() {
        return titolo;
    }
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getOldTitolo() {
        return oldTitolo;
    }
    public void setOldTitolo(String newTitolo) {
        this.oldTitolo = newTitolo;
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

    public List<String> getAnnunci() {
        if(annunci != null) return annunci;
        return new ArrayList<>();
    }
    public void setAnnunci(List<String> annunci) {
        this.annunci = annunci;
    }


    public int getMaxOspiti() {
        return maxOspiti;
    }
    public void setMaxOspiti(int maxOspiti) {
        this.maxOspiti = maxOspiti;
    }

}
