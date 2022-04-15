package com.core.javaflix.dashboard.subwindows;

import com.core.javaflix.BaseApplication;
import com.core.javaflix.dashboard.friends.user_search.UserWindow;
import com.core.javaflix.utilities.AppStorage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class Movie {

    private int movieID;
    private String title;
    private double rating;
    private int played;
    private Button button;

    public Movie(int movieID, String title, double rating,  int played) {
        this.movieID = movieID;
        this.title = title;
        this.rating = rating;
        this.played = played;
        this.button = new Button("GoTo");
        try {
            this.button.setOnAction(actionEvent -> {
                try {
                    this.goToMovie(this.movieID);
                } catch (IOException e) {}
            });
        } catch (Exception e) {}
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getPlayed() {
        return played;
    }

    public void setPlayed(int played) {
        this.played = played;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    /**
     * Listener for when the user clicks on the "visit" button
     * redirect user to friends page
     */
    @FXML
    public static void goToMovie(int movieID) throws IOException {
        try {
            BaseApplication.storage.search = "" + movieID;
            BaseApplication.storage.pageStorage.add(new UserWindow());
            new MovieInfoWindow().load();
        }
        catch (Exception e) {

        }
    }
}
