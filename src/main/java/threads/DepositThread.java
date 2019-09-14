package threads;

import facade.DepositAccountFacade;
import factories.ServiceFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


public class DepositThread {

    private static volatile DepositThread depositThread = null;
    private ScheduledExecutorService executorService;

    private DepositThread() {
        startTask();
    }

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

    private void startTask() {
        DepositAccountFacade depositAccountFacade = new DepositAccountFacade();
        depositAccountFacade.setUserAccountService(ServiceFactory.getInstance().getUserAccountService());
        depositAccountFacade.setDepositAccountService(ServiceFactory.getInstance().getDepositAccountService());

        executorService = Executors.newSingleThreadScheduledExecutor();

        Runnable runnable = () -> depositAccountFacade.getAll().stream().filter(x -> x.getTerm().getTime() < System.currentTimeMillis())
                .collect(Collectors.toList()).forEach(depositAccountFacade::transferMoneyToAccountBalance);

        executorService.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.DAYS);
    }

    public void shutDown(){
        executorService.shutdown();
    }
}
