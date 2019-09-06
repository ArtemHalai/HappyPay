package dao.intefaces;

import model.CreditAccount;
import model.RefillOperation;

public interface CreditAccountDAO extends DAO<CreditAccount> {

    boolean add(CreditAccount creditAccount);

    boolean updateBalanceById(double amount, int userId);

    boolean updateBalanceByAccount(double amount, long account);

    CreditAccount isAccountNumberExist(long accountNumber);

    CreditAccount getByAccountAndIban(RefillOperation refillOperation);
}
