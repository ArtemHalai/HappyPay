package dao.intefaces;

import model.CreditAccount;

public interface CreditAccountDAO extends DAO<CreditAccount> {

    boolean add(CreditAccount creditAccount);
}
