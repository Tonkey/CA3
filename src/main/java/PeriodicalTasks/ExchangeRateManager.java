package PeriodicalTasks;

import facades.CurrencyFacade;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author Michael
 */
@WebListener
public class ExchangeRateManager implements ServletContextListener {
private CurrencyFacade currencyFacade;
    private final ScheduledExecutorService scheduler;

    public ExchangeRateManager() {
        scheduler = Executors.newScheduledThreadPool(1);
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        this.currencyFacade =  new CurrencyFacade(Persistence.createEntityManagerFactory("pu_development"));
        scheduler.scheduleAtFixedRate(new ExchangeRates(currencyFacade), 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }
   
}
