package security;

import entity.CurrencyRates;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Michael
 */
public interface ICurencyRatesFacade {
    
    public boolean updateDailyCurrencies(List<CurrencyRates> dailyCurrencies);
    
    public List<CurrencyRates> getDailyCurrencyRates();
    
    public List<CurrencyRates> getSpecificCurrencyRates(Date startDate, Date endDate);
    
}
