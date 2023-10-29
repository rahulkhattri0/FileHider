package com.filehider.views;
import java.util.Scanner;

import com.filehider.service.UserService;

public class WelcomeScreen {
    public void welcome() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to FileHider!");
        while(true){
            System.out.println("Enter 1 for Login");
            System.out.println("Enter 2 for signup");
            System.out.println("Press 3 to exit");
            int ch = sc.nextInt();
            switch(ch){
                case 1 : login();
                break;

                case 2 : signUp();
                break;

                case 3 : System.exit(0);

                default : System.out.println("Invalid input");
            }
        }
    }
    private void login(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter email");
        String email = sc.nextLine();
        System.out.println("Enter password");
        String password = sc.nextLine();
        int status = UserService.loginUser(email, password);
        switch(status){
            case 0: System.out.println("User does not exist! Please Sign Up.");
            break;

            case 1: System.out.println("Passwords do not match");
            break;

            case 2: //TO-DO  
            break;
        }
    }
    private void signUp(){}
}
