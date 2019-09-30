package threads;

import facade.DepositAccountFacade;
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
public class DepositThread {

    private static volatile DepositThread depositThread = null;
    private ScheduledExecutorService executorService;

    /**
     * Private constructor to prevent
     * the instantiation of this class directly.
     */
    private DepositThread() {
        startTask();
    }

    /**
     * Gets the instance of depositThread.
     *
     * @return the instance of {@link #depositThread}.
     */
    public static DepositThread getInstance() {
        if (depositThread == null) {
            synchronized (DepositThread.class) {
                if (depositThread == null) {
                    depositThread = new DepositThread();
                }
            }
        }
        return depositThread;
    }

    /**
     * Method to start executing task which check if deposit term is expired then transfer money to main account balance.
     */
    private void startTask() {
        DepositAccountFacade depositAccountFacade = new DepositAccountFacade();
        depositAccountFacade.setUserAccountService(ServiceFactory.getInstance().getUserAccountService());
        depositAccountFacade.setDepositAccountService(ServiceFactory.getInstance().getDepositAccountService());

        executorService = Executors.newSingleThreadScheduledExecutor();

        Runnable runnable = () -> depositAccountFacade.getAll().stream().filter(x -> x.getTerm().getTime() < System.currentTimeMillis())
                .collect(Collectors.toList()).forEach(depositAccountFacade::transferMoneyToAccountBalance);

        executorService.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.DAYS);
    }

    /**
     * Method to shut down the task.
     */
    public void shutDown(){
        executorService.shutdown();
    }
}
