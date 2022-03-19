package com.core.javaflix;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class User {
    private String email = null;
    private String username = null;
    private String name = null;
    private Button button = null;

    public User(String email, String username, String name) {
        this.email = email;
        this.username = username;
        this.name = name;
        this.button = new Button("Visit");
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public Button getButton() {
        return button;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}
