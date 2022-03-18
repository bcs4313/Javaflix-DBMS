package com.core.javaflix;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

/**
 * Core launching class of the application, connecting to the database
 * and starting the base UI.
 * @author Cody Smith
 */
public class BaseApplication extends Application {
    public static DataStreamManager dm;

    /**
     * Starting point of our application.
     * Initializes a data strea to the database and
     * opens the login window within the JavaFX stage
     * @param stage mainframe for hosting interactive UI elements
     * @throws IOException exception occurs when the fxml page fails to load
     */
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

    /**
     * listener that disconnects user from the database and closes application upon being called.
     * note: this does NOT work if you force close the application through intellij
     * or the task manager. You must close the stage for a true disconnection.
     */
    @Override
    public void stop(){
        System.out.println("Disconnecting User From Database...");
        try {
            if (DataStreamManager.conn != null) {
                DataStreamManager.conn.close();
                System.out.println("Disconnection Successful");
                System.exit(0);
            }
        }
        catch ( SQLException e)
        {
            System.err.println("error: Unable to disconnect from Database!");
            e.printStackTrace();
            System.exit(-1);
        }
    }
}