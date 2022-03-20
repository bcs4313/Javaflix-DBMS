package com.core.javaflix;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SpecificCollectionController {

    @FXML
    private Label collectionName;

    @FXML
    private Button goBack;

    @FXML
    private VBox movieList;

    @FXML
    private TextField newName;

    @FXML
    private Label numberOfMovies;

    @FXML
    private Label totalTime;

    public int movieID;
    @FXML
    public void sendToCollections() throws IOException, SQLException {
        new CollectionWindow().load();
    }

    @FXML
    public void removeCollection() throws SQLException, IOException {
        var c = DataStreamManager.conn;
        Statement statement = c.createStatement();
        statement.execute("DELETE FROM p320_05.\"Collection\" WHERE p320_05.\"Collection\".\"CollectionID\" = " + AppStorage.collectionID + "");
        new CollectionWindow().load();

    }

    @FXML
    public void changeCollectionName() throws SQLException, IOException {
        var c = DataStreamManager.conn;
        Statement statement = c.createStatement();
        statement.execute("UPDATE p320_05.\"Collection\" SET \"CollectionName\" = \'" + newName.getText() + "\' WHERE \"CollectionID\" = " + AppStorage.collectionID + "");
        AppStorage.collectionName = newName.getText();
        newName.clear();
        new SpecificCollectionWindow().load();

    }

    public void select(String movieID) throws IOException {
        AppStorage.inCollection = true;
        AppStorage.search = movieID;
        new MovieInfoWindow().load();
    }


    @FXML
    public void initialize() throws SQLException {
        ArrayList<Button> buttonList = new ArrayList<Button>();
        ArrayList<Integer> buttonID = new ArrayList<Integer>();
        /**
         * Creates a SQL query to retrieve the users list of collection
         */
        var c = DataStreamManager.conn;
        Statement statement = c.createStatement();
        Statement statement2 = c.createStatement();
        ResultSet rs = statement.executeQuery("SELECT p320_05.\"CollectionMovie\".\"MovieID\" FROM p320_05.\"CollectionMovie\" WHERE \"CollectionID\" = " + AppStorage.collectionID + "");
        collectionName.setText(AppStorage.collectionName + "");

        while(rs.next())
        {
            int movieid = Integer.valueOf(rs.getString("MovieID"));
            ResultSet ms = statement2.executeQuery("SELECT p320_05.\"Movie\".\"Title\" FROM p320_05.\"Movie\" WHERE \"MovieID\" = " + movieid + "");
            ms.next();
            Button button = new Button(ms.getString("Title"));
            button.setOnAction(EventHandler -> {
                try {
                    select(String.valueOf(movieid));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            buttonList.add(button);
            buttonID.add(movieid);
            movieList.getChildren().add(button);
        }

        /**
         * Gets and sets the total of number of movies in a users collection
         */
        rs = statement.executeQuery("SELECT COUNT(p320_05.\"CollectionMovie\".\"CollectionID\") FROM p320_05.\"CollectionMovie\" WHERE \"CollectionID\" = " + AppStorage.collectionID + "");
        rs.next();
        numberOfMovies.setText("Number of Movies: " + rs.getString(1));

        rs = statement.executeQuery("SELECT p320_05.\"CollectionMovie\".\"MovieID\" FROM p320_05.\"CollectionMovie\" WHERE \"CollectionID\" = " + AppStorage.collectionID + "");
        int totalHour = 0;
        int totalMinute = 0;
        while(rs.next())
        {
            ResultSet ms = statement2.executeQuery("SELECT p320_05.\"Movie\".\"Duration\" FROM p320_05.\"Movie\" WHERE \"MovieID\" =" + rs.getString(1) + "");
            ms.next();
            String duration = ms.getString(1);
            totalHour = totalHour + Integer.valueOf(duration.split("h")[0]);
            totalMinute = totalMinute + Integer.valueOf(duration.split(" ")[1].split("m")[0]);
        }
        totalTime.setText("Total Time: " + totalHour + "h " + totalMinute + "m");
    }
}
