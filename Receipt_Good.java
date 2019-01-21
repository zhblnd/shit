package database.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "receipt_good", schema = "public", catalog = "trade_org")
public class Receipt_Good {
    private UUID id;
    private UUID receipt_id;
    private UUID good_id;
    private Receipt receipt;
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
    @Column(name = "receiptid")
    public UUID getReceipt_id() {
        return receipt_id;
    }

    public void setReceipt_id(UUID receipt_id) {
        this.receipt_id = receipt_id;
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
    @JoinColumn(name = "receiptid", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
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
        Receipt_Good that = (Receipt_Good) o;
        return
                Objects.equals(id, that.id) &&
                Objects.equals(receipt_id, that.receipt_id) &&
                Objects.equals(good_id, that.good_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, receipt_id, good_id);
    }
}
