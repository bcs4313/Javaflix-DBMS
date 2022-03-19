package com.core.javaflix;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.*;

public class FollowedController {

    @FXML
    Button unFollow;

    @FXML
    VBox following;

    private String selected = null;

    @FXML
    public void initialize() {
        try {
            var c = DataStreamManager.conn;
            Statement statement = c.createStatement();
            unFollow.setDisable(true);
            this.selected = null;
            ResultSet rs = statement.executeQuery("select R.\"FollowID\", S.\"Username\"\n" +
                    "from p320_05.\"UserFollow\" R, p320_05.\"User\" S\n" +
                    "where R.\"UserID\" = "  + BaseApplication.storage.userID + "\n" +
                    "and R.\"FollowID\" = S.\"UserID\"");
            while (rs.next()) {
                for (int i = 2; i <= 2; i++) {
                    String columnValue = rs.getString(i);
                    String ID = rs.getString(i - 1);
                    Button button = new Button(columnValue);
                    button.setOnAction(actionEvent -> selectFriend(ID, button));
                    following.getChildren().add(button);
                }
            }
        } catch (SQLException e) {
            System.out.println("error");
        }
    }

    @FXML
    public void sentToFriends() throws IOException {
        new FriendsWindow().load();
    }

    public void selectFriend(String ID, Button button) {
        unFollow.setDisable(false);
        this.selected = ID;
    }

    @FXML
    public void unfollow() throws IOException {
        try {
            var c = DataStreamManager.conn;
            Statement statement = c.createStatement();
            statement.executeQuery("delete from p320_05.\"UserFollow\"\n" +
                    "where \"UserID\" = '" + BaseApplication.storage.userID + "'\n" +
                    "and \"FollowID\" = '" + this.selected + "'");
        } catch (SQLException e) {

        }
        new FollowedWindow().load();
    }
}
