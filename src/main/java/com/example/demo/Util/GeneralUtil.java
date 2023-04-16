package com.example.demo.Util;

import java.security.SecureRandom;

public class GeneralUtil {
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final int CODE_LENGTH = 6;

    public static String generateRandomCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(ALPHA_NUMERIC_STRING.length());
            char randomChar = ALPHA_NUMERIC_STRING.charAt(index);
            sb.append(randomChar);
        }
        return sb.toString();
    }
}
