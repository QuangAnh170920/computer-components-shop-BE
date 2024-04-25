package com.computercomponent.api.until;

public class OTPUtil {
  public static String generateOtpCode(){
    int otp = (int) Math.floor(((Math.random() * 899999) + 100000));
    return Integer.toString(otp);
  }
}
