package main.persistence.exceptions;

public class DaoFactoryException extends RuntimeException {
    public DaoFactoryException(String message) {
        super(message);
    }
}
