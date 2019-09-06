package dao.intefaces;

import model.DepositAccount;
import model.RefillOperation;

public interface DepositAccountDAO extends DAO<DepositAccount> {

    boolean add(DepositAccount depositAccount);

    boolean updateBalanceById(double amount, int userId);

    boolean updateBalanceByAccount(double amount, long account);

    DepositAccount isAccountNumberExist(long accountNumber);

    DepositAccount getByAccountAndIban(RefillOperation refillOperation);
}
