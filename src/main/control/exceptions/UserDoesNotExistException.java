package main.control.exceptions;

public class UserDoesNotExistException extends RuntimeException {

    public String email;

    public UserDoesNotExistException(String email) {
        this.email = email;
    }
}
