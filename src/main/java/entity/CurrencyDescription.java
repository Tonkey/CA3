package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Michael
 */
@Entity
public class CurrencyDescription {
    
    @Id
    @Column(columnDefinition = "CHAR(3)")
    private String id;
    
    private String description;
    
    public CurrencyDescription(){}
    
    public CurrencyDescription(String id, String description) {
        this.id = id;
        this.description = description;
    }
    
    public String getCurrencyDescription() {
        return description;
    }

    public String getId() {
        return id;
    }
}
