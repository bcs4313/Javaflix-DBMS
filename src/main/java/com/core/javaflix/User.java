package com.core.javaflix;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class User {
    private String email;
    private String username;
    private String name;
    private Button button;

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
}
