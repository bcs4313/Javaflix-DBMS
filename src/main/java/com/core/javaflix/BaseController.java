package com.core.javaflix;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseController {
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

    /**
     * Listener for when the user clicks on the "Login" button
     * Validate login information and load new page if valid, error message otherwise.
     * @param actionEvent contains data regarding the nature of the interaction
     */
    @FXML
    public void loginUser(ActionEvent actionEvent) throws SQLException {
        System.out.println("Login Attempted");
        String email = emailAddressInput.getText();


        // retrieve session
        var s = DataStreamManager.session;
        var c = DataStreamManager.conn;
        System.out.println(c.getCatalog());
        Statement statement = c.createStatement();
        ResultSet rs = statement.executeQuery("SELECT p320_05.\"User\".\"FirstName\" FROM p320_05.\"User\" WHERE \"Email\" = '" + email + "'");
        rs.next();
        System.out.println(rs.getString("FirstName"));
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