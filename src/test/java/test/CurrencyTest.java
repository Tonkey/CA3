package test;

import PeriodicalTasks.ExchangeRates;
import entity.CurrencyDescription;
import entity.CurrencyRates;
import facades.CurrencyFacade;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import static java.util.concurrent.TimeUnit.SECONDS;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Persistence;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 *
 * @author Michael
 */
public class CurrencyTest {

    public CurrencyTest() {
    }

    private static ExchangeRates exchangeRates;
    private static CurrencyFacade currencyFacade;
    private static ScheduledExecutorService scheduler;
    private static int count = 0;
    private static Date currentDate;
    private static List<CurrencyRates> serverCalledList;
    private static List<CurrencyRates> databaseCalledList;

    @BeforeClass
    public static void setUpClass() {
        currencyFacade = new CurrencyFacade(Persistence.createEntityManagerFactory("pu_development"));
        exchangeRates = new ExchangeRates(currencyFacade);
    }

    @AfterClass
    public static void tearDownClass() {
        scheduler.shutdownNow();
    }

    @Before
    public void setUp() {
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.currentDate = new Date();
        this.serverCalledList = new ArrayList();
        this.databaseCalledList = new ArrayList();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testServerCall() {
        System.out.println("Starting Currency Test no. 1 - 'Server to servercall' - checking costum connection to server - input stream returns bytes ... ");
        try {
            URL url = new URL("http://www.nationalbanken.dk/_vti_bin/DN/DataService.svc/CurrencyRatesXML?lang=en");
            URLConnection connection = url.openConnection();

            boolean exists = false;
            if (connection.getInputStream().available() != 0) {
                exists = true;
            }
            assertTrue(exists);
        } catch (Exception e) {

        }
        System.out.println("Test no. 1 - 'Server to servercall' - finished. ");
    }

    @Test
    public void testServercallForExchangerates() {

        System.out.println("Starting Currency Test no. 2 - 'Compare data' ...");
        
        System.out.println("Starting Currency Test no. 2.1 - Checking programs servercall to get XML with daily Valuta exchange rates");
        assertTrue(exchangeRates.updateExchangeRates());

        databaseCalledList = currencyFacade.getDailyCurrencyRates(currentDate);
        setUpServerCalledList();
        
        System.out.println("Test no. 2.2 - 'Comparing lengths' - of the lists from server and database call ...");
        assertEquals(serverCalledList.size(), databaseCalledList.size());

        System.out.println("Test no. 2.3 - 'Comparing different codes' from the two lists ...");
        for (int i = 0; i < serverCalledList.size(); i++) {
            assertEquals(serverCalledList.get(i).getCode(), databaseCalledList.get(i).getCode());
        }

        System.out.println("Test no. 2 - 'Compare data' - finished.");
    }

    @Test
    public void testScheduler() {
        System.out.println("Starting Currency Test no. 3 - 'Scheduler' ...");
        int expectedValue = 5;
        int delta = 1;
        scheduleTester();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException ex) {
            Logger.getLogger(CurrencyTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        assertEquals(expectedValue, count, delta);

        System.out.println("Test no. 3 - 'Scheduler' - finished.");
    }

    public static void setUpServerCalledList() {
        try {
            URL url = new URL("http://www.nationalbanken.dk/_vti_bin/DN/DataService.svc/CurrencyRatesXML?lang=en");
            URLConnection connection = url.openConnection();

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document document = docBuilder.parse(connection.getInputStream());

            NodeList currencyNodeList = document.getElementsByTagName("currency");

            for (int i = 0, size = currencyNodeList.getLength(); i < size; i++) {

                serverCalledList.add(new CurrencyRates(currentDate,
                        new CurrencyDescription(currencyNodeList.item(i).getAttributes().getNamedItem("code").getNodeValue(),
                                currencyNodeList.item(i).getAttributes().getNamedItem("desc").getNodeValue()),
                        checkRate(currencyNodeList.item(i).getAttributes().getNamedItem("rate").getNodeValue()), currentDate));
            }
        } catch (Exception e) {
        }
    }

    public static void scheduleTester() {

        final Runnable scheduledTest = new Runnable() {
            public void run() {
                count++;
            }
        };
        final ScheduledFuture<?> testHandler
                = scheduler.scheduleAtFixedRate(scheduledTest, 1, 1, SECONDS);
    }

    private static double checkRate(String rateToCheck) {

        double rate = 0;
        try {
            rate = Double.parseDouble(rateToCheck);
        } catch (NumberFormatException e) {
            rate = 0;
        }
        return rate;
    }
}
