package com.core.javaflix.dashboard.collections;

import com.core.javaflix.utilities.AbstractWindow;
import com.core.javaflix.utilities.AppStorage;
import com.core.javaflix.utilities.DataStreamManager;
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
    private void goBack() throws IOException, SQLException {
        AbstractWindow.loadLastPage();
    }

    @FXML
    private void createCollection() throws IOException, SQLException {
        int userid = AppStorage.userID;
        String inputText = collectionNameInput.getText();
        var c = DataStreamManager.conn;
        System.out.println(c.getCatalog());
        Statement statement = c.createStatement();
        ResultSet rs = statement.executeQuery("SELECT p320_05.\"Collection\".\"CollectionID\" FROM p320_05.\"Collection\" ORDER BY p320_05.\"Collection\".\"CollectionID\" DESC");
        if(rs.next()) {
            int collectionID = (rs.getInt(1)) + 1;
            statement.execute("INSERT INTO p320_05.\"Collection\" (\"CollectionID\", \"UserID\", \"CollectionName\") VALUES (" + collectionID + ", " + userid + ", \'" + inputText + "\')");
        }
        else
        {
            statement.execute("INSERT INTO p320_05.\"Collection\" (\"CollectionID\", \"UserID\", \"CollectionName\") VALUES (0, " + userid + ", \'" + inputText + "\')");
        }
        AbstractWindow.loadLastPage();
    }

}
