package entity;

import java.io.Serializable;
import java.util.Date;
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
    @OneToOne
    private CurrencyDescription code;

    @Column(name = "RATE", nullable = false)
    private double rate;

//    @JoinColumn(name = "DESCRIPTION" , nullable = false)
//    @OneToOne
//    private CurrencyDescription desc;
    public CurrencyRates() {
    }

    public CurrencyRates(Date date, CurrencyDescription code, double rate) {
        this.date = date;
        this.code = code;
        this.rate = rate;
//        this.desc = desc;
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

    public double getRate() {
        return rate;
    }
//
//    public CurrencyDescription getDesc() {
//        return desc;
//    }
}
