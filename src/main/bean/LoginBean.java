package main.bean;

public class LoginBean {

    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String telefono;
    private int role;

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
