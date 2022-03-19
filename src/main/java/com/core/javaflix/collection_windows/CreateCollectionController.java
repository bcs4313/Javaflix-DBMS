package com.core.javaflix.collection_windows;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class CreateCollectionController {

    @FXML
    private Button cancelButton;

    @FXML
    private void sendToCollections() throws IOException {
        new CollectionWindow().load();
    }

    @FXML
    private void createCollection()
    {
        System.out.println("Ligma");
    }

}
