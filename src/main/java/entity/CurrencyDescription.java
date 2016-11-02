package entity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Michael
 */
@Entity
public class CurrencyDescription {
    
    @Id
    private int id;
    
    private String description;
    
    public CurrencyDescription(){}
    
    public CurrencyDescription(int id, String description) {
        this.id = id;
        this.description = description;
    }
    
    public String getCurrencyDescription() {
        return description;
    }
    
}
