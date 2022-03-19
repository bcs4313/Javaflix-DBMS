package com.core.javaflix;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class UserSearchController {
    @FXML
    private Label searchLabel;

    @FXML // contains email address of the user
    private TextField searchBar;

    @FXML //button to update the user's information
    private Button searchButton;

    @FXML //button to update the user's information
    private Button backButton;

    @FXML //column containing emails
    private TableView searchTable;

    /**
     * initialize the page with this information.
     */
    @FXML
    public void initialize() {

        //create columns
        searchTable = new TableView();

        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        TableColumn<User, String> nameColumn = new TableColumn<>("Name");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<User, Button> buttonColumn = new TableColumn<>("Visit");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("button"));

        //add columns
        searchTable.getColumns().add(emailColumn);
        searchTable.getColumns().add(usernameColumn);
        searchTable.getColumns().add(nameColumn);

        VBox vbox = new VBox(searchTable);
        //searchTable.getColumns().add(buttonColumn);

        //get values
        //searchTable.getItems().add(new User("test@gmail.com", "test", "Test Account"));

    }



    /**
     * Listener for when the user clicks on the "search" button
     * redirect user to this page, but with update info
     * @param actionEvent contains data regarding the nature of the interaction
     */
    @FXML
    public void searchUser(ActionEvent actionEvent) throws IOException {
        new UserSearchWindow().load();
    }

    /**
     * Listener for when the user clicks on the "back" button
     * redirect user to friends page
     * @param actionEvent contains data regarding the nature of the interaction
     */
    @FXML
    public void goBack(ActionEvent actionEvent) throws IOException {
        new FriendsWindow().load();
    }

    /**
     * Listener for when the user clicks on the "visit" button
     * redirect user to friends page
     * @param actionEvent contains data regarding the nature of the interaction
     */
    @FXML
    public static void visitUser(ActionEvent actionEvent) throws IOException {
        new FriendsWindow().load();
    }

}
