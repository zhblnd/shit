package database.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "goodtype", schema = "public", catalog = "trade_org")
public class GoodType {
    private UUID id;
    private String name;
    private int norm;
    private boolean deleted;
    private Collection<Good> goods;

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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "norm")
    public int getNorm() {
        return norm;
    }

    public void setNorm(int norm) {
        this.norm = norm;
    }

    @Basic
    @Column(name = "deleted")
    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @OneToMany(mappedBy = "goodType")
    public Collection<Good> getGoods() {
        if(goods == null){
            this.setGoods(new ArrayList<>());
        }
        return goods;
    }

    public void setGoods(Collection<Good> goods) {
        this.goods = goods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoodType that = (GoodType) o;
        return
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
