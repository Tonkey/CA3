package facades;

import static com.google.common.base.Predicates.instanceOf;
import entity.CurrencyRates;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
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
    public List<CurrencyRates> getDaílyCurrencyRates(Date date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CurrencyRates> getSpecificCurrencyRates(Date startDate, Date endDate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
