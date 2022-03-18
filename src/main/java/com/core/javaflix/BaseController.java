package com.core.javaflix;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class BaseController {
    private SceneController sc;

    @FXML
    private Label emailAddressLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Button createAccountButton;

    @FXML // contains email address info given by the user
    private TextField emailAddressInput;

    @FXML // contains email address info given by the user
    private TextField passwordInput;

    @FXML
    private Button searchButton;

    //builder method for the class
    public BaseController() {
        sc = new SceneController();
    }

    /**
     * Listener for when the user clicks on the "Login" button
     * Validate login information and load new page if valid, error message otherwise.
     * @param actionEvent contains data regarding the nature of the interaction
     */
    @FXML
    public void loginUser(ActionEvent actionEvent) throws SQLException {
        System.out.println("Login Attempted");


        // retrieve session
        var s = DataStreamManager.session;
        var c = DataStreamManager.conn;
        System.out.println(c.getCatalog());
        Statement statement = c.createStatement();
        ResultSet rs = statement.executeQuery("SELECT p320_05.\"User\".\"Password\" FROM p320_05.\"User\" " +
                "WHERE \"Email\" = '" + emailAddressInput.getText() + "'");
        rs.next();
        try {
            if (rs.getString("password").equals(passwordInput.getText())) {
                System.out.println("Login Successful");
                //TODO send to home page
                try {
                    sc.goToHome(actionEvent);
                }
                catch (IOException e) {
                    System.out.println("Failed to load home page");
                }
            } else {
                System.out.println("Login failed (Invalid Password)");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Login Failed (Invalid Email Entry)");
        }
    }

    /**
     * Listener for when the user clicks on the "Search Movies" button
     * a new page is expected to load to search through the movie library.
     * @param actionEvent contains data regarding the nature of the interaction
     */
    public void searchMovies(ActionEvent actionEvent) throws IOException{
        sc.goToSearch(actionEvent);
    }

    /**
     * Listener for when the user clicks on the "Create Account" button
     * a new page is expected to load to enter info for the new account.
     * @param actionEvent contains data regarding the nature of the interaction
     */
    @FXML
    public void sendToAccountCreation(ActionEvent actionEvent) {
        System.out.println("Create Account Button Clicked");
    }
}