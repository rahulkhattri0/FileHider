package com.filehider.views;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.filehider.dao.DataDao;
import com.filehider.models.Data;

public class UserView {
    /*
     * Directly using DAO here
     */
    public static void home(String email,String name){
        boolean exit = false;
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("Welcome "+ name + " !");
            System.out.println("Press 1 to show all hidden files");
            System.out.println("Press 2 to hide a file");
            System.out.println("Press 3 to logout and go to main menu");
            int ch = sc.nextInt();
            switch(ch){
                case 1: showFiles(email);
                break;
                case 2: hideFile(email);
                break;
                case 3: exit = true;
                break;
            }
            if(exit) break;
        }
    }

    private static void showFiles(String email) {
        try {
            List<Data> files = DataDao.showAllFiles(email);
            if(files.size()==0) System.out.println("Sorry you have hidden 0 files");
            else{
                System.out.println("ID \t Name");
                for(Data file : files){
                    System.out.println(file.getId() + "\t" + file.getFileName());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void hideFile(String email){
        System.out.println("Enter File path (CORRECTLY!)");
        Scanner sc = new Scanner(System.in);
        String path = sc.nextLine();
        File f = new File(path);
        Data data = new Data(f.getName(), path);
        try {
            DataDao.hideFile(data, email);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("File Hidden Successfully!");
    }
}
