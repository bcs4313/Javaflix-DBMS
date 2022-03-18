package com.core.javaflix;

import javafx.fxml.FXML;

import java.io.IOException;

public class FansController {
    @FXML
    public void sentToFriends() throws IOException {
        new FriendsWindow().load();
    }
}
