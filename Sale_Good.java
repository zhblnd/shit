package database.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "sale_good", schema = "public", catalog = "trade_org")
public class Sale_Good {
    private UUID id;
    private UUID sale_id;
    private UUID good_id;
    private Sale sale;
    private Good good;

    @Id
    @Type(type = "pg-uuid")
    @Column(name = "id")
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Basic
    @Type(type = "pg-uuid")
    @Column(name = "saleid")
    public UUID getSale_id() {
        return sale_id;
    }

    public void setSale_id(UUID sale_id) {
        this.sale_id = sale_id;
    }

    @Basic
    @Type(type = "pg-uuid")
    @Column(name = "goodid")
    public UUID getGood_id() {
        return good_id;
    }

    public void setGood_id(UUID good_id) {
        this.good_id = good_id;
    }

    @ManyToOne
    @JoinColumn(name = "saleid", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    @ManyToOne
    @JoinColumn(name = "goodid", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sale_Good that = (Sale_Good) o;
        return
                Objects.equals(id, that.id) &&
                Objects.equals(sale_id, that.sale_id) &&
                Objects.equals(good_id, that.good_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sale_id, good_id);
    }
}
