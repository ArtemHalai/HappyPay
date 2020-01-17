package util;

import model.UserAccount;

import java.time.LocalDate;

public class UserAccountValidity {

    private UserAccountValidity(){

    }

    public static boolean checkUserIdAndValidityAreValid(UserAccount userAccount) {
        return checkUserIdIsValid(userAccount) && checkUserAccountIsValid(userAccount);
    }

    public static boolean checkUserIdIsValid(UserAccount userAccount) {
        return userAccount.getUserId() > 0;
    }

    public static boolean checkUserAccountIsValid(UserAccount userAccount) {
        return userAccount.getValidity().toEpochDay() > LocalDate.now().toEpochDay();
    }
}
