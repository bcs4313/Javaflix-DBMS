package com.core.javaflix;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateCollectionController {

    @FXML
    private Button cancelButton;

    @FXML
    private TextField collectionNameInput;

    @FXML
    private void sendToCollections() throws IOException {
        new CollectionWindow().load();
    }

    @FXML
    private void createCollection() throws IOException, SQLException {
        int id = AppStorage.userID;
        String inputText = collectionNameInput.getText();
        var c = DataStreamManager.conn;
        System.out.println(c.getCatalog());
        Statement statement = c.createStatement();
        ResultSet rs = statement.executeQuery("INSERT INTO p320_05.\"Collection\" (\"CollectionID\", \"UserID\", \"CollectionName\") VALUES (2, " + id + ", \'" + inputText + "\')");
        rs.next();
        System.out.println(collectionNameInput.getText());
        new CollectionWindow().load();
    }

}
