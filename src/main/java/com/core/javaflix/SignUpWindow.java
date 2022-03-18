package com.core.javaflix;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpWindow {
    /**
     * load this window into the main stage (manually called)
     * @throws IOException thrown if fxml file isn't retrieved properly
     */
    public void load() throws IOException {
        // load application page
        FXMLLoader fxmlLoader = new FXMLLoader(BaseApplication.class.getResource("signup-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 852, 480); // standard 480p window size
        BaseApplication.window.setScene(scene);
        BaseApplication.window.show();
    }

}
