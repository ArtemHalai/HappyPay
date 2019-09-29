package dao.intefaces;

import model.DepositAccount;

import java.util.List;

public interface DepositAccountDAO extends DAO<DepositAccount> {

    boolean add(DepositAccount depositAccount);

    boolean updateBalanceById(double amount, int userId);

    boolean deleteDepositAccount(int userId);

    int count(int userId);

    List<DepositAccount> getAll();
}
