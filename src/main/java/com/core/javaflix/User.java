package com.core.javaflix;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class User {
    private int userID;
    private String email;
    private String username;
    private String name;
    private Button button;

    public User(int userID, String email, String username, String name) {
        this.userID = userID;
        this.email = email;
        this.username = username;
        this.name = name;
        this.button = new Button("Visit");
        try {
            this.button.setOnAction(actionEvent -> {
                try {
                    this.visitUser(this.userID);
                } catch (IOException e) {
                }
            });
        }
        catch (Exception e) {

        }
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

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getUserID() {
        return userID;
    }

    /**
     * Listener for when the user clicks on the "visit" button
     * redirect user to friends page
     */
    @FXML
    public static void visitUser(int userID) throws IOException {
        try {
            BaseApplication.storage.search = "";
            BaseApplication.storage.otherID = userID;
            new UserWindow().load();
        }
        catch (Exception e) {

        }
    }
}
