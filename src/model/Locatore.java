package model;

import java.util.List;

public class Locatore extends User{

    String nome;
    String cognome;
    String telefono;
    List<Annuncio> annunci;

    public Locatore(User user) {
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

    public List<Annuncio> getAnnunci() {
        return annunci;
    }
    public void setAnnunci(List<Annuncio> annunci) {
        this.annunci = annunci;
    }

}
