package dao.intefaces;

import model.DepositAccount;

public interface DepositAccountDAO extends DAO<DepositAccount> {

    boolean add(DepositAccount depositAccount);

    boolean updateBalanceById(double amount, int userId);
}
