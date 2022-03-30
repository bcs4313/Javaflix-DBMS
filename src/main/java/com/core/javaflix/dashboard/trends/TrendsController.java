package com.core.javaflix.dashboard.trends;

import com.core.javaflix.BaseApplication;
import com.core.javaflix.dashboard.DashboardWindow;
import com.core.javaflix.dashboard.collections.CollectionWindow;
import com.core.javaflix.dashboard.friends.friending.FriendsWindow;
import com.core.javaflix.dashboard.friends.user_search.UserWindow;
import com.core.javaflix.dashboard.subwindows.MovieInfoWindow;
import com.core.javaflix.utilities.AbstractWindow;
import com.core.javaflix.utilities.AppStorage;
import com.core.javaflix.utilities.DataStreamManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.Date;
import java.util.List;

/**
 * The trends controller establishes top movies to the user at the press of a button.
 * The algorithm for determining top "releases" is relatively simple.
 *
 * movieScore = watchedTimes * starRating^1.7;
 * // gives higher weight in score to well-received movies
 *
 * Top X most popular movies operate ONLY by watches
 * movieScore = watchedTimes
 *
 *
 */
public class TrendsController {
    @FXML
    private VBox MovieBox;

    @FXML
    private void Populate20from90d() throws SQLException {
        prePopulate();
        // we need to generate a comparable date here...
        Date pastDate = new Date(System.currentTimeMillis());
        pastDate.setTime(pastDate.getTime() - Duration.ofDays(90).toMillis());

        // comparing the current date with the past in this query
        var c = DataStreamManager.conn;
        Statement statement = c.createStatement();
        ResultSet rs = statement.executeQuery(  "SELECT M.\"Title\", UM.\"MovieID\"" +
                " FROM p320_05.\"Movie\" M, p320_05.\"UserMovie\" UM WHERE UM.\"watchDate\" >= '"  + pastDate +
                "' AND UM.\"MovieID\" = M.\"MovieID\" GROUP BY UM.\"MovieID\", M.\"Title\"" +
                " ORDER BY SUM(UM.\"watchedTime\") DESC LIMIT 20");
        loadButtons(rs);
    }

    @FXML
    private void populate20Friends()
    {

    }

    @FXML
    private void populate5Releases()
    {

    }

    @FXML
    private void populateRecommended()
    {

    }

    /**
     * Executed before any search occurs.
     */
    public void prePopulate()
    {
        // de-populate buttons
        List<Node> c = MovieBox.getChildren();
        int size = c.size();
        for(int i = 0; i < size; i++)
        {
            c.remove(0);
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
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
               }
           };

           b.setOnAction(buttonHandler);
       }
   }

   @FXML
   private void goBack()
   {
       AbstractWindow.loadLastPage();
   }

    private void search() throws IOException {
        BaseApplication.storage.pageStorage.add(new DashboardWindow());
        new MovieInfoWindow().load();
    }
}
