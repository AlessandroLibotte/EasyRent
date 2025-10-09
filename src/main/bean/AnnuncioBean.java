package main.bean;

import java.util.List;

public class AnnuncioBean {

    //properties
    private String owner;
    private String titolo;
    private String indirizzo;
    private String descrizione;
    private String[] servizi;
    private int maxOspiti;
    private double price;
    private int voto;

    private List<String> prenotanti;

    public AnnuncioBean() {}

    public AnnuncioBean(String titolo) { this.titolo = titolo; }

    public AnnuncioBean(String titolo, String owner) {
        this.titolo = titolo;
        this.owner = owner;
    }

    public boolean isNotValid(){
        return isOwnerValid() && isTitoloValid() && isIndirizzoValid() && isPriceValid();
    }

    private boolean isOwnerValid() {
        return owner != null && !owner.isEmpty();
    }

    private boolean isTitoloValid() {
        return titolo != null && !titolo.isEmpty();
    }

    private boolean isIndirizzoValid() {
        return indirizzo != null && !indirizzo.isEmpty();
    }

    private boolean isPriceValid() {
        return price > 0;
    }

    //getter setter
    public String getOwner() { return owner; }
    public void setOwner(String owner) {
        this.owner = owner;
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

    public String getServiziString() {
        return String.join(" ", servizi);
    }
    public String[] getServizi() {
        return servizi;
    }
    public void setServiziString(String servizi) { this.servizi = servizi.split(","); }
    public void setServizi(String[] servizi) { this.servizi = servizi;}

    public int getMaxOspiti() {
        return maxOspiti;
    }
    public void setMaxOspiti(int maxOspiti) {
        this.maxOspiti = maxOspiti;
    }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getVoto() {
        return voto;
    }
    public void setVoto(int voto) {
        this.voto = voto;
    }

    public List<String> getPrenotanti() {
        return prenotanti;
    }
    public void setPrenotanti(List<String> prenotanti) {
        this.prenotanti = prenotanti;
    }
}
