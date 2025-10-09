package main.persistence.exceptions;

public class ExistsException extends RuntimeException {
    public ExistsException(String message) {
        super(message);
    }
}
