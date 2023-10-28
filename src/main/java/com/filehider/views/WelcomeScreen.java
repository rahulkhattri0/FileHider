package com.filehider.views;

import java.util.Scanner;

public class WelcomeScreen {
    public void welcome(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to FileHider!");
        while(true){
            System.out.println("Enter 1 for Login");
            System.out.println("Enter 2 for signup");
            System.out.println("Press 3 to exit");
            int ch = sc.nextInt();
            switch(ch){
                case 1 : System.out.println("1");
                break;

                case 2 : System.out.println("2");
                break;

                case 3 : sc.close();
                System.exit(0);

                default : System.out.println("Invalid input");
            }
        }
    }
}
