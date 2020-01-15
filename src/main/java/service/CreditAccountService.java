package service;

import dao.intefaces.CreditAccountDAO;
import factories.DaoFactory;
import model.CreditAccount;

import java.sql.Connection;
import java.util.List;

import static enums.DAOEnum.CREDIT_ACCOUNT_JDBC;

public class CreditAccountService {

    private CreditAccountDAO creditAccountDAO;
    private DaoFactory factory;

    public CreditAccountService() {
        factory = DaoFactory.getInstance();
    }

    public void setCreditAccountDAO(CreditAccountDAO creditAccountDAO) {
        this.creditAccountDAO = creditAccountDAO;
    }

    public void setDefaultCreditAccountDAO(Connection connection) {
        this.creditAccountDAO = factory.getCreditAccountDAO(connection, CREDIT_ACCOUNT_JDBC);
    }

    public boolean add(CreditAccount creditAccount) {
        return creditAccountDAO.add(creditAccount);
    }

    public boolean updateArrears(double amount, int userId) {
        return creditAccountDAO.payArrears(amount, userId);
    }

    public boolean updateBalanceById(double amount, int userId){
        return creditAccountDAO.updateBalanceById(amount, userId);
    }

    public CreditAccount getById(int id) {
        return creditAccountDAO.getById(id);
    }

    public List<CreditAccount> getAll() {
        return creditAccountDAO.getAll();
    }

    public boolean updateInterestCharges(double amount, int userId) {
        return creditAccountDAO.updateInterestCharges(amount, userId);
    }
}
