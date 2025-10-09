package main.bean;

public class LoginBean {

    private final String email;
    private String password;

    public LoginBean(String email){
        this.email = email;
    }

    public LoginBean(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public boolean isNotValid() {
        return !isEmailValid() || !isPasswordValid();
    }

    private boolean isEmailValid() {
        return email != null && email.contains("@");
    }

    private boolean isPasswordValid() {
        return password != null && password.length() >= 8;
    }

    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
}
