package main.control.exceptions;

public class AnnuncioExistsException extends Exception {

    public String titolo;

    public AnnuncioExistsException(String titolo) {
        this.titolo = titolo;
    }
}
