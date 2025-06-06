package com.juaracoding.smartpro_web.security;

import org.mindrot.jbcrypt.BCrypt;

import java.util.function.Function;

public class BCryptCustom {
    private final int logRounds;

    public BCryptCustom(int logRounds) {
        this.logRounds = logRounds;
    }

    public String hash(String plainText) {
        return BCrypt.hashpw(plainText, BCrypt.gensalt(logRounds));
    }

    public Boolean verifyHash(String plainText, String hashedText) {
        return BCrypt.checkpw(plainText, hashedText);
    }

    public Boolean verifyAndUpdateHash(String plainText, String hashedText, Function<String, Boolean> updateFunc) {
        if (BCrypt.checkpw(plainText, hashedText)) {
            int intRounds = getRounds(hashedText);
            if (intRounds == 0) {
                return false;
            }

            if (intRounds != logRounds) {
                String newHashedText = hash(plainText);
                return updateFunc.apply(newHashedText);
            }
            return true;
        }

        return false;
    }

    private int getRounds(String salt) {
        char minor = (char)0;
        int off = 0;

        if (salt.charAt(0) != '$' || salt.charAt(1) != '2')
        {
            return 0;
        }
        if (salt.charAt(2) == '$')
        {
            off = 3;
        }
        else
        {
            minor = salt.charAt(2);
            if (minor != 'a' || salt.charAt(3) != '$')
            {
                return 0;
            }
            off = 4;
        }
        if (salt.charAt(off + 2) > '$')
        {
            return 0;
        }
        return Integer.parseInt(salt.substring(off, off + 2));
    }
}
