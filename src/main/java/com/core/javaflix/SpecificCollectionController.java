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
    public void sendToCollections() throws IOException, SQLException {
        new CollectionWindow().load();
    }


    @FXML
    public void initialize() throws SQLException {
        ArrayList<Button> buttonList = new ArrayList<Button>();
        ArrayList<Integer> buttonID = new ArrayList<Integer>();

        /**
         * Eventhandler handles when a user clicks on a collection button in the Vbox
         */
        EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    new SpecificCollectionWindow().load();
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }
        };
        /**
         * Creates a SQL query to retrieve the users list of collection
         */
        var c = DataStreamManager.conn;
        System.out.println(c.getCatalog());
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
            button.setOnAction(buttonHandler);
            buttonList.add(button);
            buttonID.add(movieid);
            movieList.getChildren().add(button);
        }
    }
}
