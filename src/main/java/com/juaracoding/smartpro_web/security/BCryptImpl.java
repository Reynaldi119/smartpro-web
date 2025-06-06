package com.juaracoding.smartpro_web.security;

public class BCryptImpl {
    private static final BCryptCustom bcrypt = new BCryptCustom(11);

    public static String hash(String plain) {
        return bcrypt.hash(plain);
    }

    public static Boolean verifyHash(String plain, String hashed) {
        return bcrypt.verifyHash(plain, hashed);
    }

    public static void main(String[] args) {
        System.out.println(hash("riyan.gunawanP@ssw0rd1"));
    }
}
