package threads;

import facade.CreditAccountFacade;
import factories.ServiceFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CreditThread {

    private static volatile CreditThread creditThread = null;
    private ScheduledExecutorService executorService;

    private CreditThread() {
        startTask();
    }

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

    private void startTask() {
        CreditAccountFacade creditAccountFacade = new CreditAccountFacade();
        creditAccountFacade.setCreditAccountService(ServiceFactory.getInstance().getCreditAccountService());

        executorService = Executors.newSingleThreadScheduledExecutor();

        Runnable runnable = () -> creditAccountFacade.getAll().stream().filter(x -> creditAccountFacade.checkArrears(x.getUserId()))
                .collect(Collectors.toList()).forEach(creditAccountFacade::updateInterestCharges);

        executorService.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.DAYS);
    }

    public void shutDown() {
        executorService.shutdown();
    }
}
