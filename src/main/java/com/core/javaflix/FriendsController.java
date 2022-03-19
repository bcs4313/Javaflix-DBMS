package com.core.javaflix;

import javafx.fxml.FXML;

import java.io.IOException;

public class FriendsController {
    @FXML
    public void goToFans() throws IOException {
        new FansWindow().load();
    }

    @FXML
    public void goToFollowed() throws IOException {
        new FollowedWindow().load();
    }

    @FXML
    public void sentToDashboard() throws IOException {
        new DashboardWindow().load();
    }

    @FXML
    public void sendToUserSearch() throws IOException {
        new UserSearchWindow().load();
    }
}
