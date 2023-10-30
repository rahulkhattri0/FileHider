package com.filehider.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.filehider.db.MyConnection;
import com.filehider.models.User;

public class UserDAO {
    public static boolean isUserExists(String email) throws SQLException {
        Connection con = MyConnection.getConenction();
        /*
         * parameterized queries save us from sql injection attacks
         */
        String query = "select * from users where email=?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, email);
        ResultSet rs = pstmt.executeQuery();
        boolean flag = rs.next() ? true : false;
        MyConnection.closeConnection();
        return flag;
    }
    public static int saveUser(User user) throws SQLException{
        Connection con = MyConnection.getConenction();
        String query = "insert into users(id,email,name,password) values(default,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, user.getEmail());
        pstmt.setString(2, user.getName());
        pstmt.setString(3, user.getPassword());
        int rowsAffected = pstmt.executeUpdate();
        MyConnection.closeConnection();
        return rowsAffected;//returns number of rows affected - in our case will be 1
    }

    public static boolean isPasswordMatch(String email,String password) throws SQLException{
        Connection con = MyConnection.getConenction();
        String query = "select * from users where email=? and password=?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, email);
        pstmt.setString(2, password);
        ResultSet rs = pstmt.executeQuery();
        boolean flag = rs.next() ? true : false;
        MyConnection.closeConnection();
        return flag;
    }

    public static String userName(String email) throws SQLException{
        Connection con = MyConnection.getConenction();
        String query = "select name from users where email=?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, email);
        ResultSet rs = pstmt.executeQuery();
        String name = "";
        rs.next();
        name = rs.getString(1);
        MyConnection.closeConnection();
        return name;
    }
}
