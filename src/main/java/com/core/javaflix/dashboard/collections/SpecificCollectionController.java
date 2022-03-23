package com.core.javaflix.dashboard.collections;

import com.core.javaflix.BaseApplication;
import com.core.javaflix.dashboard.subwindows.MovieInfoWindow;
import com.core.javaflix.utilities.AbstractWindow;
import com.core.javaflix.utilities.AppStorage;
import com.core.javaflix.utilities.DataStreamManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

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

    public ArrayList<Integer> listOfMovie = new ArrayList<>();

    public void watch(int position) {
        int id = listOfMovie.get(position);
        try {
            Date current = new Date(System.currentTimeMillis());
            var c = DataStreamManager.conn;
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery("select count(*)\n" +
                    "from p320_05.\"UserMovie\"\n" +
                    "where \"UserID\" =" + AppStorage.userID + "\n" +
                    "and \"MovieID\" = " + id);
            rs.next();
            int result = rs.getInt(1);
            if (result == 0) {
                statement.executeQuery("INSERT INTO p320_05.\"UserMovie\"(\"UserID\", \"MovieID\", \"watchDate\")" +
                        " VALUES (" + AppStorage.userID + ", " + id + ", '" + new Date(System.currentTimeMillis()) + "')");
            } else {
                statement.executeQuery("UPDATE p320_05.\"UserMovie\" " +
                        "SET \"watchDate\" = '" + current  + "', \"watchedTime\" = 1 + \"watchedTime\"" +
                        " where \"UserID\" = " + AppStorage.userID + " and \"MovieID\" = " + id);
            }
        } catch (SQLException e) {
        }
    }

    @FXML
    public void markAsWatched()
    {
        int size = listOfMovie.size();
        for(int i = 0; i < size; i++) {
            watch(i);
        }
    }
    @FXML
    public void goBack() throws IOException, SQLException {
        AbstractWindow.loadLastPage();
    }

    @FXML
    public void removeCollection() throws SQLException, IOException {
        var c = DataStreamManager.conn;
        Statement statement = c.createStatement();
        statement.execute("DELETE FROM p320_05.\"Collection\" WHERE p320_05.\"Collection\".\"CollectionID\" = " + AppStorage.collectionID + "");
        AbstractWindow.loadLastPage();

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
        BaseApplication.storage.pageStorage.add(new SpecificCollectionWindow());
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
            listOfMovie.add(movieid);
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
