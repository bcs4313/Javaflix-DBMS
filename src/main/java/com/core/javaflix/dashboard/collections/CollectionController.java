package com.core.javaflix.dashboard.collections;

import com.core.javaflix.BaseApplication;
import com.core.javaflix.utilities.AbstractWindow;
import com.core.javaflix.utilities.AppStorage;
import com.core.javaflix.utilities.DataStreamManager;
import com.core.javaflix.dashboard.DashboardWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CollectionController {

    @FXML
    private HBox CHBox;

    @FXML
    private VBox CollectionVBox;

    @FXML
    private VBox MCVBox;

    @FXML
    private VBox PlayLengthVBox;

    @FXML
    private Button createCollection;

    @FXML
    private Button backButton;

    @FXML
    private Label numberCollections;


    @FXML
    private void goBack() throws IOException {
        AbstractWindow.loadLastPage();
        BaseApplication.storage.collectionID = -1;
    }

    @FXML
    private void sendToCreateCollection() throws IOException {
        BaseApplication.storage.pageStorage.add(new CollectionWindow());
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
                    AppStorage.collectionName = event.getTarget().toString().split("\'")[1];
                    BaseApplication.storage.pageStorage.add(new CollectionWindow());
                    new SpecificCollectionWindow().load();
                } catch (IOException e) {
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
        ResultSet rs = statement.executeQuery("SELECT C.\"CollectionName\", C.\"CollectionID\", COUNT(CM.\"CollectionID\")\n" +
                "FROM p320_05.\"CollectionMovie\" CM FULL OUTER JOIN\n" +
                " p320_05.\"Collection\" C ON C.\"CollectionID\" = CM.\"CollectionID\"\n" +
                "WHERE \"UserID\" = " + AppStorage.userID + " " +
                "group by C.\"CollectionName\", C.\"CollectionID\"\n" +
                "ORDER BY C.\"CollectionName\" ASC");
        while(rs.next())
        {
            Button button = new Button(rs.getString("CollectionName"));
            button.setPrefHeight(40);
            collectIDList.add(Integer.valueOf(rs.getString("CollectionID")));
            collectNameList.add(rs.getString("CollectionName"));
            button.setOnAction(buttonHandler);
            CollectionVBox.getChildren().add(button);
            Label L = new Label(rs.getString("count"));
            L.setPrefHeight(40);
            MCVBox.getChildren().add(L);
        }

        // looping through collection names locally and querying each time
        for(int i = 1; i < CollectionVBox.getChildren().size(); i++) {
            Button b = (Button)CollectionVBox.getChildren().get(i);
            String collectionName = b.getText();
            rs = statement.executeQuery("SELECT M.\"Duration\"\n" +
                    "FROM p320_05.\"Collection\" C, p320_05.\"CollectionMovie\" CM, p320_05.\"Movie\" M\n" +
                    "WHERE C.\"CollectionName\" = '" + collectionName + "'\n" +
                    "AND CM.\"CollectionID\" = C.\"CollectionID\" AND\n" +
                    "M.\"MovieID\" = CM.\"MovieID\"");
            int totalHour = 0;
            int totalMinute = 0;
            while (rs.next()) {
                String duration = rs.getString(1);
                totalHour = totalHour + Integer.valueOf(duration.split("h")[0]);
                totalMinute = totalMinute + Integer.valueOf(duration.split(" ")[1].split("m")[0]);
            }
            Label LL = new Label("Total Time: " + totalHour + "h " + totalMinute + "m");
            LL.setPrefHeight(40);
            PlayLengthVBox.getChildren().add(i, LL);
        }

        // total collection counting
        rs = statement.executeQuery("select COUNT(C.\"CollectionName\")\n" +
                "from p320_05.\"Collection\" C\n" +
                "where C.\"UserID\" = " + AppStorage.userID);

        rs.next();
        numberCollections.setText("Total Collections: " + rs.getString(1));
    }

}