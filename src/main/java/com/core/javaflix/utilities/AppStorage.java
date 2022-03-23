package com.core.javaflix.utilities;

import java.util.ArrayList;

public class AppStorage {
    //store current user id
    public static int userID;
    //store userId of person user is visiting
    public static int otherID;
    //store window
    public static ArrayList<AbstractWindow> pageStorage = new ArrayList<AbstractWindow>();
    public static String movieID;
    public static Object method;
    public static String search;
    public static int collectionID;
    public static String collectionName;
    public static boolean inCollection = false;
    public AppStorage() {}
}
