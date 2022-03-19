package com.core.javaflix;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;


public class DatasetLoader {
    // these lists prevent duplicates from existing within our database
    public static ArrayList<String> nameList = new ArrayList<>();
    public static ArrayList<Integer> personIdList = new ArrayList<>();
    public static ArrayList<String> genreList = new ArrayList<>();
    public static ArrayList<Integer> studioIdList = new ArrayList<>();
    public static ArrayList<String> studioStringList = new ArrayList<>();

    public static void main(String[] args)
    {
        String line = "";
        String path = "C:\\Users\\Drago\\Documents\\JavaFlix\\src\\main\\resources\\datasets\\movieSet.csv";
        int IDCap = 8201;
        int MovieID = 0;
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(path));
            // skip initial lines
            for(int i = 0; i < 8; i++) {
                line = br.readLine();
            }
            DataStreamManager streamManager = new DataStreamManager();
            streamManager.establishConnection();

            // delete ALL rows from members
            var c1 = DataStreamManager.conn;
            Statement statement3 = c1.createStatement();
            statement3.execute("DELETE FROM p320_05.\"Movie\"");
            statement3.execute("DELETE FROM p320_05.\"Person\"");
            statement3.execute("DELETE FROM p320_05.\"CastMovie\"");
            statement3.execute("DELETE FROM p320_05.\"DirectMovie\"");
            statement3.execute("DELETE FROM p320_05.\"Genre\"");
            statement3.execute("DELETE FROM p320_05.\"Genre\"");
            statement3.execute("DELETE FROM p320_05.\"GenreMovie\"");
            statement3.execute("DELETE FROM p320_05.\"Studio\"");
            statement3.execute("DELETE FROM p320_05.\"StudioMovie\"");
            while ((line = br.readLine()) != null && MovieID < IDCap)   //returns a Boolean value
            {
                String[] row = line.split(",");    // use comma as separator
                MovieID = Integer.parseInt(row[0]); // movie int



                var c = DataStreamManager.conn;
                try {
                    // movie creation section
                    Statement statement10 = c.createStatement();
                    System.out.println("Movie [Movie MovieID=" + MovieID + ", Title=" + row[1] + "," +
                            " Release Date=" + row[2] + ", Duration=" + row[19] + ", MPAA=" + row[10] + "]");

                    statement10.execute("INSERT INTO p320_05.\"Movie\" VALUES " +
                            "(" + MovieID + ", '" + row[1] + "', '" + row[2] + "',\n'" +
                            row[19] + "', '" + row[10] + "')");

                    // genre adding section
                    addGenre(row[11], MovieID);

                    // studio adding section
                    addStudio(row[20], MovieID);

                    // person adding section (director)
                    Statement statement = c.createStatement();
                    if(!nameList.contains(row[14])) // if director is in list already
                    {
                        if( row[14].split(" ").length != 2)
                        {
                            System.out.println("skip");
                        }
                        else {
                            // insert into person
                            String firstName = row[14].split(" ")[0];
                            String lastName = row[14].split(" ")[1];
                            int randomID = new Random().nextInt(9999999);
                            while (personIdList.contains(randomID)) {
                                randomID = new Random().nextInt(9999999);
                            }
                            statement.execute("INSERT INTO p320_05.\"Person\" VALUES " +
                                    "(" + randomID + ", '" + firstName + "', '" + lastName + "')");
                            nameList.add(row[14]);
                            personIdList.add(randomID);

                            // link to movie
                            linkDirectorToMovie(MovieID, randomID);

                        }
                    }

                    // person adding section (cast)
                    Statement statement1 = c.createStatement();
                    if(!nameList.contains(row[24])) // if director is in list already
                    {
                        String[] members = row[24].split("-");
                        for(int i = 0; i < members.length; i++) {
                            System.out.println(members[i]);
                            if (members[i].split(" ").length < 2) {
                                System.out.println("skip");
                            } else {
                                System.out.println("noskip");
                                String firstName = members[i].split(" ")[0];
                                String lastName = members[i].split(" ")[1];
                                int randomID = new Random().nextInt(9999999);
                                while (personIdList.contains(randomID)) {
                                    randomID = new Random().nextInt(9999999);
                                }

                                System.out.println("Inserting " + firstName + ", " + lastName + " " + randomID);
                                statement1.execute("INSERT INTO p320_05.\"Person\" VALUES " +
                                        "(" + randomID + ", '" + firstName + "', '" + lastName + "')");
                                nameList.add(members[i]);
                                personIdList.add(randomID);

                                // link to movie
                                linkCastToMovie(MovieID, randomID);
                            }
                        }
                    }
                }
                catch (SQLException e)
                {
                    System.out.println("Skipped Movie");
                    e.printStackTrace();
                }

            }
        }
        catch (IOException | SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void addGenre(String input, int movieID) throws SQLException {
        // split genres by hyphen
        String[] names = input.split("-");
        try {
            for (String name : names) {
                name = name.trim();
                if (!genreList.contains(name)) {
                    System.out.println("Adding genre " + name);
                    var c = DataStreamManager.conn;
                    Statement statement = c.createStatement();
                    statement.execute("INSERT INTO p320_05.\"Genre\" VALUES " +
                            "('" + name + "')");
                    genreList.add(name);
                }
                linkGenreToMovie(movieID, name);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void linkGenreToMovie(int movieID, String genre) throws SQLException {
        System.out.println("Linking " + genre + " to " + movieID);
        var c = DataStreamManager.conn;
        Statement statement = c.createStatement();
        statement.execute("INSERT INTO p320_05.\"GenreMovie\" VALUES " +
                "(" + movieID + ", '" + genre + "')");
    }

    public static void linkDirectorToMovie(int movieID, int personID) throws SQLException {
        System.out.println("Linking " + personID + " to " + movieID);
        var c = DataStreamManager.conn;
        Statement statement = c.createStatement();
        statement.execute("INSERT INTO p320_05.\"DirectMovie\" VALUES " +
                "(" + personID + ", " + movieID + ")");
    }

    public static void linkCastToMovie(int movieID, int personID) throws SQLException {
        System.out.println("Linking " + personID + " to " + movieID);
        var c = DataStreamManager.conn;
        Statement statement = c.createStatement();
        statement.execute("INSERT INTO p320_05.\"CastMovie\" VALUES " +
                "(" + personID + ", " + movieID + ")");
    }

    public static void addStudio(String studios, int movieID)
    {
        // split genres by hyphen
        String[] names = studios.split("-");
        try {
            for (String name : names) {
                name = name.trim();
                if (!studioStringList.contains(name)) {
                    System.out.println("Adding studio " + name);
                    var c = DataStreamManager.conn;

                    // generate an ID for the studio
                    int randomID = new Random().nextInt(9999999);
                    while (studioIdList.contains(randomID)) {
                        randomID = new Random().nextInt(9999999);
                    }

                    Statement statement = c.createStatement();
                    statement.execute("INSERT INTO p320_05.\"Studio\" VALUES " +
                            "(" + randomID + ", '" + name + "')");
                    studioStringList.add(name);


                    linkMovieToStudio(randomID, movieID);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void linkMovieToStudio(int studioID, int movieID) throws SQLException {
        System.out.println("Linking " + studioID + " to " + movieID);
        var c = DataStreamManager.conn;
        Statement statement = c.createStatement();
        statement.execute("INSERT INTO p320_05.\"StudioMovie\" VALUES " +
                "(" + movieID + ", " + studioID + ")");
    }

    public static void initializeExistingValues()
    {
        nameList = new ArrayList<>();
        personIdList = new ArrayList<>();
        genreList = new ArrayList<>();
    }
}

