package util;

import model.UserAccount;

import java.time.ZoneId;

public class UserAccountValidity {

    public static boolean userIdAndValidityAreValid(UserAccount userAccount) {
        return userAccount.getUserId() > 0 && userAccount.getValidity().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() > System.currentTimeMillis();
    }

    public static boolean userIdIsValid(UserAccount userAccount) {
        return userAccount.getUserId() > 0;
    }

    public static boolean userAccountIsValid(UserAccount userAccount) {
        return userAccount.getValidity().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() > System.currentTimeMillis();
    }
}
