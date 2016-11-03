package PeriodicalTasks;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author Michael
 */
@WebListener
public class ExchangeRateManager implements ServletContextListener {

    private final ScheduledExecutorService scheduler;

    public ExchangeRateManager() {
        scheduler = Executors.newScheduledThreadPool(1);
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        scheduler.scheduleAtFixedRate(new ExchangeRates(), 0, 2, TimeUnit.SECONDS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }
   
}
