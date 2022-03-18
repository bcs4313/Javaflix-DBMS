package com.core.javaflix;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class FriendsWindow {
    public void load() throws IOException {
        // load application page
        FXMLLoader fxmlLoader = new FXMLLoader(BaseApplication.class.getResource("friends-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 852, 480); // standard 480p window size
        BaseApplication.window.setScene(scene);
        BaseApplication.window.show();
    }
}
