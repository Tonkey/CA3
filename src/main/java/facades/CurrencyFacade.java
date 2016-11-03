package facades;

import entity.CurrencyRates;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import security.ICurencyRatesFacade;

/**
 *
 * @author Michael
 */
public class CurrencyFacade implements ICurencyRatesFacade{

    EntityManagerFactory emf;

    public CurrencyFacade() {
        emf = Persistence.createEntityManagerFactory("pu_development");
    }

    private EntityManager getEntityManager() {

        return emf.createEntityManager();

    }

    @Override
    public boolean updateDailyCurrencies(List<CurrencyRates> dailyCurrencies) {

        EntityManager em = getEntityManager();


        try {
                em.getTransaction().begin();

            for (int i = 0; i < dailyCurrencies.size(); i++) {
        
                em.merge(dailyCurrencies.get(i));

            }
                em.getTransaction().commit();
                
        } catch (PersistenceException e) {
            
            //if (e instanceof ConstraintViolationException) {
                
                e.printStackTrace();
            return false;
            //}
            
        } catch (Exception e) {
            
            //if()
            
            e.printStackTrace();
            return false;

        } finally {

            em.close();

        }
        return true;
    }

    @Override
    public List<CurrencyRates> getDailyCurrencyRates() {
        EntityManager em = getEntityManager();
        List<CurrencyRates> rates = new ArrayList<CurrencyRates>();
        
        Date date = new Date();
        try {
   
            em.getTransaction().begin();
            
            Query q =  em.createQuery("SELECT u from CurrencyRates u WHERE u.date = :date").setParameter("date", date);
          
            rates =  q.getResultList();
                    
            em.getTransaction().commit();

            return rates;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
        
        
    }

    @Override
    public List<CurrencyRates> getSpecificCurrencyRates(Date startDate, Date endDate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
