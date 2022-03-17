package com.core.javaflix;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class BaseApplication extends Application {
    public static DataStreamManager dm;

    @Override
    public void start(Stage stage) throws IOException {
        // initialize a data stream to the JavaFlix database
        DataStreamManager streamManager = new DataStreamManager();
        if(streamManager.establishConnection()) // if the streamManager successfully connects
        {
            // load application page
            FXMLLoader fxmlLoader = new FXMLLoader(BaseApplication.class.getResource("login-page.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 852, 480); // standard 480p window size
            stage.setTitle("JavaFlix");
            stage.setScene(scene);
            stage.show();
        }
        else
        {
            System.err.println("Stopped Application Loading Process (connection failure)");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}