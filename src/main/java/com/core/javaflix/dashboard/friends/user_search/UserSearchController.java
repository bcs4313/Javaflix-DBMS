package com.core.javaflix.dashboard.friends.user_search;

import com.core.javaflix.BaseApplication;
import com.core.javaflix.dashboard.friends.friending.FriendsWindow;
import com.core.javaflix.dashboard.subwindows.MovieInfoWindow;
import com.core.javaflix.utilities.AbstractWindow;
import com.core.javaflix.utilities.DataStreamManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserSearchController {
    @FXML
    private Label searchLabel;

    @FXML // contains email address of the user
    private TextField searchBar;

    @FXML //button to update the user's information
    private Button searchButton;

    @FXML //button to update the user's information
    private Button backButton;

    @FXML
    private TableView searchTable;

    @FXML  //column containing username
    private TableColumn<User, String> usernameColumn;

    @FXML  //column containing name
    private TableColumn<User, String> nameColumn;

    @FXML  //column containing name
    private TableColumn<User, Button> buttonColumn;


    /**
     * initialize the page with this information.
     */
    @FXML
    public void initialize() {

        //set search bar
        searchBar.setText(BaseApplication.storage.search);

        //set value factories
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("Username"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        buttonColumn.setCellValueFactory(new PropertyValueFactory<>("Button"));

        //add values to observable list
        try {
            var c = DataStreamManager.conn;
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * " +
                    "FROM p320_05.\"User\"" +
                    "WHERE \"Email\" LIKE '%" + BaseApplication.storage.search + "%' " +
                    "OR \"Username\" LIKE '%" + BaseApplication.storage.search + "%' " +
                    "OR \"FirstName\" LIKE '%" + BaseApplication.storage.search + "%' " +
                    "OR \"LastName\" LIKE '%" + BaseApplication.storage.search + "%';");

            while (rs.next()) {
                list.add(new User(rs.getInt("UserID"),
                        rs.getString("Username"),
                        rs.getString("FirstName") +  " " + rs.getString("LastName"),
                        new UserSearchWindow()));
            }
        }
        catch (Exception e) {

        }

        searchTable.setItems(list);
    }

    ObservableList<User> list = FXCollections.observableArrayList();



    /**
     * Listener for when the user clicks on the "search" button
     * redirect user to this page, but with update info
     * @param actionEvent contains data regarding the nature of the interaction
     */
    @FXML
    public void searchUser(ActionEvent actionEvent) throws IOException {
        BaseApplication.storage.search = searchBar.getText();
        new UserSearchWindow().load();
    }

    /**
     * Listener for when the user clicks on the "back" button
     * redirect user to friends page
     * @param actionEvent contains data regarding the nature of the interaction
     */
    @FXML
    public void goBack(ActionEvent actionEvent) throws IOException {
        AbstractWindow.loadLastPage();
    }

}
