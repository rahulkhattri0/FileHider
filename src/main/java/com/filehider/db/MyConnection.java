package com.filehider.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    public static Connection connection;
    /*
     *  dont want to create objects everytime we need to get this connection 
     * so keeping everything static
     */

    public static Connection getConenction(){
        try {
            /*
             * runs the staticblock and loads the driver 
             * also this is now optional with the newer versions of jdbc
             */
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/FileHider", "root", "root");
            System.out.println("DB Connected");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e){
            System.out.println("some error occurred while establishing connection to database");
            e.printStackTrace();
        }
        return connection;
    }
    public static void closeConnection(){
        try {
            connection.close();
            System.out.println("Connection closeed");
        } catch (SQLException e) {
            System.out.println("some error while closing connection");
            e.printStackTrace();
        }
    }   
    public static void main(String[] args) {
        getConenction();
    }
}
