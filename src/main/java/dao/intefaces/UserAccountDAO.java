package dao.intefaces;


import model.UserAccount;

public interface UserAccountDAO extends DAO<UserAccount> {

    boolean updateDepositStatusById(int id);

    boolean updateCreditStatusById(int id);

    boolean updateBalanceById(int id, double amount);

}
