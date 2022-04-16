package com.core.javaflix.dashboard.friends.following;

import com.core.javaflix.BaseApplication;
import com.core.javaflix.dashboard.friends.friending.FriendsWindow;
import com.core.javaflix.dashboard.friends.user_search.User;
import com.core.javaflix.utilities.AbstractWindow;
import com.core.javaflix.utilities.AppStorage;
import com.core.javaflix.utilities.DataStreamManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.*;

public class FollowedController {
    ObservableList<User> list = FXCollections.observableArrayList(
    );

    @FXML
    private TableView followingTable;


    @FXML
    private TableColumn<User, String> nameColumn;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, Button> buttonColumn;

    @FXML
    public void initialize() {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("Username"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        buttonColumn.setCellValueFactory(new PropertyValueFactory<>("Button"));
        try {
            var c = DataStreamManager.conn;
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery("select R.*\n" +
                    "from p320_05.\"UserFollow\" L, p320_05.\"User\" R\n" +
                    "where L.\"UserID\" = " + AppStorage.userID + "\n" +
                    "AND R.\"UserID\" = L.\"FollowID\"");
            while (rs.next()) {
                String select = String.valueOf(rs.getInt("UserID"));
                User user = new User(rs.getInt("UserID"),
                        rs.getString("Username"),
                        rs.getString("FirstName") +  " " + rs.getString("LastName"));

                Button button = new Button("Unfollow");
                button.setOnAction(EventHandler -> {
                    try {
                        unfollow(select);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                user.setButton(button);
                list.add(user);
            }
        } catch (SQLException e) {
            System.out.println("error");
        }
        followingTable.setItems(list);
    }

    @FXML
    public void goBack() throws IOException {
        AbstractWindow.loadLastPage();
    }

    @FXML
    public void unfollow(String a) throws IOException {
        try {
            var c = DataStreamManager.conn;
            Statement statement = c.createStatement();
            statement.executeQuery("delete from p320_05.\"UserFollow\"\n" +
                    "where \"UserID\" = '" + BaseApplication.storage.userID + "'\n" +
                    "and \"FollowID\" = '" + a + "'");
        } catch (SQLException e) {

        }
        new FollowedWindow().load();
    }
}
