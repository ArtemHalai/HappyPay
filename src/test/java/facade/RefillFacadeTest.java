package facade;

import factories.JDBCConnectionFactory;
import model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.*;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RefillFacadeTest {

    private static final int USER_ID = 1;
    private static final int PAGE_SIZE = 10;
    private static final double AMOUNT = 100.99;

    @Mock
    private CreditAccountService creditAccountService;

    @Mock
    private UserAccountService userAccountService;

    @Mock
    private BillPaymentService billPaymentService;

    @Mock
    private TransferService transferService;

    @Mock
    private RefillService refillService;

    @Mock
    private JDBCConnectionFactory connectionFactory;

    @Mock
    private UserAccount userAccount;

    @Mock
    private RefillOperation refillOperation;

    @Mock
    private CreditAccount creditAccount;

    @Mock
    private RefillPaginationDTO refillPaginationDTO;

    @Mock
    private AllOperationsDTO allOperationsDTO;

    @Mock
    private Connection connection;

    @InjectMocks
    private RefillFacade refillFacade;

    @Before
    public void setUp() {
        when(connectionFactory.getConnection()).thenReturn(connection);
    }

    @Test
    public void refill_ReturnsTrue_WhenRefillOperationWasSuccessful() {
        double balance = 11111.99;
        double creditLimit = 1001.99;
        double arrears = 0;

        when(refillOperation.getUserId()).thenReturn(USER_ID);
        when(userAccountService.getById(USER_ID)).thenReturn(userAccount);
        when(creditAccountService.getById(USER_ID)).thenReturn(creditAccount);
        when(userAccount.getUserId()).thenReturn(USER_ID);
        when(userAccount.isCredit()).thenReturn(true);
        when(userAccount.getBalance()).thenReturn(balance);
        when(refillOperation.getAmount()).thenReturn(AMOUNT);
        when(creditAccount.getLimit()).thenReturn(creditLimit);
        when(creditAccount.getArrears()).thenReturn(arrears);
        when(creditAccountService.updateBalanceById(creditLimit - AMOUNT, USER_ID)).thenReturn(true);
        when(creditAccountService.updateArrears(arrears + AMOUNT, USER_ID)).thenReturn(true);
        when(userAccountService.updateBalanceById(balance + AMOUNT, USER_ID)).thenReturn(true);
        when(refillService.add(refillOperation)).thenReturn(true);

        boolean successfulRefill = refillFacade.refill(refillOperation);

        assertTrue(successfulRefill);
    }

    @Test
    public void refill_ReturnsFalse_WhenRefillOperationWasNotSuccessful() {
        double creditLimit = 1.99;

        when(refillOperation.getUserId()).thenReturn(USER_ID);
        when(userAccountService.getById(USER_ID)).thenReturn(userAccount);
        when(creditAccountService.getById(USER_ID)).thenReturn(creditAccount);
        when(userAccount.getUserId()).thenReturn(USER_ID);
        when(userAccount.isCredit()).thenReturn(true);
        when(refillOperation.getAmount()).thenReturn(AMOUNT);
        when(creditAccount.getLimit()).thenReturn(creditLimit);

        boolean unsuccessfulRefill = refillFacade.refill(refillOperation);

        assertFalse(unsuccessfulRefill);
    }

    @Test
    public void getRefillOperations_ReturnsRefillPaginationDTOContainingListWithRefillOperations_WhenPassRefillPaginationDTOContainingNecessaryDataForThis() {
        when(refillService.getRefillOperations(refillPaginationDTO)).thenReturn(refillPaginationDTO);

        RefillPaginationDTO paginationDTO = refillFacade.getRefillOperations(refillPaginationDTO);

        assertEquals(refillPaginationDTO, paginationDTO);
    }

    @Test
    public void getAllOperations_ReturnsAllOperationsDTOContainingListWithOperationsDataObjectsLessThanTen_WhenPassAllOperationsDTOContainingNecessaryDataForThis() {
        when(refillService.getAllOperations(allOperationsDTO)).thenReturn(allOperationsDTO);
        when(billPaymentService.getAllOperations(allOperationsDTO)).thenReturn(allOperationsDTO);
        when(transferService.getAllOperations(allOperationsDTO)).thenReturn(allOperationsDTO);
        when(allOperationsDTO.getUserId()).thenReturn(USER_ID);
        when(allOperationsDTO.getPageSize()).thenReturn(PAGE_SIZE);

        AllOperationsDTO operationsDTO = refillFacade.getAllOperations(allOperationsDTO);

        assertEquals(allOperationsDTO, operationsDTO);
    }

    @Test
    public void getAllOperations_ReturnsAllOperationsDTOContainingListWithOperationsDataObjectsMoreThanOrEqualTen_WhenPassAllOperationsDTOContainingNecessaryDataForThis() {
        List<OperationsData> operationsData = new ArrayList<>();
        for (int i = 0; i < PAGE_SIZE; i++) {
            OperationsData operation = new OperationsData();
            operation.setDate(new Date(System.currentTimeMillis()));
            operationsData.add(operation);
        }

        when(refillService.getAllOperations(allOperationsDTO)).thenReturn(allOperationsDTO);
        when(billPaymentService.getAllOperations(allOperationsDTO)).thenReturn(allOperationsDTO);
        when(transferService.getAllOperations(allOperationsDTO)).thenReturn(allOperationsDTO);
        when(allOperationsDTO.getUserId()).thenReturn(USER_ID);
        when(allOperationsDTO.getPageSize()).thenReturn(PAGE_SIZE);
        when(allOperationsDTO.getList()).thenReturn(operationsData);

        AllOperationsDTO operationsDTO = refillFacade.getAllOperations(allOperationsDTO);

        assertEquals(allOperationsDTO.getList(), operationsDTO.getList());
    }

    @Test
    public void getUserAccount_ReturnsUserAccount_WhenUserExistsForGivenId() {
        when(userAccountService.getById(USER_ID)).thenReturn(userAccount);

        UserAccount actualUserAccount = refillFacade.getUserAccount(USER_ID);

        assertEquals(userAccount, actualUserAccount);
    }
}