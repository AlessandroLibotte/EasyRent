package main.model;

import java.util.List;

public class Locatore extends User{

    private List<Annuncio> annunci;

    public Locatore(User user) {
        super(user.getEmail());
        this.password = user.getPassword();
    }

    public List<Annuncio> getAnnunci() {
        return annunci;
    }
    public void setAnnunci(List<Annuncio> annunci) {
        this.annunci = annunci;
    }

}
