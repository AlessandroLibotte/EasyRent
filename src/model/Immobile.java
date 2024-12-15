package model;

public class Immobile {

    String indirizzo;
    boolean[] servizi;

    public Immobile(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getIndirizzo() {
        return indirizzo;
    }
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public boolean[] getServizi() {
        return servizi;
    }
    public void setServizi(boolean[] servizi) {
        this.servizi = servizi;
    }

}
