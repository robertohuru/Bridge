/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.drda.NetworkServerControl;

/**
 *
 * @author DELL
 */
public final class DatabaseConnection implements DatabaseConnectionVariables {

    private Connection con;
    private String dbHost;
    private String dbUsername;
    private String dbPassword;
    private String dbError = "";
    private int dbErrorCode = 0;
    private static NetworkServerControl server = null;
    private static DatabaseConnection dbConnection = null;

    public DatabaseConnection() {

        this.dbHost = host;
        this.dbUsername = user;
        this.dbPassword = password;
        //setDBSystemDir();      
        setConnection();
    }

    public static synchronized DatabaseConnection getInstance() {
        if (dbConnection == null) {
            dbConnection = new DatabaseConnection();
        }
        return dbConnection;
    }

    public static NetworkServerControl getServer() {
        try {
            server = new NetworkServerControl(InetAddress.getByName("localhost"), 1527);
        } catch (Exception ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return server;
    }

    public static void startDerbyServer() {
        server = getServer();
        try {
            server.start(null);
            System.err.println("Started");
        } catch (Exception e) {
            //MyLogger.log(getClass(), e.getMessage());
        }
    }

    public static void closeDerbyServer() {
        server = getServer();
        try {
            server.shutdown();
            System.out.println("Closed");
        } catch (Exception ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setDBSystemDir() {
        // Decide on the db system directory: <userhome>/.addressbook/
        String userHomeDir = System.getProperty("user.home", ".");
        String systemDir = userHomeDir + ".netbeans-derby/passwordsafe_db";

        // Set the db system directory.
        System.setProperty("derby.system.home", systemDir);
        //System.out.println("dir" + systemDir);
    }

    public final boolean setConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            con = DriverManager.getConnection(dbHost, dbUsername, dbPassword);
            System.out.println("connection ok");            
        } catch (SQLException SQL_Err) {
            //MyLogger.log(getClass(), SQL_Err.getMessage());
            System.out.println(SQL_Err);
            return false;
        } catch (ClassNotFoundException cnfe) {
            System.out.println(cnfe);
            //MyLogger.log(getClass(), cnfe.getMessage());
            return false;
        }

        return true;
    }

    public Connection getConnection() {
        return con;
    }

    public void closeConnection(){
        try {
            this.con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public String getError() {
        return dbError;
    }

    public static void main(String[] args) {
        startDerbyServer();
        getInstance().getConnection();
        closeDerbyServer();

    }

}
