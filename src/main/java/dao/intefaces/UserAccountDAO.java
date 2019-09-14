package dao.intefaces;


import model.UserAccount;

public interface UserAccountDAO extends DAO<UserAccount> {

    boolean add(UserAccount userAccount);

    boolean updateBalanceById(double amount, int userId);

    boolean updateCreditStatusById(int id, boolean decision);

    boolean updateTerm(int userId);

    UserAccount getByAccountNumber(long recipientAccountNumber);

    boolean updateByAccount(double amount, long recipientAccountNumber);

    boolean updateDepositStatusById(int userId, boolean decision);
}
