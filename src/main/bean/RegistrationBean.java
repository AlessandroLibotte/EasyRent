package main.bean;

import main.model.Role;

public class RegistrationBean {
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String telefono;
    private Role role;

    public RegistrationBean(String nome, String cognome, String email, String password, String telefono, Role role) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.role = role;
    }

    public boolean isValid() {
        return isNomeValid() && isCognomeValid() && isEmailValid() && isPasswordValid() && isRoleValid();
    }

    private boolean isNomeValid() {
        return nome != null && !nome.isBlank();
    }

    private boolean isCognomeValid() {
        return cognome != null && !cognome.isBlank();
    }

    private boolean isEmailValid() {
        return email != null && email.contains("@");
    }

    private boolean isPasswordValid() {
        return password != null && password.length() >= 8;
    }

    private boolean isRoleValid() {
        return role != null && role != Role.INVALID;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
