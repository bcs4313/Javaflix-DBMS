package com.core.javaflix.dashboard;

import com.core.javaflix.*;
import com.core.javaflix.dashboard.collections.CollectionWindow;
import com.core.javaflix.dashboard.friends.friending.FriendsWindow;
import com.core.javaflix.dashboard.profile.ProfileWindow;
import com.core.javaflix.dashboard.subwindows.MovieInfoWindow;
import com.core.javaflix.utilities.AppStorage;
import com.core.javaflix.utilities.DataStreamManager;
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

    private boolean descending = true;

    private String ascensionStatement = " ORDER BY p320_05.\"Movie\".\"Title\" DESC";
    private String ascensionStatement2 = " ORDER BY M.\"Title\" DESC";

    @FXML
    private Button friendsButton;

    @FXML
    private Button collectionButton;

    @FXML
    private Button profileButton;

    @FXML
    private Button ascensionButton;

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
    public void sendToLogin()
    {
        BaseApplication.load();
    }

    @FXML
    private void updateMovieList() throws SQLException {
        String movieSearch = searchInput.getText();

        Button temp = ascensionButton;
        ascensionButton = new Button();
        ascensionButton.setText(temp.getText());
        ascensionButton.setOnAction(temp.getOnAction());

        // de-populate buttons
        List<Node> c = MovieBox.getChildren();
        int size = c.size();
        for(int i = 0; i < size; i++)
        {
            c.remove(0);
        }

        MovieBox.getChildren().add(ascensionButton);

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
        else if(studioCheck.isSelected())
        {
            loadButtons(populateByStudio(movieSearch));
        }
        else if(genreCheck.isSelected())
        {
            loadButtons(populateByGenre(movieSearch));
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
               " FROM p320_05.\"Movie\" WHERE lower(\"Title\") LIKE \'%" +
               search.toLowerCase() + "%\'" + ascensionStatement);
       return rs;
   }

    public ResultSet populateByDate(String search) throws SQLException {
        var c = DataStreamManager.conn;
        Statement statement = c.createStatement();
        ResultSet rs = statement.executeQuery(  "SELECT DISTINCT p320_05.\"Movie\".\"Title\", p320_05.\"Movie\".\"MovieID\"" +
                " FROM p320_05.\"Movie\" WHERE \"ReleaseDate\" LIKE \'%" + search + "%\'" + ascensionStatement);
        return rs;
    }

    public ResultSet populateByCast(String search) throws SQLException {
        String[] members = search.split(" ");
        String start = members[0];
        String builder = "";
        for(int i = 1; i < members.length; i++) {
            builder += " AND lower(P.\"FirstName\") LIKE %" +  members[i].toLowerCase() + "% OR " + "lower(P.\"LastName\") " +
                    "LIKE %" + members[i].toLowerCase() + "%" + ascensionStatement;
        }
        var c = DataStreamManager.conn;
        Statement statement = c.createStatement();

        try {
            ResultSet rs = statement.executeQuery("SELECT M.\"Title\", M.\"MovieID\" FROM\n" +
                    " p320_05.\"Movie\" M, p320_05.\"CastMovie\" CM, p320_05.\"Person\" P WHERE\n" +
                    "M.\"MovieID\" = CM.\"MovieID\" AND P.\"PersonID\"= CM.\"PersonID\" " +
                    "AND (lower(P.\"FirstName\") LIKE '%" + start.toLowerCase() + "%' OR lower(P.\"LastName\") LIKE '%" + start.toLowerCase() + "%'" +
                    builder + ")" + " GROUP BY M.\"MovieID\" "  + ascensionStatement2);
            return rs;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet populateByStudio(String search) throws SQLException {
        var c = DataStreamManager.conn;
        Statement statement = c.createStatement();
        ResultSet rs = statement.executeQuery(  "SELECT M.\"Title\", M.\"MovieID\" FROM\n" +
                " p320_05.\"Movie\" M, p320_05.\"StudioMovie\" SM, p320_05.\"Studio\" S WHERE\n" +
                " M.\"MovieID\" = SM.\"MovieID\" AND S.\"StudioID\" = SM.\"StudioID\" " +
                "AND (lower(S.\"Name\") LIKE '%" + search.toLowerCase() + "%')" + ascensionStatement2);
        return rs;
    }

    public ResultSet populateByGenre(String search) throws SQLException
    {
        var c = DataStreamManager.conn;
        Statement statement = c.createStatement();
        ResultSet rs = statement.executeQuery(  "SELECT M.\"Title\", M.\"MovieID\" FROM\n" +
                " p320_05.\"Movie\" M, p320_05.\"GenreMovie\" GM, p320_05.\"Genre\" G WHERE\n" +
                "M.\"MovieID\" = GM.\"MovieID\" AND G.\"Name\" = GM.\"GenreName\" " +
                "AND (lower(G.\"Name\") LIKE '%" + search.toLowerCase() + "%')" +
                " GROUP BY M.\"MovieID\" " + ascensionStatement2);
        return rs;
    }

    /*
    "select R.\"FollowID\", S.\"Username\"\n" +
                    "from p320_05.\"UserFollow\" R, p320_05.\"User\" S\n" +
                    "where R.\"UserID\" = "  + BaseApplication.storage.userID + "\n" +
                    "and R.\"FollowID\" = S.\"UserID\""
     */

    @FXML
    private void flipOrder() throws SQLException {
        if(descending)
        {
            ascensionButton.setText("Ascending ^");
            ascensionStatement = " ORDER BY p320_05.\"Movie\".\"Title\" ASC";
            ascensionStatement2 = " ORDER BY M.\"Title\" ASC";
        }
        else
        {
            ascensionButton.setText("Descending v");
            ascensionStatement = " ORDER BY p320_05.\"Movie\".\"Title\" DESC";
            ascensionStatement2 = " ORDER BY M.\"Title\" DESC";
        }
        descending = !descending;
        updateMovieList();
    }

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
