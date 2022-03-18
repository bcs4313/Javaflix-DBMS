package com.core.javaflix;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class DashboardController {

    @FXML
    private Button friendsButton;

    @FXML
    private Button collectionButton;

    @FXML
    private Button profileButton;

    @FXML
    private TextField searchInput;

    @FXML
    private void sendToCollections() throws IOException {
        new CollectionWindow().load();
    }

    @FXML
    private void sendToFriends() throws IOException {
        new FriendsWindow().load();
    }

    @FXML
    private void sendToProfile() throws IOException {
        new ProfileWindow().load();
    }

}
