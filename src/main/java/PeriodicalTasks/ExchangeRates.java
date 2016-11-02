package PeriodicalTasks;

import entity.CurrencyDescription;
import entity.CurrencyRates;
import java.io.IOException;
import java.net.MalformedURLException;
import org.xml.sax.helpers.*;

import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Michael
 */
public class ExchangeRates extends DefaultHandler implements Runnable {

    public ExchangeRates() {

    }

    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new ExchangeRates(), 0, 2, TimeUnit.SECONDS);
    }

    public void updateExchangeRates() {

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

            for (int i = 0, size = currencyNodeList.getLength(); i < size; i++) {

                CurrencyRates dailyCurrencyRates = new CurrencyRates(new Date(),
                        new CurrencyDescription(currencyNodeList.item(i).getAttributes().getNamedItem("code").getNodeValue(),
                                currencyNodeList.item(i).getAttributes().getNamedItem("desc").getNodeValue()),
                        checkRate(currencyNodeList.item(i).getAttributes().getNamedItem("rate").getNodeValue()));

//                System.out.println("currencyDescription is: " + dailyCurrencyRates.getCodeID());
//                System.out.println("currencyDescription is: " + dailyCurrencyRates.getCode());
//                System.out.println("currencyDescription is: " + dailyCurrencyRates.getRate());
            }
            System.out.println(new Date() + " - currency data is now updated ...");

        } catch (MalformedURLException MalURLex) {

        } catch (IOException IOex) {

        } catch (ParserConfigurationException PCex) {

        } catch (SAXException SAXex) {

        } catch (TransformerConfigurationException TCex) {

        } catch (TransformerException TransFex) {

        }
    }

    private double checkRate(String rateToCheck) {

        double rate = 0;
        if (rateToCheck.equalsIgnoreCase("-")) {
            rate = 0;
        } else {
            rate = Double.parseDouble(rateToCheck);
        }
        return rate;
    }

    @Override
    public void run() {
        updateExchangeRates();
    }
}
