package PeriodicalTasks;

import com.google.common.base.CharMatcher;
import entity.CurrencyDescription;
import entity.CurrencyRates;

import facades.CurrencyFacade;

import java.io.IOException;

import org.xml.sax.helpers.*;
import org.xml.sax.SAXException;

import java.net.URL;
import java.net.URLConnection;
import java.net.MalformedURLException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.persistence.Persistence;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 *
 * @author Michael
 */
public class ExchangeRates extends DefaultHandler implements Runnable {

    public ExchangeRates() {

    }

//    public static void main(String[] args) {
//        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//        scheduler.scheduleAtFixedRate(new ExchangeRates(), 0, 2, TimeUnit.SECONDS);
//    }

    public boolean updateExchangeRates() {

        CurrencyFacade currencyFacade = new CurrencyFacade(Persistence.createEntityManagerFactory("pu_development"));

        try {

            URL url = new URL("http://www.nationalbanken.dk/_vti_bin/DN/DataService.svc/CurrencyRatesXML?lang=en");
            URLConnection connection = url.openConnection();

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document document = docBuilder.parse(connection.getInputStream());

            TransformerFactory transformerfactory = TransformerFactory.newInstance();
            Transformer xform = transformerfactory.newTransformer();

//          prints out the full xml
//          xform.transform(new DOMSource(document), new StreamResult(System.out));
            NodeList currencyNodeList = document.getElementsByTagName("currency");

            List<CurrencyRates> dailyCurrencies = new ArrayList<CurrencyRates>();
            
            Date now = new Date();

            for (int i = 0, size = currencyNodeList.getLength(); i < size; i++) {

                dailyCurrencies.add(new CurrencyRates(now,
                        new CurrencyDescription(currencyNodeList.item(i).getAttributes().getNamedItem("code").getNodeValue(),
                                currencyNodeList.item(i).getAttributes().getNamedItem("desc").getNodeValue()),
                        checkRate(currencyNodeList.item(i).getAttributes().getNamedItem("rate").getNodeValue()),now));
               
            }
            
            currencyFacade.updateDailyCurrencies(dailyCurrencies);


        } catch (MalformedURLException MalURLex) {

            return false;

        } catch (IOException IOex) {

            return false;

        } catch (ParserConfigurationException PCex) {

            return false;

        } catch (SAXException SAXex) {

            return false;

        } catch (TransformerConfigurationException TCex) {

            return false;

        } catch (TransformerException TransFex) {

            return false;

        }
        return true;
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

    @Override
    public void run() {
        updateExchangeRates();
    }
}
