package main.view.viewgui.controllers;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ViewGUILoginPage {

    public PasswordField passwdfield;
    public TextField emailfieled;

    public void login(){

        System.out.println(emailfieled.getCharacters());

    }

}
