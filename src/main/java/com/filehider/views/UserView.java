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
            System.out.println("Press 3 to unhide a file.");
            System.out.println("Press 4 to logout and go to main menu");
            int ch = sc.nextInt();
            switch(ch){
                case 1: showFiles(email);
                break;
                case 2: hideFile(email);
                break;
                case 3: unhideFile(email);
                break;
                case 4: exit = true;
                break;
                default: System.out.println("Invaid input");
                break; 
            }
            if(exit) break;
        }
    }

    private static void unhideFile(String email) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter ID of the file to be deleted.");
        int id = sc.nextInt();
        //check if id is valid
        List<Data> files;
        try {
            files = DataDao.getFiles(email);
            boolean isIdValid = false;
            for(Data file : files){
                if(id==file.getId()){
                    isIdValid = true;
                    break;
                }
            }
            if(isIdValid){
                DataDao.unhide(id);
                System.out.println("successfully unhidden file");
            }
            else{
                System.out.println("Invalid ID entered!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }

    private static void showFiles(String email) {
        try {
            List<Data> files = DataDao.getFiles(email);
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
            System.out.println("File Hidden Successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
}
