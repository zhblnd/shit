package database.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "writeoffact_good", schema = "public", catalog = "trade_org")
public class WriteOffAct_Good {
    private UUID id;
    private UUID writeoffact_id;
    private UUID good_id;
    private WriteOffAct writeOffAct;
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
    @Column(name = "writeoffactid")
    public UUID getWriteoffact_id() {
        return writeoffact_id;
    }

    public void setWriteoffact_id(UUID writeoffact_id) {
        this.writeoffact_id = writeoffact_id;
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
    @JoinColumn(name = "writeoffactid", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public WriteOffAct getWriteOffAct() {
        return writeOffAct;
    }

    public void setWriteOffAct(WriteOffAct writeOffAct) {
        this.writeOffAct = writeOffAct;
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
        WriteOffAct_Good that = (WriteOffAct_Good) o;
        return
                Objects.equals(id, that.id) &&
                Objects.equals(writeoffact_id, that.writeoffact_id) &&
                Objects.equals(good_id, that.good_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, writeoffact_id, good_id);
    }
}
