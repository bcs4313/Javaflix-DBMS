package com.core.javaflix.dashboard.friends.friending;

import com.core.javaflix.BaseApplication;
import com.core.javaflix.dashboard.DashboardWindow;
import com.core.javaflix.dashboard.friends.following.FansWindow;
import com.core.javaflix.dashboard.friends.following.FollowedWindow;
import com.core.javaflix.dashboard.friends.user_search.UserSearchWindow;
import com.core.javaflix.utilities.AbstractWindow;
import javafx.fxml.FXML;

import java.io.FileReader;
import java.io.IOException;

public class FriendsController {
    @FXML
    public void goToFans() throws IOException {
        BaseApplication.storage.pageStorage.add(new FriendsWindow());
        new FansWindow().load();
    }

    @FXML
    public void goToFollowed() throws IOException {
        BaseApplication.storage.pageStorage.add(new FriendsWindow());
        new FollowedWindow().load();
    }

    @FXML
    public void goBack() throws IOException {
        AbstractWindow.loadLastPage();
    }

    @FXML
    public void sendToUserSearch() throws IOException {
        BaseApplication.storage.pageStorage.add(new FriendsWindow());
        new UserSearchWindow().load();
    }
}
