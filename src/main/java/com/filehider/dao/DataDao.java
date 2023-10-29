package com.filehider.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.filehider.db.MyConnection;
import com.filehider.models.Data;

public class DataDao {
    public static List<Data> showAllFiles(String email) throws SQLException{
        List<Data> files = new ArrayList<>();
        // can do without inner join if we take userId as an argument in this function,but i wanted to use joins
        String query = "select d.dataId,d.name,d.path from users as u Inner join data as d on u.id = d.userId where u.email = ?";
        Connection con = MyConnection.getConenction();
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, email);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            String id = rs.getString(1);
            String name = rs.getString(2);
            String path = rs.getString(3);
            files.add(new Data(id, name, path));
        }
        MyConnection.closeConnection();
        return files;
    }
    public static void hideFile(Data data,String email) throws SQLException, IOException{
        //first get id of user - can skip this if we directly pass id - but ok
        Connection con = MyConnection.getConenction();
        String query1 = "select id from users where email=?";
        PreparedStatement pstmt1 = con.prepareStatement(query1);
        pstmt1.setString(1, email);
        ResultSet rs1 = pstmt1.executeQuery();
        int id=0;
        while(rs1.next()){
            id = rs1.getInt(1);
        }
        //now hide the file
        String query2 = "insert into data(name,path,userId,bin_data) values (?,?,?,?)";
        PreparedStatement pstmt2 = con.prepareStatement(query2);
        pstmt2.setString(1, data.getFileName());
        pstmt2.setString(2, data.getPath());
        pstmt2.setInt(3, id);
        File f = new File(data.getPath());
        FileReader fr = new FileReader(f);
        pstmt2.setCharacterStream(4, fr,f.length());
        pstmt2.executeUpdate();
        fr.close();
        f.delete();//main part - delete from users directory coz the file has been saved in the database
        MyConnection.closeConnection();
    }
}
