package com.core.javaflix;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class FriendsController {
    @FXML
    Button fans;

    @FXML
    TextField searchFriend;

    @FXML
    Button followed;

    @FXML
    public void goToFans() throws IOException {
        new FansWindow().load();
    }

    public void goToFollowed() throws IOException {
        new FollowedWindow().load();
    }

    public void sentToDashboard() throws IOException {
        new DashboardWindow().load();
    }
}
