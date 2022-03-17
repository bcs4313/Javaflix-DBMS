package com.core.javaflix;

import com.jcraft.jsch.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * Establishes/undoes a connection to the database for an individual user.
 */
public class DataStreamManager {
    // connection to the DBMS
    public static Connection conn = null;

    // user session within the DBMS
    public static Session session = null;

    /**
     * Establish a connection to the database with a username,
     * password, and database name. Stores elements of the
     * connection in conn and session to be handled and modified.
     * @return if the connection was successful or not
     */
    public boolean establishConnection()
    {
        String driverName = "org.postgresql.Driver";
        int lport = 5432;
        String rhost = "starbug.cs.rit.edu";
        int rport = 5432;
        String user = "drs7727"; //change to your username
        String password = "S$989kn13329";
        String databaseName = "p320_05"; //change to your database name

        try {
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            session = jsch.getSession(user, rhost, 22);
            session.setPassword(password);
            session.setConfig(config);
            session.setConfig("PreferredAuthentications","publickey,keyboard-interactive,password");
            session.connect();
            System.out.println("Connected");
            int assigned_port = session.setPortForwardingL(lport, "localhost", rport);
            System.out.println("Port Forwarded");

            // Assigned port could be different from 5432 but rarely happens
            String url = "jdbc:postgresql://localhost:"+ assigned_port + "/" + databaseName;

            System.out.println("database Url: " + url);
            Properties props = new Properties();
            props.put("user", user);
            props.put("password", password);

            Class.forName(driverName);
            conn = DriverManager.getConnection(url, props);
            System.out.println("Database connection established");

        } catch (Exception e) {
            // connection failed!
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
