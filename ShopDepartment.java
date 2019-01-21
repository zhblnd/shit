package database.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "shopdepartment", schema = "public", catalog = "trade_org")
public class ShopDepartment {
    private UUID id;
    private String name;
    private Collection<Seller> sellers;
    private Collection<Sale> sales;

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

    @OneToMany(mappedBy = "shopDepartment")
    public Collection<Seller> getSellers() {
        if(sellers == null){
            this.setSellers(new ArrayList<>());
        }
        return sellers;
    }

    public void setSellers(Collection<Seller> sellers) {
        this.sellers = sellers;
    }

//    @OneToMany(mappedBy = "sales")
//    public Collection<Sale> getSales() {
//        if(sales == null){
//            this.setSales(new ArrayList<>());
//        }
//        return sales;
//    }

//    public void setSales(Collection<Sale> sales) {
//        this.sales = sales;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShopDepartment that = (ShopDepartment) o;
        return
                Objects.equals(id, that.id) &&
                        Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
