package com.core.javaflix;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class CollectionController {

    @FXML
    private ScrollPane collectionList;

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
}