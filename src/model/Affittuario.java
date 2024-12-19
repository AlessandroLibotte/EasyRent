package model;

import java.util.ArrayList;

public class Affittuario extends User{

    String nome;
    String cognome;
    String telefono;
    ArrayList<Annuncio> prenotazioni;

    public Affittuario(User user) {
        super(user.getEmail());
        this.password = user.getPassword();
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public ArrayList<Annuncio> getPrenotazioni() {
        return prenotazioni;
    }
    public void setPrenotazioni(ArrayList<Annuncio> prenotazioni) {
        this.prenotazioni = prenotazioni;
    }
}
