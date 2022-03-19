package com.core.javaflix;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.*;

public class BaseController {
    @FXML
    private Label emailAddressLabel;

    @FXML
    private Label passwordLabel;

    @FXML // contains email address info given by the user
    private TextField emailAddressInput;

    @FXML // contains email address info given by the user
    private TextField passwordInput;

    @FXML
    private Button loginButton;

    @FXML
    private Button createAccountButton;

    /**
     * Listener for when the user clicks on the "Login" button
     * Validate login information and load new page if valid, error message otherwise.
     * @param actionEvent contains data regarding the nature of the interaction
     */
    @FXML
    public void loginUser(ActionEvent actionEvent) throws SQLException {
        System.out.println("Login Attempted");

        // retrieve session
        var c = DataStreamManager.conn;
        System.out.println(c.getCatalog());
        Statement statement = c.createStatement();
        ResultSet rs = statement.executeQuery("SELECT p320_05.\"User\".\"Password\" FROM p320_05.\"User\" " +
                "WHERE \"Email\" = '" + emailAddressInput.getText() + "'");
        rs.next();
        try {
            if (rs.getString("password").equals(passwordInput.getText())) {
                System.out.println("Login Successful");

                //get user ids
                ResultSet rsid = statement.executeQuery("SELECT p320_05.\"User\".\"UserID\" FROM p320_05.\"User\" " +
                        "WHERE \"Email\" = '" + emailAddressInput.getText() + "'");
                rsid.next();
                BaseApplication.storage.userID = rsid.getInt("UserID");

                //update last log in to today
                statement.executeQuery(  "UPDATE p320_05.\"User\" " +
                        "SET \"LastAccess\" = '" + new Date(System.currentTimeMillis()) + "' " +
                        "WHERE \"Email\" = '" + emailAddressInput.getText() + "';");

                //load dashboard
                new DashboardWindow().load();
            } else {
                System.out.println("Login failed (Invalid Password)");
            }
        }
        catch (SQLException | IOException e)
        {
            System.out.println("Login Failed (Invalid Email Entry)");
            e.printStackTrace();
        }
    }

    /**
     * Listener for when the user clicks on the "Create Account" button
     * a new page is expected to load to enter info for the new account.
     * @param actionEvent contains data regarding the nature of the interaction
     */
    @FXML
    public void sendToAccountCreation(ActionEvent actionEvent) throws IOException {
        System.out.println("Create Account Button Clicked");

        // load a scene and place it within our base application
        new SignUpWindow().load();
    }
}