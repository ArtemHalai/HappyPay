package dao.intefaces;

import model.CreditAccount;

import java.util.List;

public interface CreditAccountDAO extends DAO<CreditAccount> {

    boolean add(CreditAccount creditAccount);

    boolean updateBalanceById(double amount, int userId);

    boolean payArrears(double amount, int userId);

    List<CreditAccount> getAll();

    boolean updateInterestCharges(double amount, int userId);
}
