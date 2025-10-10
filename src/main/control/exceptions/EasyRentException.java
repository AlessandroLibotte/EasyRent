package main.control.exceptions;

public abstract class EasyRentException extends RuntimeException {

    public abstract void showMessageGUI();
    public abstract void showMessageCLI();

}
