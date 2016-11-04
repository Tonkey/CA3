/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
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

    @BeforeClass
    public static void setUpClass() {
        exchangeRates = new ExchangeRates();
        currencyFacade = new CurrencyFacade();
        scheduler = Executors.newScheduledThreadPool(1);
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
        scheduler.shutdownNow();
    }

    @Test
    public void testServercallForExchangerates() {

        List<CurrencyRates> serverCalledList = new ArrayList<CurrencyRates>();
        List<CurrencyRates> databaseCalledList = new ArrayList<CurrencyRates>();
        Date now = new Date();

        System.out.println("Starting Currency Test no. 1.1- servercall checking the input stream returns bytes ... ");
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

        System.out.println("Starting Currency Test no. 1.2- servercall to get XML with daily Valuta exchange rates");
        assertTrue(exchangeRates.updateExchangeRates());

        System.out.println("Test no. 1 - Servercall - finished ... ");

        try {

            URL url = new URL("http://www.nationalbanken.dk/_vti_bin/DN/DataService.svc/CurrencyRatesXML?lang=en");
            URLConnection connection = url.openConnection();

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document document = docBuilder.parse(connection.getInputStream());

            TransformerFactory transformerfactory = TransformerFactory.newInstance();
            Transformer xform = transformerfactory.newTransformer();

            NodeList currencyNodeList = document.getElementsByTagName("currency");

            for (int i = 0, size = currencyNodeList.getLength(); i < size; i++) {

                serverCalledList.add(new CurrencyRates(now,
                        new CurrencyDescription(currencyNodeList.item(i).getAttributes().getNamedItem("code").getNodeValue(),
                                currencyNodeList.item(i).getAttributes().getNamedItem("desc").getNodeValue()),
                        checkRate(currencyNodeList.item(i).getAttributes().getNamedItem("rate").getNodeValue()), now));

            }
        } catch (Exception e) {
        }

        databaseCalledList = currencyFacade.getDailyCurrencyRates(now);

        System.out.println("Starting Currency Test no. 2 - matching object from servercall with object from Database call");
        System.out.println("Test no. 2.1 - Comparing length of the lists from server and database call ...");

        assertEquals(serverCalledList.size(), databaseCalledList.size());
        System.out.println("Test no. 2.2 - Comparing different codes from the two lists ...");
        for (int i = 0; i < serverCalledList.size(); i++) {
            assertEquals(serverCalledList.get(i).getCode(), databaseCalledList.get(i).getCode());
        }
        System.out.println("Test no. 2.3 - Comparing different rates from the two lists ...");
        for (int i = 0; i < serverCalledList.size(); i++) {
            double alpha = serverCalledList.get(i).getRate();
            double betha = databaseCalledList.get(i).getRate();
            double delta = 0.1;
            assertEquals(alpha, betha, delta);
        }

        System.out.println("Test no. 2 - Database call - finished ...");

        System.out.println("Starting Currency Test no. 3 - Testing Scheduler");
        int expectedValue = 5;
        int delta = 1;
        scheduleTester();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException ex) {
            Logger.getLogger(CurrencyTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        assertEquals(expectedValue, count, delta);

        System.out.println("Test no. 3 - Scheduler Test - finished ...");

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

    private double checkRate(String rateToCheck) {

        double rate = 0;
        try {
            rate = Double.parseDouble(rateToCheck);
        } catch (NumberFormatException e) {
            rate = 0;
        }
        return rate;
    }
}
