package main.persistence.exceptions;

public class LoadException extends RuntimeException {
    public LoadException(String message) {
        super(message);
    }
}
