package com.core.javaflix.dashboard.collections;

import com.core.javaflix.dashboard.subwindows.MovieInfoWindow;
import com.core.javaflix.utilities.AppStorage;
import com.core.javaflix.utilities.DataStreamManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectCollectionController {
    @FXML
    public Label titleLabel;

    @FXML
    public VBox resultBox;

    @FXML
    public void backToMovie() throws IOException {
        new MovieInfoWindow().load();
    }

    @FXML
    public Button selectButton;

    public String collection = null;

    @FXML
    public void selectOne() {
        var c = DataStreamManager.conn;
        try {
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery("select count(*)\n" +
                    "from p320_05.\"CollectionMovie\"\n" +
                    "where \"CollectionID\" = " + this.collection + " and \"MovieID\" = " + AppStorage.search);
            rs.next();
            int result = rs.getInt(1);
            if (result != 0) {
                titleLabel.setText("   You have this one in the collection");
            } else {
                titleLabel.setText("   Movie added");
                statement.executeQuery("INSERT INTO p320_05.\"CollectionMovie\" (\"CollectionID\", \"MovieID\") " +
                        "VALUES (" + this.collection + ", " + AppStorage.search + ")"
                );
            }
        } catch (SQLException e) {
            System.out.println("abc");
        }
    }

    public void addTo(String CollectionID, String collectionName) {
        this.collection = CollectionID;
        titleLabel.setText("   " + collectionName);
        selectButton.setDisable(false);
    }

    @FXML
    public void initialize() {
        selectButton.setDisable(true);
        var c = DataStreamManager.conn;
        try {
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery("select count(*)\n" +
                    "from p320_05.\"Collection\"\n" +
                    "where \"UserID\" = " + AppStorage.userID);
            rs.next();
            int result = rs.getInt(1);
            if (result == 0 ) {
                titleLabel.setText("  You have no collection");
            } else {
                titleLabel.setText("  Choose a collection");
                rs = statement.executeQuery("select \"CollectionName\", \"CollectionID\"\n" +
                        "from p320_05.\"Collection\"\n" +
                        "where \"UserID\" = " + AppStorage.userID);
                while (rs.next()) {
                    for (int i = 1; i <= 1; i++) {
                        String columnValue = rs.getString(i);
                        String collectionID = rs.getString(2);
                        Button button = new Button(columnValue);
                        resultBox.getChildren().add(button);
                        button.setOnAction(EventHandler -> addTo(collectionID, columnValue));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
