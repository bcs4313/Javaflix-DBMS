package com.core.javaflix.dashboard.friends.following;

import com.core.javaflix.dashboard.friends.friending.FriendsWindow;
import com.core.javaflix.dashboard.friends.user_search.User;
import com.core.javaflix.utilities.AbstractWindow;
import com.core.javaflix.utilities.AppStorage;
import com.core.javaflix.utilities.DataStreamManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FansController {
    ObservableList<User> list = FXCollections.observableArrayList(
    );

    @FXML
    private TableView fansTable;

    @FXML
    private TableColumn<User, String> nameColumn;

    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, String> buttonColumn;

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
                    "where L.\"FollowID\" = " + AppStorage.userID + "\n" +
                    "AND R.\"UserID\" = L.\"UserID\"" );

            while (rs.next()) {
                User user = new User(rs.getInt("UserID"),
                        rs.getString("Username"),
                        rs.getString("FirstName") +  " " + rs.getString("LastName"),
                        new FansWindow());
                user.setButton(null);
                list.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve fans");
        }
        fansTable.setItems(list);
    }

    @FXML
    public void goBack() throws IOException {
        AbstractWindow.loadLastPage();
    }
}
