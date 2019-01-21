package database.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "expenditureinvoice_good", schema = "public", catalog = "trade_org")
public class ExpenditureInvoice_Good {
    private UUID id;
    private UUID expenditureinvoice_id;
    private UUID good_id;
    private ExpenditureInvoice expenditureInvoice;
    private Good good;

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
    @Type(type = "pg-uuid")
    @Column(name = "expenditureinvoiceid")
    public UUID getExpenditureinvoice_id() {
        return expenditureinvoice_id;
    }

    public void setExpenditureinvoice_id(UUID expenditureinvoice_id) {
        this.expenditureinvoice_id = expenditureinvoice_id;
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
    @JoinColumn(name = "expenditureinvoiceid", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public ExpenditureInvoice getExpenditureInvoice() {
        return expenditureInvoice;
    }

    public void setExpenditureInvoice(ExpenditureInvoice expenditureInvoice) {
        this.setExpenditureinvoice_id(expenditureInvoice.getId());
        this.expenditureInvoice = expenditureInvoice;
    }

    @ManyToOne
    @JoinColumn(name = "goodid", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.setGood_id(good.getId());
        this.good = good;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpenditureInvoice_Good that = (ExpenditureInvoice_Good) o;
        return
                Objects.equals(id, that.id) &&
                Objects.equals(expenditureinvoice_id, that.expenditureinvoice_id) &&
                Objects.equals(good_id, that.good_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, expenditureinvoice_id, good_id);
    }
}
