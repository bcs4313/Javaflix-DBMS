package com.core.javaflix.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DatasetLoader {
    public static void main(String[] args)
    {
        String line = "";
        String path = "C:\\Users\\Drago\\Documents\\JavaFlix\\src\\main\\resources\\datasets\\netflixMoviesProcessed.csv";
        int IDCap = 8201;
        int ID = 0;
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(path));
            // skip first line
            line = br.readLine();
            while ((line = br.readLine()) != null && ID < IDCap)   //returns a Boolean value
            {
                String[] employee = line.split(",");    // use comma as separator
                ID = Integer.parseInt(employee[0].substring(1));

                System.out.println("Movie [Movie ID=" + ID + ", Title=" + employee[2] + "," +
                        " Release Date=" + employee[6] + ", Duration=" + employee[8] + ", MPAA=" + employee[7] + "]");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}

