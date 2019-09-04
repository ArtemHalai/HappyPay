package dao.intefaces;

import model.DepositAccount;

public interface DepositAccountDAO extends DAO<DepositAccount> {

    boolean add(DepositAccount depositAccount);

    boolean updateAmount(double amount, int userId);
}
