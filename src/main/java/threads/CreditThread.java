package threads;

import facade.CreditAccountFacade;
import factories.ServiceFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * A class that works with ScheduledExecutorService.
 *
 * @see ScheduledExecutorService
 */
public class CreditThread {

    private static volatile CreditThread creditThread = null;
    private ScheduledExecutorService executorService;

    /**
     * Private constructor to prevent
     * the instantiation of this class directly.
     */
    private CreditThread() {
        startTask();
    }

    /**
     * Gets the instance of creditThread.
     *
     * @return the instance of {@link #creditThread}.
     */
    public static CreditThread getInstance() {
        if (creditThread == null) {
            synchronized (CreditThread.class) {
                if (creditThread == null) {
                    creditThread = new CreditThread();
                }
            }
        }
        return creditThread;
    }

    /**
     * Method to start executing task which calculates interest charges for credit accounts.
     */
    private void startTask() {
        CreditAccountFacade creditAccountFacade = new CreditAccountFacade();
        creditAccountFacade.setCreditAccountService(ServiceFactory.getInstance().getCreditAccountService());

        executorService = Executors.newSingleThreadScheduledExecutor();

        Runnable runnable = () -> creditAccountFacade.getAll().stream().filter(x -> creditAccountFacade.checkArrears(x.getUserId()))
                .collect(Collectors.toList()).forEach(creditAccountFacade::updateInterestCharges);

        executorService.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.DAYS);
    }

    /**
     * Method to shut down the task.
     */
    public void shutDown() {
        executorService.shutdown();
    }
}
