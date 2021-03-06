package com.core.javaflix.dashboard.settings;

import com.core.javaflix.BaseApplication;
import com.core.javaflix.utilities.AbstractWindow;
import com.core.javaflix.utilities.DataStreamManager;
import com.core.javaflix.dashboard.DashboardWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// generating values


public class SettingsController {
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
     * initialize the page with this information.
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
            statement.executeQuery(  "UPDATE p320_05.\"User\" " +
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

        AbstractWindow.loadLastPage();
    }

    /**
     * Listener for when the user clicks on the "back" button
     * redirect user to login page
     * @param actionEvent contains data regarding the nature of the interaction
     */
    @FXML
    public void profileCancel(ActionEvent actionEvent) throws IOException {
        AbstractWindow.loadLastPage();
    }

}
