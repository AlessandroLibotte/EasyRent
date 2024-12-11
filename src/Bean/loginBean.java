package Bean;

public class loginBean {

    String nome;
    String cognome;
    String email;
    String password;
    String telefono;
    int role;

    //login constructor
    public loginBean(String email, String password) {
        this.email = email;
        this.password = password;
    }

    //register constructor
    public loginBean(String nome, String cognome, String email, String password, String telefono, int role) {
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
