package edu.RL.util;

import java.util.Random;

public class OTPUtil {
    public static String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // 6 digits
        return String.valueOf(otp);
    }
}
