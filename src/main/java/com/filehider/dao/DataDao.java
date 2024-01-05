package com.filehider.dao;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.filehider.db.MyConnection;
import com.filehider.models.Data;

public class DataDao {
    public static List<Data> getFiles(String email) throws SQLException{
        List<Data> files = new ArrayList<>();
        // can do without inner join if we take userId as an argument in this function,but i wanted to use joins
        String query = "select d.dataId,d.name,d.path from users as u Inner join data as d on u.id = d.userId where u.email = ?";
        Connection con = MyConnection.getConnection();
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, email);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            String path = rs.getString(3);
            files.add(new Data(id, name, path));
        }
        MyConnection.closeConnection();
        return files;
    }
    public static void hideFile(Data data,String email) throws SQLException, IOException{
        //first get id of user - can skip this if we directly pass id - but ok
        Connection con = MyConnection.getConnection();
        String query1 = "select id from users where email=?";
        PreparedStatement pstmt = con.prepareStatement(query1);
        pstmt.setString(1, email);
        ResultSet rs = pstmt.executeQuery();
        int id=0;
        rs.next();
        id = rs.getInt(1);
        //now hide the file
        String query2 = "insert into data(name,path,userId,bin_data) values (?,?,?,?)";
        pstmt = con.prepareStatement(query2);
        pstmt.setString(1, data.getFileName());
        pstmt.setString(2, data.getPath());
        pstmt.setInt(3, id);
        File f = new File(data.getPath());
        FileReader fr = new FileReader(f);
        pstmt.setCharacterStream(4, fr,f.length());
        pstmt.executeUpdate();
        fr.close();
        f.delete();//main part - delete from users directory coz the file has been saved in the database
        MyConnection.closeConnection();
    }

    public static void unhide(int id) throws SQLException, IOException{
        Connection con = MyConnection.getConnection();
        String query1 = "select path,bin_data from data where dataId=?";
        PreparedStatement pstmt = con.prepareStatement(query1);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        String path = rs.getString(1);
        Clob data = rs.getClob(2);
        Reader fr = data.getCharacterStream();
        FileWriter fw = new FileWriter(path);
        boolean isEOF = false;
        while(!isEOF){
            int charCode = fr.read();
            if(charCode==-1) isEOF = true;
            else{
                char ch = (char) charCode;
                fw.write(ch);
            }
        }
        fw.close();
        fr.close();
        String query2 = "delete from data where dataId = ?";
        pstmt = con.prepareStatement(query2);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
        MyConnection.closeConnection();
    }
}
