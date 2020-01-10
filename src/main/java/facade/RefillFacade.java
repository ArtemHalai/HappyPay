package facade;

import comparator.OperationDateComparator;
import factories.DaoFactory;
import factories.JDBCConnectionFactory;
import lombok.extern.log4j.Log4j;
import model.*;
import service.*;
import util.TransactionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static enums.DAOEnum.*;

@Log4j
public class RefillFacade {

    private RefillService refillService;
    private TransferService transferService;
    private BillPaymentService billPaymentService;
    private CreditAccountService creditAccountService;
    private UserAccountService userAccountService;
    private DaoFactory factory;
    private JDBCConnectionFactory connectionFactory;

    public RefillFacade() {
        factory = DaoFactory.getInstance();
        connectionFactory = JDBCConnectionFactory.getInstance();
    }

    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    public void setRefillService(RefillService refillService) {
        this.refillService = refillService;
    }

    public void setCreditAccountService(CreditAccountService creditAccountService) {
        this.creditAccountService = creditAccountService;
    }

    public void setTransferService(TransferService transferService) {
        this.transferService = transferService;
    }

    public void setBillPaymentService(BillPaymentService billPaymentService) {
        this.billPaymentService = billPaymentService;
    }

    public boolean refill(RefillOperation refillOperation) {
        try (Connection connection = connectionFactory.getConnection()) {
            TransactionManager.setRepeatableRead(connection);
            refillService.setRefillDAO(factory.getRefillDAO(connection, REFILL_JDBC));
            creditAccountService.setCreditAccountDAO(factory.getCreditAccountDAO(connection, CREDIT_ACCOUNT_JDBC));
            userAccountService.setUserAccountDAO(factory.getUserAccountDAO(connection, USER_ACCOUNT_JDBC));
            UserAccount userAccount = userAccountService.getById(refillOperation.getUserId());
            CreditAccount creditAccount = creditAccountService.getById(refillOperation.getUserId());
            if (userAccount.getUserId() > 0 && userAccount.isCredit() && creditAccount.getLimit() >= refillOperation.getAmount()) {
                boolean limitUpdated =
                        creditAccountService.updateBalanceById(creditAccount.getLimit() - refillOperation.getAmount(), refillOperation.getUserId());
                boolean addArrears =
                        creditAccountService.updateArrears(creditAccount.getArrears() + refillOperation.getAmount(), refillOperation.getUserId());
                boolean updated =
                        userAccountService.updateBalanceById(userAccount.getBalance() + refillOperation.getAmount(), refillOperation.getUserId());
                boolean added =
                        refillService.add(refillOperation);
                if (updated && added && limitUpdated && addArrears) {
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
        } catch (SQLException e) {
            log.error("Could not make refill operation", e);
        }
        return false;
    }

    public RefillPaginationDTO getRefillOperations(RefillPaginationDTO paginationDTO) {
        try (Connection connection = connectionFactory.getConnection()) {
            refillService.setRefillDAO(factory.getRefillDAO(connection, REFILL_JDBC));
            paginationDTO = refillService.getRefillOperations(paginationDTO);
        } catch (SQLException e) {
            log.error("Could not get refill operations", e);
        }
        return paginationDTO;
    }

    public AllOperationsDTO getAllOperations(AllOperationsDTO allOperationsDTO) {

        try (Connection connection = connectionFactory.getConnection()) {
            refillService.setRefillDAO(factory.getRefillDAO(connection, REFILL_JDBC));
            billPaymentService.setBillPaymentDAO(factory.getBillPaymentDAO(connection, BILL_PAYMENT_JDBC));
            transferService.setTransferDAO(factory.getTransferDAO(connection, TRANSFER_JDBC));

            AllOperationsDTO paginationDTO1 = refillService.getAllOperations(allOperationsDTO);
            AllOperationsDTO paginationDTO2 = billPaymentService.getAllOperations(allOperationsDTO);
            AllOperationsDTO paginationDTO3 = transferService.getAllOperations(allOperationsDTO);

            List<OperationsData> list = new ArrayList<>();
            list.addAll(paginationDTO1.getList());
            list.addAll(paginationDTO2.getList());
            list.addAll(paginationDTO3.getList());
            if (list.size() > 10) {
                allOperationsDTO.setList(list.stream()
                        .sorted((o1, o2) -> new OperationDateComparator()
                                .compare(o1.getDate(), o2.getDate()))
                        .collect(Collectors.toList()).subList(0, 10));
            } else {
                allOperationsDTO.setList(list.stream().sorted().collect(Collectors.toList()));
            }
        } catch (SQLException e) {
            log.error("Could not get all operations", e);
        }
        return allOperationsDTO;
    }

    public UserAccount getUserAccount(int userId) {
        UserAccount userAccount = new UserAccount();
        try (Connection connection = connectionFactory.getConnection()) {
            userAccountService.setUserAccountDAO(factory.getUserAccountDAO(connection, USER_ACCOUNT_JDBC));
            userAccount = userAccountService.getById(userId);
        } catch (SQLException e) {
            log.error("Could not get user account");
        }
        return userAccount;
    }
}
