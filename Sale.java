package database.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.*;

@Entity
@Table(name = "sale", schema = "public", catalog = "trade_org")
public class Sale {
    private UUID id;
    private Date date;
    private Time time;
    private int check_number;
    private UUID customer_id;
    private UUID seller_id;
    private Customer customer;
    private Seller seller;
    private BigDecimal price;
    private Collection<Sale_Good> sale_goods;

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
    @Column(name = "checknumber")
    public int getCheck_number() {
        return check_number;
    }

    public void setCheck_number(int check_number) {
        this.check_number = check_number;
    }

    @Basic
    @Type(type = "pg-uuid")
    @Column(name = "customerid")
    public UUID getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(UUID customer_id) {
        this.customer_id = customer_id;
    }

    @Basic
    @Type(type = "pg-uuid")
    @Column(name = "sellerid")
    public UUID getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(UUID seller_id) {
        this.seller_id = seller_id;
    }

    @ManyToOne
    @JoinColumn(name = "customerid", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.setCustomer_id(customer.getId());
        this.customer = customer;
    }

    @ManyToOne
    @JoinColumn(name = "sellerid", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.setSeller_id(seller.getId());
        this.seller = seller;
    }

    @OneToMany(mappedBy = "sale")
    public Collection<Sale_Good> getSale_goods() {
        if(sale_goods == null){
            this.setSale_goods(new ArrayList<>());
        }
        return sale_goods;
    }

    public void setSale_goods(Collection<Sale_Good> sale_goods) {
        this.sale_goods = sale_goods;
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
        Sale that = (Sale) o;
        return
                Objects.equals(id, that.id) &&
                        Objects.equals(date, that.date) &&
                        Objects.equals(time, that.time) &&
                        Objects.equals(check_number, that.check_number) &&
                        Objects.equals(customer_id, that.customer_id) &&
                        Objects.equals(seller_id, that.seller_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, time, check_number, customer_id, seller_id);
    }
}
