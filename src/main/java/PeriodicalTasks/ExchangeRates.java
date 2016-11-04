package PeriodicalTasks;

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
import javax.persistence.Persistence;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 *
 * @author Michael
 */
public class ExchangeRates extends DefaultHandler implements Runnable {

    private CurrencyFacade currencyFacade;
    private List<CurrencyRates> dailyCurrencies;
    public ExchangeRates(CurrencyFacade newCurrencyFacade) {
        this.currencyFacade = newCurrencyFacade;
        this.dailyCurrencies = new ArrayList();
    }

    public boolean updateExchangeRates() {
      
        try {

            URL url = new URL("http://www.nationalbanken.dk/_vti_bin/DN/DataService.svc/CurrencyRatesXML?lang=en");
            URLConnection connection = url.openConnection();

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document document = docBuilder.parse(connection.getInputStream());

            NodeList currencyNodeList = document.getElementsByTagName("currency");        
            
            Date now = new Date();

            for (int i = 0, size = currencyNodeList.getLength(); i < size; i++) {

                dailyCurrencies.add(new CurrencyRates(now,
                        new CurrencyDescription(currencyNodeList.item(i).getAttributes().getNamedItem("code").getNodeValue(),
                                currencyNodeList.item(i).getAttributes().getNamedItem("desc").getNodeValue()),
                        checkRate(currencyNodeList.item(i).getAttributes().getNamedItem("rate").getNodeValue()),now));
               
            }

        } catch (MalformedURLException MalURLex) {

            return false;

        } catch (IOException IOex) {

            return false;

        } catch (ParserConfigurationException PCex) {

            return false;

        } catch (SAXException SAXex) {

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
        currencyFacade.updateDailyCurrencies(this.dailyCurrencies);
    }
}
