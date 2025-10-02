package main.model;

public class Immobile {

    //Properties
    private String indirizzo;
    private int maxOspiti;
    String[] servizi;

    //Constructor
    public Immobile(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    //Getter Setter
    public int getMaxOspiti() {
        return maxOspiti;
    }
    public void setMaxOspiti(int maxOspiti) {
        this.maxOspiti = maxOspiti;
    }

    public String getIndirizzo() {
        return indirizzo;
    }
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String[] getServizi() {
        return servizi;
    }
    public void setServizi(String[] servizi) {
        this.servizi = servizi;
    }

}
