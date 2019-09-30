package listener;

import threads.CreditThread;
import threads.DepositThread;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Define bank context listener class.
 * This class is implementation of ServletContextListener.
 *
 * @see ServletContextListener
 */
public class BankContextListener implements ServletContextListener {

    private DepositThread depositThread;
    private CreditThread creditThread;

    /**
     * Method to initialize {@link #depositThread}, {@link #creditThread}.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        depositThread = DepositThread.getInstance();
        creditThread = CreditThread.getInstance();
    }

    /**
     * Method to shutdown {@link #depositThread}, {@link #creditThread}.
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        depositThread.shutDown();
        creditThread.shutDown();
    }
}
