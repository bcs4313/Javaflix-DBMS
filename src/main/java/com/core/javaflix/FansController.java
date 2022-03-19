package com.core.javaflix;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class FansController {
    @FXML
    private VBox followed;

    @FXML
    public void initialize() {
        try {
            var c = DataStreamManager.conn;
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery("select R.\"UserID\", S.\"Username\"\n" +
                    "from p320_05.\"UserFollow\" R, p320_05.\"User\" S\n" +
                    "where R.\"FollowID\" = 1009\n" +
                    "and S.\"UserID\" = R.\"UserID\"");
            ResultSetMetaData rsmd = rs.getMetaData();
            int size = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 2; i <= 2; i++) {
                    String columnValue = rs.getString(i);
                    followed.getChildren().add(new Button(columnValue));
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve fans");
        }
    }

    @FXML
    public void sentToFriends() throws IOException {
        new FriendsWindow().load();
    }
}
