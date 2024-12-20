package bean;

import java.util.ArrayList;
import java.util.List;

public class AnnuncioBean {

    private String currentUser;
    private String titolo;
    private String oldTitolo;
    private String indirizzo;
    private String descrizione;
    private boolean[] servizi;
    private int maxOspiti;

    private List<String> annunci;


    public AnnuncioBean(String currentUser, String titolo, String indirizzo, String descrizione, boolean[] servizi, int maxOspiti) {
        this.currentUser = currentUser;
        this.titolo = titolo;
        this.indirizzo = indirizzo;
        this.descrizione = descrizione;
        this.servizi = servizi;
        this.maxOspiti = maxOspiti;
    }

    public AnnuncioBean(List<String> annunci) {
        this.annunci = annunci;
    }

    public AnnuncioBean(String titolo, String currentUser) {
        this.titolo = titolo;
        this.currentUser = currentUser;
    }

    public AnnuncioBean(String currentUser, String oldTitolo, String newTitolo, String indirizzo, String descrizione, boolean[] servizi, int maxOspiti) {
        this.currentUser = currentUser;
        this.oldTitolo = oldTitolo;
        this.titolo = newTitolo;
        this.indirizzo = indirizzo;
        this.descrizione = descrizione;
        this.servizi = servizi;
        this.maxOspiti = maxOspiti;
    }

    public String getCurrentUser() {
        return currentUser;
    }
    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
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
