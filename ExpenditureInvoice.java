package database.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Time;
import java.util.*;
import java.math.BigDecimal;

@Entity
@Table(name = "expenditureinvoice", schema = "public", catalog = "trade_org")
public class ExpenditureInvoice {
    private UUID id;
    private Date date;
    private Time time;
    private UUID seller_id;
    private Seller seller;
    private BigDecimal price;
    private Collection<ExpenditureInvoice_Good> expenditureInvoice_goods;

    @Id
    @Type(type = "pg-uuid")
    @Column(name = "id")
    public UUID getId() {
        if (id == null) {
            this.setId(UUID.randomUUID());
        }
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "time")
    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Basic
    @Type(type = "pg-uuid")
    @Column(name = "sellerid", nullable = true)
    public UUID getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(UUID seller_id) {
        if(seller_id!=null){
            this.seller_id = seller_id;
        }
    }

    @ManyToOne
    @JoinColumn(name = "sellerid", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        if(seller!=null){
            this.setSeller_id(seller.getId());
            this.seller = seller;
        }
    }

    @OneToMany(mappedBy = "expenditureInvoice")
    public Collection<ExpenditureInvoice_Good> getExpenditureInvoice_goods() {
        if(expenditureInvoice_goods == null){
            this.setExpenditureInvoice_goods(new ArrayList<>());
        }
        return expenditureInvoice_goods;
    }

    public void setExpenditureInvoice_goods(Collection<ExpenditureInvoice_Good> expenditureInvoice_goods) {
        this.expenditureInvoice_goods = expenditureInvoice_goods;
    }

    @Basic
    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpenditureInvoice that = (ExpenditureInvoice) o;
        return
                Objects.equals(id, that.id) &&
                        Objects.equals(date, that.date) &&
                        Objects.equals(time, that.time) &&
                        Objects.equals(seller_id, that.seller_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, time, seller_id);
    }
}
