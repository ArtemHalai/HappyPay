package listener;

import threads.CreditThread;
import threads.DepositThread;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class BankContextListener implements ServletContextListener {

    private DepositThread depositThread;
    private CreditThread creditThread;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        depositThread = DepositThread.getInstance();
        creditThread = CreditThread.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        depositThread.shutDown();
        creditThread.shutDown();
    }
}
