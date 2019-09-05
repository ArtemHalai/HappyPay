package dao.intefaces;


import model.UserAccount;

import java.util.List;

public interface UserAccountDAO extends DAO<UserAccount> {

    boolean add(UserAccount userAccount);

    boolean updateDepositStatusById(int id);

    boolean updateCreditStatusById(int id);
}
