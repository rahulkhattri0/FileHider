package com.filehider.service;

import java.sql.SQLException;

import com.filehider.dao.UserDAO;
import com.filehider.models.User;

public class UserService {
    public static boolean saveUser(String name,String email,String password){
        try {
            if(UserDAO.isUserExists(email)) return false;
            else {
                User user = new User(name, email, password);
                UserDAO.saveUser(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    /*
     *  0 - User does not exist
     *  1 - Password Does not match
     *  2 - All good,user logged in
     */
    public static int loginUser(String email,String password){
        try {
            if(!UserDAO.isUserExists(email)) return 0;
            else if(!UserDAO.isPasswordMatch(email, password)) return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 2;
    }
}
