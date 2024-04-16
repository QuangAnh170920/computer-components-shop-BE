package com.computercomponent.until;

public class RandomCodeUtil {
    public static String generateRandomCode(){
        int randomCode = (int) Math.floor(((Math.random() * 899999) + 100000));
        return Integer.toString(randomCode);
    }
}