package com.core.javaflix;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

// generating values
import java.util.Random;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;

public class ProfileController {
    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;

    @FXML // contains email address info given by the user
    private TextField userNameInput;

    @FXML // contains email address info given by the user
    private TextField emailAddressInput;

    @FXML // contains email address info given by the user
    private TextField passwordInput;

    @FXML
    private Button loginButton;

    @FXML
    private Button backButton;

    /**
     * Listener for when the user clicks on the "back" button
     * redirect user to login page
     * @param actionEvent contains data regarding the nature of the interaction
     */
    @FXML
    public void loginBack(ActionEvent actionEvent) {
        //BaseApplication.base.load();
    }

    /**
     * Listener for when the user clicks on the "Create Account" button
     * redirect user to login page afterwards
     * @param actionEvent contains data regarding the nature of the interaction
     * @return if account creation was successful
     */
    @FXML
    public boolean createAccount(ActionEvent actionEvent) {
        try {
            //@todo auto increment user id instead of generating it here
            // now to generate all user information to insert into the 'User' Table
            int userID = new Random().nextInt(9999);
            String userName = userNameInput.getText();
            String email = emailAddressInput.getText();
            String password = passwordInput.getText();
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Calendar obj = Calendar.getInstance();
            String creationDate = formatter.format(obj.getTime());
            System.out.println("Current Date: " + creationDate);

            var c = DataStreamManager.conn;
            Statement statement = c.createStatement();
            statement.execute("INSERT INTO p320_05.\"User\" VALUES " +
                    "(" + userID + ",'" + userName + "', '" + email + "',\n" +
                    "null, null,\n" +
                    "CAST('" + creationDate + "' AS DATE),\n" +
                    "CAST('" + creationDate + "' AS DATE)\n" +
                    ", '" + password + "')");
            System.out.println("Account Created");
            //BaseApplication.base.load();
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
