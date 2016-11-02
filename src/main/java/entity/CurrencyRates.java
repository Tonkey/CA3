package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "CURRENCY_RATES",
        indexes = {
            @Index(name = "DATE", columnList = "DATE", unique = false)})
public class CurrencyRates implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE", nullable = false)
    private Date date;
    @Id
    @Column(name = "CODE", nullable = false)
    private String code;

    @Column(name = "RATE", nullable = false)
    private double rate;
    
    @Column(name = "DESC" , nullable = false)
    @OneToOne
    private CurrencyDescription desc;

    public CurrencyRates() {    
    }

    public CurrencyRates(Date date, String code, double rate) {
        this.date = date;
        this.code = code;
        this.rate = rate;
    }

    public Date getDate() {
        return date;
    }

    public String getCode() {
        return code;
    }

    public double getRates() {
        return rate;
    }
}
