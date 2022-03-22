package com.core.javaflix.dashboard.friends.user_search;

import com.core.javaflix.BaseApplication;
import com.core.javaflix.dashboard.subwindows.Movie;
import com.core.javaflix.utilities.DataStreamManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserController {

    private boolean friend;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label followingLabel;

    @FXML
    private Label followerLabel;

    @FXML //button to update the user's information
    private Button friendButton;

    @FXML //button to update the user's information
    private Button backButton;

    @FXML
    private TableView movieTable;

    @FXML  //column containing titles
    private TableColumn<Movie, String> titleColumn;

    @FXML  //column containing movie rating
    private TableColumn<Movie, String> ratingColumn;

    @FXML  //column containing times a movie has been played
    private TableColumn<Movie, String> playedColumn;


    /**
     * initialize the page with this information.
     */
    @FXML
    public void initialize() {

        //set value factories
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("Rating"));
        playedColumn.setCellValueFactory(new PropertyValueFactory<>("Played"));

        //add values to observable list
        try {
            var c = DataStreamManager.conn;
            Statement statement = c.createStatement();
            //TODO
        }
        catch (Exception e) {

        }

        movieTable.setItems(list);

        //query for user
        try {
            var c = DataStreamManager.conn;
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery(  "SELECT * " +
                    "FROM p320_05.\"User\"" +
                    "WHERE \"UserID\" = '" + BaseApplication.storage.otherID + "';");

            rs.next();

            //set text
            usernameLabel.setText(rs.getString("Username"));
            nameLabel.setText(rs.getString("FirstName") + " " + rs.getString("LastName"));
            emailLabel.setText(rs.getString("Email"));
            rs = statement.executeQuery(  "SELECT Count(*) " +
                    "FROM p320_05.\"UserFollow\"" +
                    "WHERE \"UserID\" = '" + BaseApplication.storage.otherID + "';");
            rs.next();
            followingLabel.setText("" + rs.getInt("count"));
            rs = statement.executeQuery(  "SELECT Count(*) " +
                    "FROM p320_05.\"UserFollow\"" +
                    "WHERE \"FollowID\" = '" + BaseApplication.storage.otherID + "';");
            rs.next();
            followerLabel.setText("" + rs.getInt("count"));

            //set friends button
            try {
                rs = statement.executeQuery(  "SELECT Count(*) " +
                        "FROM p320_05.\"UserFollow\"" +
                        "WHERE \"FollowID\" = '" + BaseApplication.storage.otherID + "' " +
                        "and \"UserID\" = '" + BaseApplication.storage.userID + "';");
                rs.next();
                if (rs.getInt("count") > 0) {
                    friend = true;
                    friendButton.setText("Unfriend");
                }
                else {
                    friend = false;
                }
            }
            catch (Exception e) {
            }
        }
        catch (Exception e) {
        }
    }

    ObservableList<User> list = FXCollections.observableArrayList(
    );



    /**
     * Listener for when the user clicks on the "search" button
     * redirect user to this page, but with update info
     * @param actionEvent contains data regarding the nature of the interaction
     */
    @FXML
    public void friendUser(ActionEvent actionEvent) throws IOException {
        try {
            //open datastream
            var c = DataStreamManager.conn;
            Statement statement = c.createStatement();

            //remove friend
            if (friend) {
                friendButton.setText("Friend");
                statement.executeQuery("DELETE FROM p320_05.\"UserFollow\" " +
                        "WHERE \"UserID\" = '" + BaseApplication.storage.userID + "' " +
                        "AND \"FollowID\" = '" + BaseApplication.storage.otherID + "';");
            }
            //add friend
            else {
                friendButton.setText("Unfriend");
                statement.execute("INSERT INTO p320_05.\"UserFollow\" " +
                        "VALUES('" + BaseApplication.storage.userID + "', '" + BaseApplication.storage.otherID + "');");
            }
        }
        catch (Exception e) {
        }
        friend = !friend;
    }

    /**
     * Listener for when the user clicks on the "back" button
     * redirect user to friends page
     * @param actionEvent contains data regarding the nature of the interaction
     */
    @FXML
    public void goBack(ActionEvent actionEvent) throws IOException {
        new UserSearchWindow().load();
    }

}
