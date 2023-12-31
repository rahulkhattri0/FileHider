package com.filehider.views;
import java.util.Scanner;

import com.filehider.service.GenerateOTP;
import com.filehider.service.OTPservice;
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

            case 2: String userName = UserService.getUserName(email);
            UserView.home(email, userName);
            break;

            default : System.out.println("something went wrong");
            break;
        }
    }
    private void signUp(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Name");
        String name = sc.nextLine();
        System.out.println("Enter Email");
        String email = sc.nextLine();
        System.out.println("Enter password");
        String password = sc.nextLine();
        String otp = GenerateOTP.getOTP();
        int EmailStatus = OTPservice.sendOTP(otp, email, name);
        if(EmailStatus == 0){
            System.out.println("User already exists! Please sign in");
        }
        else if(EmailStatus == 1){
            System.out.println("Could not Send email.Please try again later");
        }
        else{
            System.out.println("An email verification OTP has been sent to your email,please enter that OTP");
            String otpInput = sc.nextLine();
            if(otpInput.equals(otp)){
                UserService.saveUser(name, email, password);
            }
            else{
                System.out.println("Wrong otp!");
            }
        }
    }
}
