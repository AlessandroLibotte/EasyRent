package model;

import java.util.List;

public class Affittuario extends User{

    private List<Annuncio> prenotazioni;

    public Affittuario(User user) {
        super(user.getEmail());
        this.password = user.getPassword();
    }

    public List<Annuncio> getPrenotazioni() {
        return prenotazioni;
    }
    public void setPrenotazioni(List<Annuncio> prenotazioni) {
        this.prenotazioni = prenotazioni;
    }
}
