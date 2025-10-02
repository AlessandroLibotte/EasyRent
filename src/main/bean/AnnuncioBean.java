package main.bean;

import java.util.ArrayList;
import java.util.List;

public class AnnuncioBean {

    //properties
    private String owner;
    private String titolo;
    private String oldTitolo;
    private String indirizzo;
    private String descrizione;
    private String[] servizi;
    private int maxOspiti;
    private double price;
    private int voto;

    private List<String> titoliAnnunci;
    private List<String> indirizziAnnunci;
    private List<Integer> votiAnnunci;
    private List<Double> prezziAnnunci;
    private List<String> prenotanti;

    //constructors
    public AnnuncioBean(String owner, String titolo, String indirizzo, String descrizione, String servizi, int maxOspiti, double price, int voto) {
        this.owner = owner;
        this.titolo = titolo;
        this.indirizzo = indirizzo;
        this.descrizione = descrizione;
        this.servizi = servizi.split("[,\\s]");
        this.maxOspiti = maxOspiti;
        this.price = price;
        this.voto = voto;
    }
    public AnnuncioBean(String owner, String titolo, String indirizzo, String descrizione, String[] servizi, int maxOspiti, double price, int voto, List<String> prenotanti) {
        this.owner = owner;
        this.titolo = titolo;
        this.indirizzo = indirizzo;
        this.descrizione = descrizione;
        this.servizi = servizi;
        this.maxOspiti = maxOspiti;
        this.price = price;
        this.voto = voto;
        this.prenotanti = prenotanti;
    }

    public AnnuncioBean(String owner, String oldTitolo, String newTitolo, String indirizzo, String descrizione, String servizi, int maxOspiti, double price, int voto) {
        this.owner = owner;
        this.oldTitolo = oldTitolo;
        this.titolo = newTitolo;
        this.indirizzo = indirizzo;
        this.descrizione = descrizione;
        this.servizi = servizi.split("[,\\s]");
        this.maxOspiti = maxOspiti;
        this.price = price;
        this.voto = voto;
    }
    public AnnuncioBean(List<String> titoliAnnunci, List<String> indirizziAnnunci, List<Integer> votiAnnunci, List<Double> prezziAnnunci) {
        this.titoliAnnunci = titoliAnnunci;
        this.indirizziAnnunci = indirizziAnnunci;
        this.votiAnnunci = votiAnnunci;
        this.prezziAnnunci = prezziAnnunci;
    }

    public AnnuncioBean(String owner) { this.owner = owner; }

    public AnnuncioBean(String titolo, String owner) {
        this.titolo = titolo;
        this.owner = owner;
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

    public String[] getServizi() {
        return servizi;
    }
    public void setServizi(String[] servizi) { this.servizi = servizi; }

    public List<String> getTitoliAnnunci() {
        if(titoliAnnunci != null) return titoliAnnunci;
        return new ArrayList<>();
    }
    public void setTitoliAnnunci(List<String> titoliAnnunci) {
        this.titoliAnnunci = titoliAnnunci;
    }

    public int getMaxOspiti() {
        return maxOspiti;
    }
    public void setMaxOspiti(int maxOspiti) {
        this.maxOspiti = maxOspiti;
    }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public List<String> getIndirizziAnnunci() { return indirizziAnnunci; }
    public void setIndirizziAnnunci(List<String> indirizziAnnunci) { this.indirizziAnnunci = indirizziAnnunci; }

    public List<Integer> getVotiAnnunci() { return votiAnnunci; }
    public void setVotiAnnunci(List<Integer> votiAnnunci) { this.votiAnnunci = votiAnnunci; }

    public List<Double> getPrezziAnnunci() { return prezziAnnunci; }
    public void setPrezziAnnunci(List<Double> prezziAnnunci) { this.prezziAnnunci = prezziAnnunci; }

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
