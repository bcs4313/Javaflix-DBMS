package com.core.javaflix;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class FollowedController {

    @FXML
    Button but;

    @FXML
    public void sentToFriends() throws IOException {
        new FriendsWindow().load();
    }
}
