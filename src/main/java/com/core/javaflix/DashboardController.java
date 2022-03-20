package com.core.javaflix;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DashboardController {

    @FXML
    private Button friendsButton;

    @FXML
    private Button collectionButton;

    @FXML
    private Button profileButton;

    @FXML
    private TextField searchInput;

    @FXML
    private VBox MovieBox;

    @FXML
    private CheckBox nameCheck;
    @FXML
    private CheckBox dateCheck;
    @FXML
    private CheckBox castCheck;
    @FXML
    private CheckBox studioCheck;
    @FXML
    private CheckBox genreCheck;

    @FXML
    private void updateMovieList() throws SQLException {
        String movieSearch = searchInput.getText();

        // de-populate buttons
        List<Node> c = MovieBox.getChildren();
        int size = c.size();
        for(int i = 0; i < size; i++)
        {
            c.remove(0);
        }

        if(nameCheck.isSelected())
        {
            loadButtons(populateByName(movieSearch));
        }
        else if(dateCheck.isSelected())
        {
            loadButtons(populateByDate(movieSearch));
        }
        else if(castCheck.isSelected())
        {
            loadButtons(populateByCast(movieSearch));
        }
    }
    // generate buttons according to a query resultset in VBox
   public void loadButtons(ResultSet rs) throws SQLException {

        int index = 0;
        int max = 100;
       while(rs.next() && index < max)
       {
           String title = rs.getString("Title");
           String id = rs.getString("MovieID");
           System.out.println("Movie: " + title + " , ID: " + id);
           Button b = new Button();
           b.setText(title);
           b.setMaxWidth(1000000.0);
           MovieBox.getChildren().add(b);
           index++;
           EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
               @Override
               public void handle(ActionEvent event) {
                   try {
                       AppStorage.search = String.valueOf(id);
                       search();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
           };

           b.setOnAction(buttonHandler);
       }
   }

   public ResultSet populateByName(String search) throws SQLException {
       var c = DataStreamManager.conn;
       Statement statement = c.createStatement();
       ResultSet rs = statement.executeQuery(  "SELECT DISTINCT p320_05.\"Movie\".\"Title\", p320_05.\"Movie\".\"MovieID\"" +
               " FROM p320_05.\"Movie\" WHERE lower(\"Title\") LIKE \'%" + search.toLowerCase() + "%\'");
       return rs;
   }

    public ResultSet populateByDate(String search) throws SQLException {
        var c = DataStreamManager.conn;
        Statement statement = c.createStatement();
        ResultSet rs = statement.executeQuery(  "SELECT DISTINCT p320_05.\"Movie\".\"Title\", p320_05.\"Movie\".\"MovieID\"" +
                " FROM p320_05.\"Movie\" WHERE \"ReleaseDate\" LIKE \'%" + search + "%\'");
        return rs;
    }

    public ResultSet populateByCast(String search) throws SQLException {
        String[] members = search.split(" ");
        String start = members[0];
        String builder = "";
        for(int i = 1; i < members.length; i++) {
            builder += " OR (P.\"FirstName\" LIKE %" +  members[i] + "% OR " + "P.\"LastName\" LIKE %" + members[i] + "%";
        }
        var c = DataStreamManager.conn;
        Statement statement = c.createStatement();
        /*
        ResultSet rs = statement.executeQuery("SELECT M.\"Title\", M.\"MovieID\", FROM\n" +
                "p320_05.\"Movie\" M, p320_05.\"CastMovie\" CM, p320_05.\"Person\" P WHERE\n" +
                "M.\"MovieID\" = CM.\"MovieID\" AND P.\"PersonID\"= CM.\"PersonID\" " +
                "AND (P.\"FirstName\" = '" + start +  "' OR P.\"LastName\" = '" + start + "'" + builder + ")");

         */
        try {
            ResultSet rs = statement.executeQuery("SELECT M.\"Title\", M.\"MovieID\" FROM\n" +
                    " p320_05.\"Movie\" M, p320_05.\"CastMovie\" CM, p320_05.\"Person\" P WHERE\n" +
                    "M.\"MovieID\" = CM.\"MovieID\" AND P.\"PersonID\"= CM.\"PersonID\" " +
                    "AND (P.\"FirstName\" LIKE '%" + start + "%' OR P.\"LastName\" LIKE '%" + start + "%')");
            return rs;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /*
    "select R.\"FollowID\", S.\"Username\"\n" +
                    "from p320_05.\"UserFollow\" R, p320_05.\"User\" S\n" +
                    "where R.\"UserID\" = "  + BaseApplication.storage.userID + "\n" +
                    "and R.\"FollowID\" = S.\"UserID\""
     */
    @FXML
    private void sendToCollections() throws IOException, SQLException {
        new CollectionWindow().load();
    }

    @FXML
    private void sendToFriends() throws IOException {
        new FriendsWindow().load();
    }

    @FXML
    private void sendToProfile() throws IOException {
        new ProfileWindow().load();
    }

    @FXML
    private void search() throws IOException {
        new MovieInfoWindow().load();
    }

}
