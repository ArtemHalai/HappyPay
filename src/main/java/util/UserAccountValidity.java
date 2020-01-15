package util;

import model.UserAccount;

public class UserAccountValidity {

    public static boolean userIdAndValidityAreValid(UserAccount userAccount) {
        return userAccount.getUserId() > 0 && userAccount.getValidity().getTime() > System.currentTimeMillis();
    }

    public static boolean userIdIsValid(UserAccount userAccount) {
        return userAccount.getUserId() > 0;
    }

    public static boolean userAccountIsValid(UserAccount userAccount) {
        return userAccount.getValidity().getTime() > System.currentTimeMillis();
    }
}
