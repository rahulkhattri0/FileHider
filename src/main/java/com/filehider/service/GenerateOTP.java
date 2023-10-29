package com.filehider.service;

import java.util.Random;

public class GenerateOTP {
    public static String getOTP(){
        /*
         * we need to generate a random 4-digit number
         */
        int min = 1000;
        int max = 9999;
        Random random = new Random();
        int randInt = random.nextInt(max+1);
        return (randInt < 1000 ? randInt + min : randInt) + "";
    }
}
