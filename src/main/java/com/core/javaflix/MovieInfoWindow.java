package com.core.javaflix;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class MovieInfoWindow {
    public void load() throws IOException {
        // load application page
        FXMLLoader fxmlLoader = new FXMLLoader(BaseApplication.class.getResource("movieInfo-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 852, 480); // standard 480p window size
        BaseApplication.window.setScene(scene);
        BaseApplication.window.show();
    }
}
