package com.core.javaflix;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.*;

public class MovieInfoController {
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
    public void sentToDashboard() throws IOException {
        AppStorage.search = null;
        new DashboardWindow().load();
    }

    @FXML
    public void collectionSelection() throws IOException {
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
    public void rateMovie() {
        try {
            if (rateField.getText().trim().isEmpty()) {
                rateLabel.setText("Enter value");
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
        }
    }

    @FXML
    public void initialize() {
        try {
            var c = DataStreamManager.conn;
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery("select \"Title\", \"ReleaseDate\", \"Duration\", \"mpaa\"\n" +
                    "from p320_05.\"Movie\"\n" +
                    "where \"MovieID\" = " + AppStorage.search);
            rs.next();
            resultBox.getChildren().add(new Label("MOVIE INFORMATION"));
            resultBox.getChildren().add(new Label("Title: " + rs.getString(1)));
            resultBox.getChildren().add(new Label("Release Date: " + rs.getString(2)));
            resultBox.getChildren().add(new Label("Duration: " + rs.getString(3)));
            resultBox.getChildren().add(new Label("MPAA: " + rs.getString(4)));

            rs = statement.executeQuery("select avg(rate)\n" +
                    "from p320_05.\"UserMovie\"\n" +
                    "where \"MovieID\" = " + AppStorage.search);
            rs.next();
            String rate = rs.getString(1);
            if (rate == null) {
                resultBox.getChildren().add(new Label("Rate: Nobody rated"));
            } else {
                resultBox.getChildren().add(new Label("Rate: " + rate));
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

            movieMember.getChildren().add(new Label("\nCast Members:"));
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
