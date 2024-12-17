package bean;

import model.Affittuario;
import model.Locatore;
import model.User;

public class LoginBean {

    String nome;
    String cognome;
    String email;
    String password;
    String telefono;
    int role;

    //login constructor
    public LoginBean(String email, String password) {
        this.email = email;
        this.password = password;
    }

    //register constructor
    public LoginBean(String nome, String cognome, String email, String password, String telefono, int role) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.role = role;
    }

    public LoginBean(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        if(user instanceof Affittuario a){
            this.nome = a.getNome();
            this.cognome = a.getCognome();
            this.telefono = a.getTelefono();
        }
        if (user instanceof Locatore l){
            this.nome = l.getNome();
            this.cognome = l.getCognome();
            this.telefono = l.getTelefono();
        }
    }

    public String getNome() {
        return nome;
    }
    public String getCognome() {
        return cognome;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getTelefono() {
        return telefono;
    }
    public int getRole() {
        return role;
    }

}
