package com.core.javaflix.dashboard.friends.user_search;

import com.core.javaflix.BaseApplication;
import com.core.javaflix.utilities.AbstractWindow;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class User {
    private int userID;
    private String username;
    private String name;
    private Button button;
    private Button secondaryButton;

    public User(int userID, String username, String name, AbstractWindow win) {
        this.userID = userID;
        this.username = username;
        this.name = name;
        this.button = new Button("Visit");
        try {
            this.button.setOnAction(actionEvent -> {
                try {
                    this.visitUser(this.userID, win);
                } catch (IOException e) {
                }
            });
        }
        catch (Exception e) {

        }
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public void setUserID(int userID) { this.userID = userID; }

    public int getUserID() {
        return userID;
    }

    public Button getSecondaryButton() { return secondaryButton; }

    public void setSecondaryButton(Button secondaryButton) { this.secondaryButton = secondaryButton; }

    /**
     * Listener for when the user clicks on the "visit" button
     * redirect user to friends page
     */
    @FXML
    public static void visitUser(int userID, AbstractWindow win) throws IOException {
        try {
            BaseApplication.storage.otherID = userID;
            BaseApplication.storage.pageStorage.add(win);
            new UserWindow().load();
        }
        catch (Exception e) {

        }
    }
}
