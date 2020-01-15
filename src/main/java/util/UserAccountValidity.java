package util;

import model.UserAccount;

import java.time.LocalDate;

public class UserAccountValidity {

    public static boolean userIdAndValidityAreValid(UserAccount userAccount) {
        return userIdIsValid(userAccount) && userAccountIsValid(userAccount);
    }

    public static boolean userIdIsValid(UserAccount userAccount) {
        return userAccount.getUserId() > 0;
    }

    public static boolean userAccountIsValid(UserAccount userAccount) {
        return userAccount.getValidity().toEpochDay() > LocalDate.now().toEpochDay();
    }
}
