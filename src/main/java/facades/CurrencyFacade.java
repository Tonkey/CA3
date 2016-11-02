package facades;

import entity.CurrencyRates;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Michael
 */
public class CurrencyFacade {

    EntityManagerFactory emf;

    public CurrencyFacade() {
    }

    private EntityManager getEntityManager() {

        return emf.createEntityManager();

    }

    public void updateDailyCurrencies(ArrayList<CurrencyRates> dailyCurrencies) {

        EntityManager em = getEntityManager();

        try {

            for (int i = 0; i < dailyCurrencies.size(); i++) {

                em.getTransaction().begin();
                em.persist(dailyCurrencies.get(i));
                em.getTransaction().commit();

            }
        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            em.close();

        }
    }
}
