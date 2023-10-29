package com.filehider.service;

import java.sql.SQLException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.filehider.dao.UserDAO;

public class OTPservice {
    public static boolean sendOTP(String otp,String email,String name){
        try {
            if(UserDAO.isUserExists(email)) return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String message = "Hello, " + name + " Your Email verification otp is " + otp;
        String to = email;
        String subject = "Email verification otp:FileHider";
        String from  = "lunaticfridge27@gmail.com";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        //mail session - Step 1
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("lunaticfridge27@gmail.com", "ojzotyxvnqyucivy");
            }
        });
        //Step 2 - compose message
        MimeMessage  mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setFrom(from);
            //recipient - Internet address represents an email address in RFC822 standard
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            mimeMessage.setSubject(subject);
            
            mimeMessage.setText(message);
            //step - 3 send email 
            Transport.send(mimeMessage); //Transport is an abstract class used to send emails
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return true;
    }
}
