package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
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
            @Index(name = "CODE", columnList = "CODE", unique = false)})
public class CurrencyRates implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE", nullable = false)
    private Date date;
    @Id
    @JoinColumn(name = "CODE", nullable = false)
    @OneToOne(cascade = CascadeType.PERSIST)
    private CurrencyDescription code;

    @Column(name = "RATE", nullable = false)
    private double rate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TIMESTAMP", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date timestamp;

    public CurrencyRates() {
    }

    public CurrencyRates(Date date, CurrencyDescription code, double rate, Date timestamp) {
        this.date = date;
        this.code = code;
        this.rate = rate;
        this.timestamp = timestamp;
    }

    public Date getDate() {
        return date;
    }

    public String getCode() {
        return code.getCurrencyDescription();
    }

    public String getCodeID() {
        return code.getId();
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public double getRate() {
        return rate;
    }

}
