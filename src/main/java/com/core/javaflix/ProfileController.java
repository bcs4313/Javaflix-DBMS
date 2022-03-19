package com.core.javaflix;

import com.core.javaflix.DashboardWindow;
import com.core.javaflix.DataStreamManager;
import com.core.javaflix.DashboardWindow;
import com.core.javaflix.BaseApplication;
import com.core.javaflix.DataStreamManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

// generating values


public class ProfileController {
    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label passwordLabel;

    @FXML // contains email address of the user
    private TextField userNameInput;

    @FXML // contains the first name of the user
    private TextField firstNameInput;

    @FXML // contains the last name of the user
    private TextField lastNameInput;

    @FXML // contains email address info given by the user
    private TextField emailInput;

    @FXML // contains email address info given by the user
    private TextField passwordInput;

    @FXML //button to update the user's information
    private Button updateButton;

    @FXML //button to update the user's information
    private Button cancelButton;


    /**
     * Builder that assigns the text fields with the correct
     * information of the user.
     */
    @FXML
    public void initialize() {
        try {
            var c = DataStreamManager.conn;
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery(  "SELECT * " +
                    "FROM p320_05.\"User\"" +
                    "WHERE \"UserID\" = '" + BaseApplication.storage.userID + "';");

            rs.next();

            userNameInput.setText(rs.getString("Username"));
            firstNameInput.setText(rs.getString("FirstName"));
            lastNameInput.setText(rs.getString("LastName"));
            emailInput.setText(rs.getString("Email"));
            passwordInput.setText(rs.getString("Password"));
        }
        catch (SQLException e){
            System.out.println("Failed to get profile information");
        }
    }

    /**
     * Listener for when the user clicks on the "update" button
     * redirect user to login page
     * @param actionEvent contains data regarding the nature of the interaction
     */
    @FXML
    public void profileUpdate(ActionEvent actionEvent) throws IOException {
        try {
            var c = DataStreamManager.conn;
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery(  "UPDATE p320_05.\"User\" " +
                    "SET \"Username\" = '" + userNameInput.getText() + "', " +
                    "\"FirstName\" = '" + firstNameInput.getText() + "', " +
                    "\"LastName\" = '" + lastNameInput.getText() + "', " +
                    "\"Email\" = '" + emailInput.getText() + "', " +
                    "\"Password\" = '" + passwordInput.getText() + "' " +
                    "WHERE \"UserID\" = '" + BaseApplication.storage.userID + "';");
        }
        catch (SQLException e) {
            System.out.println("Failed to update profile information");
        }

        new DashboardWindow().load();
    }

    /**
     * Listener for when the user clicks on the "back" button
     * redirect user to login page
     * @param actionEvent contains data regarding the nature of the interaction
     */
    @FXML
    public void profileCancel(ActionEvent actionEvent) throws IOException {
        new DashboardWindow().load();
    }

}
