package database.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.*;

@Entity
@Table(name = "receipt", schema = "public", catalog = "trade_org")
public class Receipt {
    private UUID id;
    private Date date;
    private Time time;
    private String supplierinfo;
    private BigDecimal price;
    private Collection<Receipt_Good> receipt_goods;

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
    @Column(name = "supplierinfo")
    public String getSupplierinfo() {
        return supplierinfo;
    }

    public void setSupplierinfo(String supplierinfo) {
        this.supplierinfo = supplierinfo;
    }

    @OneToMany(mappedBy = "receipt")
    public Collection<Receipt_Good> getReceipt_goods() {
        if(receipt_goods == null){
            this.setReceipt_goods(new ArrayList<>());
        }
        return receipt_goods;
    }

    public void setReceipt_goods(Collection<Receipt_Good> receipt_goods) {
        this.receipt_goods = receipt_goods;
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
        Receipt that = (Receipt) o;
        return
                Objects.equals(id, that.id) &&
                Objects.equals(date, that.date) &&
                Objects.equals(time, that.time) &&
                Objects.equals(supplierinfo, that.supplierinfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, time, supplierinfo);
    }
}
