package com.core.javaflix;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void bootUp(Stage stage) throws IOException{
        this.stage = stage;
        this.stage.setTitle("JavaFlix");
        goToLogin();
    }

    public void goToLogin() throws IOException{
        root = FXMLLoader.load(getClass().getResource("login-page.fxml"));
        scene = new Scene(root, 852, 480); // standard 480p window size
        stage.setScene(scene);
        stage.show();
    }

    public void goToHome(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("home-page.fxml"));
        stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
        scene = new Scene(root, 852, 480);
        stage.setScene(scene);
        stage.show();
    }

    public void goToSearch(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("search-page.fxml"));
        stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
        scene = new Scene(root, 852, 480);
        stage.setScene(scene);
        stage.show();
    }

}
