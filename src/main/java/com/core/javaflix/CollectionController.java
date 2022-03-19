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

public class CollectionController {

    @FXML
    private VBox collectionList;

    @FXML
    private Button createCollection;

    @FXML
    private Button backButton;


    @FXML
    private void sendToDashboard() throws IOException {
        new DashboardWindow().load();
    }

    @FXML
    private void sendToCreateCollection() throws IOException {
        new CreateCollectionWindow().load();
    }

    @FXML
    public void initialize() throws SQLException {
        ArrayList<Integer> collectIDList = new ArrayList<Integer>();
        ArrayList<String> collectNameList = new ArrayList<String>();

        /**
         * Eventhandler handles when a user clicks on a collection button in the Vbox
         */
        EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    for(int i = 0; i<collectNameList.size(); i++)
                    {
                        if(collectNameList.get(i).equals(event.getTarget().toString().split("\'")[1]))
                        {
                            AppStorage.collectionID = collectIDList.get(i);
                        }
                    }
                    new SpecificCollectionWindow().load();
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }
        };
        /**
         * Creates a SQL query to retrieve the users list of collection
         */
        int userid = AppStorage.userID;
        var c = DataStreamManager.conn;
        System.out.println(c.getCatalog());
        Statement statement = c.createStatement();
        ResultSet rs = statement.executeQuery("SELECT p320_05.\"Collection\".\"CollectionName\", p320_05.\"Collection\".\"CollectionID\" FROM p320_05.\"Collection\" WHERE \"UserID\" = \'" + userid + "\' ORDER BY p320_05.\"Collection\".\"CollectionName\" ASC");
        while(rs.next())
        {
            Button button = new Button(rs.getString("CollectionName"));
            collectIDList.add(Integer.valueOf(rs.getString("CollectionID")));
            collectNameList.add(rs.getString("CollectionName"));
            button.setOnAction(buttonHandler);
            collectionList.getChildren().add(button);
        }
    }

}