package model;

import java.util.ArrayList;

public class Affittuario extends User{

    ArrayList<Annuncio> prenotazioni;

    public Affittuario(User user) {
        super(user.getEmail());
        this.password = user.getPassword();
    }

    public ArrayList<Annuncio> getPrenotazioni() {
        return prenotazioni;
    }
    public void setPrenotazioni(ArrayList<Annuncio> prenotazioni) {
        this.prenotazioni = prenotazioni;
    }
}
