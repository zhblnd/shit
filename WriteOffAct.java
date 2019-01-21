package database.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.*;

@Entity
@Table(name = "writeoffact", schema = "public", catalog = "trade_org")
public class WriteOffAct {
    private UUID id;
    private Date date;
    private Time time;
    private String reason;
    private BigDecimal price;
    private Collection<WriteOffAct_Good> writeOffAct_goods;

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
    @Column(name = "reason")
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Basic
    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @OneToMany(mappedBy = "writeOffAct")
    public Collection<WriteOffAct_Good> getWriteOffAct_goods() {
        if(writeOffAct_goods == null){
            this.setWriteOffAct_goods(new ArrayList<>());
        }
        return writeOffAct_goods;
    }

    public void setWriteOffAct_goods(Collection<WriteOffAct_Good> writeOffAct_goods) {
        this.writeOffAct_goods = writeOffAct_goods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WriteOffAct that = (WriteOffAct) o;
        return
                Objects.equals(id, that.id) &&
                        Objects.equals(date, that.date) &&
                        Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, time);
    }
}
