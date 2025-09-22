package main.model;

public class Immobile {

    private String indirizzo;
    private int maxOspiti;

    public int getMaxOspiti() {
        return maxOspiti;
    }

    public void setMaxOspiti(int maxOspiti) {
        this.maxOspiti = maxOspiti;
    }

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
