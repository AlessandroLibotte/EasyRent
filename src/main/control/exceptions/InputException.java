package main.control.exceptions;

public class InputException extends RuntimeException {

    public String message = "Uno o piu campi non sono stati compilati correttamente";

    public InputException() {}
}
