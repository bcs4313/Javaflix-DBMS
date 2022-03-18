package com.core.javaflix;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class FriendsController {
    @FXML
    Button fans;

    @FXML
    Button followed;

    @FXML
    public void goToFans() throws IOException {
        new FansWindow().load();
    }

    public void goToFollowed() throws IOException {
        new FollowedWindow().load();
    }
}
