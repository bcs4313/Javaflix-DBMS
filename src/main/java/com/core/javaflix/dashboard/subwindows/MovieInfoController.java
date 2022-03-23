package com.core.javaflix.dashboard.subwindows;

import com.core.javaflix.BaseApplication;
import com.core.javaflix.dashboard.collections.SelectCollectionWindow;
import com.core.javaflix.dashboard.collections.SpecificCollectionWindow;
import com.core.javaflix.utilities.AbstractWindow;
import com.core.javaflix.utilities.AppStorage;
import com.core.javaflix.utilities.DataStreamManager;
import com.core.javaflix.dashboard.DashboardWindow;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.*;

public class MovieInfoController {
    @FXML
    public Label rate;

    @FXML
    public Label titleLabel;

    @FXML
    public Label dateLabel;

    @FXML
    public Label durationLabel;

    @FXML Label mpaaLabel;

    @FXML
    public VBox resultBox;

    @FXML
    public TextField rateField;

    @FXML
    public VBox movieMember;

    @FXML
    public VBox commandBox;

    @FXML
    public Label watchLabel;

    @FXML
    public Label rateLabel;

    @FXML
    public Button deleteButton;

    @FXML
    public void goBack() throws IOException, SQLException {
        AbstractWindow.loadLastPage();
    }

    @FXML
    public void refreshPage() throws IOException {
        new MovieInfoWindow().load();
    }

    @FXML
    public void collectionSelection() throws IOException {
        BaseApplication.storage.pageStorage.add(new MovieInfoWindow());
        new SelectCollectionWindow().load();
    }

    @FXML
    public void watchMovie() {
        try {
            Date current = new Date(System.currentTimeMillis());
            watchLabel.setText("Watch at " + current.toString());
            var c = DataStreamManager.conn;
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery("select count(*)\n" +
                    "from p320_05.\"UserMovie\"\n" +
                    "where \"UserID\" =" + AppStorage.userID + "\n" +
                    "and \"MovieID\" = " + AppStorage.search);
            rs.next();
            int result = rs.getInt(1);
            if (result == 0) {
                statement.executeQuery("INSERT INTO p320_05.\"UserMovie\"(\"UserID\", \"MovieID\", \"watchDate\")" +
                        " VALUES (" + AppStorage.userID + ", " + AppStorage.search + ", '" + new Date(System.currentTimeMillis()) + "')");
            } else {
                statement.executeQuery("UPDATE p320_05.\"UserMovie\" " +
                        "SET \"watchDate\" = '" + current  + "', \"watchedTime\" = 1 + \"watchedTime\"" +
                        " where \"UserID\" = " + AppStorage.userID + " and \"MovieID\" = " + AppStorage.search);
            }
        } catch (SQLException e) {
        }
    }

    @FXML
    public void rateMovie() throws IOException {
        try {
            if (rateField.getText().trim().isEmpty()) {
                rateLabel.setText("Enter value 0-5");
            } else {
                double result = Double.parseDouble(rateField.getText());
                if (result > 5 || result < 0) {
                    rateLabel.setText("Rate must between 0-5");
                } else {
                    var c = DataStreamManager.conn;
                    Statement statement = c.createStatement();
                    ResultSet rs = statement.executeQuery("select count(*)\n" +
                            "from p320_05.\"UserMovie\"\n" +
                            "where \"UserID\" = " + AppStorage.userID + "\n" +
                            "and \"MovieID\" = " + AppStorage.search);
                    rs.next();
                    int count = rs.getInt(1);
                    if (count == 0) {
                        rateLabel.setText("You Never Watch");
                    } else {
                        rateLabel.setText("rated");
                        statement.executeQuery("UPDATE p320_05.\"UserMovie\" " +
                                "SET \"rate\" = " + result +
                                "where \"UserID\" = " + AppStorage.userID +" and \"MovieID\" = " + AppStorage.search);
                    }
                }
            }
        } catch (SQLException e){

        } catch (NumberFormatException e) {
            rateLabel.setText("Enter value 0-5");
        }
    }

    @FXML
    public void deleteFromCollection() {
        try {

            var c = DataStreamManager.conn;
            Statement statement = c.createStatement();
            statement.executeQuery("delete\n" +
                    "from p320_05.\"CollectionMovie\"\n" +
                    "where \"MovieID\" = " + AppStorage.search + "\n" +
                    "and \"CollectionID\" = " + AppStorage.collectionID);
        } catch (SQLException e) {

        }
    }

    @FXML
    public void initialize() {
        try {
            if (AppStorage.inCollection == false) {
                deleteButton.setDisable(true);
            } else {
                deleteButton.setDisable(false);
            }
            var c = DataStreamManager.conn;
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery("select \"Title\", \"ReleaseDate\", \"Duration\", \"mpaa\"\n" +
                    "from p320_05.\"Movie\"\n" +
                    "where \"MovieID\" = " + AppStorage.search);
            rs.next();
            titleLabel.setText("Title: " + rs.getString(1));
            dateLabel.setText("Release Date: " + rs.getString(2));
            durationLabel.setText("Duration: " + rs.getString(3));
            mpaaLabel.setText("MPAA: " + rs.getString(4));

            rs = statement.executeQuery("select avg(rate)\n" +
                    "from p320_05.\"UserMovie\"\n" +
                    "where \"MovieID\" = " + AppStorage.search);
            rs.next();
            String rate = rs.getString(1);
            if (rate == null) {
                this.rate.setText("Rate: Nobody rated");
            } else {
                this.rate.setText("Rate: " + rate);
            }
            rs = statement.executeQuery("select L.\"FirstName\", L.\"LastName\"\n" +
                    "from p320_05.\"Person\" L, p320_05.\"DirectMovie\" R\n" +
                    "where R.\"MovieID\" = +" + AppStorage.search + "\n" +
                    "and R.\"PersonID\" = L.\"PersonID\"");
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            movieMember.getChildren().add(new Label("Director:"));
            while (rs.next()) {
                String name = rs.getString(1) + " " + rs.getString(2);
                movieMember.getChildren().add(new Label(name));
            }
            movieMember.getChildren().add(new Label(""));
            movieMember.getChildren().add(new Label("Cast Members:"));
            rs = statement.executeQuery("select L.\"FirstName\", L.\"LastName\"\n" +
                    "from p320_05.\"Person\" L, p320_05.\"CastMovie\" R\n" +
                    "where R.\"MovieID\" = +" + AppStorage.search + "\n" +
                    "and R.\"PersonID\" = L.\"PersonID\"");
            while (rs.next()) {
                String name = rs.getString(1) + " " + rs.getString(2);
                movieMember.getChildren().add(new Label(name));
            }
        } catch (SQLException e) {
        }
    }
}
