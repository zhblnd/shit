package database.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "seller", schema = "public", catalog = "trade_org")
public class Seller {
    private UUID id;
    private String name;
    private String surname;
    private String patronymic;
    private UUID shop_department_id;
    private ShopDepartment shopDepartment;
    private Collection<ExpenditureInvoice> expenditureInvoices;
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

    @Basic
    @Column(name = "surname")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "patronymic")
    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @Basic
    @Type(type = "pg-uuid")
    @Column(name = "shopdepartmentid")
    public UUID getShop_department_id() {
        return shop_department_id;
    }

    public void setShop_department_id(UUID shop_department_id) {
        this.shop_department_id = shop_department_id;
    }

    @ManyToOne
    @JoinColumn(name = "shopdepartmentid", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public ShopDepartment getShopDepartment() {
        return shopDepartment;
    }

    public void setShopDepartment(ShopDepartment shopDepartment) {
        this.setShop_department_id(shopDepartment.getId());
        this.shopDepartment = shopDepartment;
    }

    @OneToMany(mappedBy = "seller")
    public Collection<ExpenditureInvoice> getExpenditureInvoices() {
        if(expenditureInvoices == null){
            this.setExpenditureInvoices(new ArrayList<>());
        }
        return expenditureInvoices;
    }

    public void setExpenditureInvoices(Collection<ExpenditureInvoice> expenditureInvoices) {
        this.expenditureInvoices = expenditureInvoices;
    }

    @OneToMany(mappedBy = "seller")
    public Collection<Sale> getSales() {
        if(sales == null){
            this.setSales(new ArrayList<>());
        }
        return sales;
    }

    public void setSales(Collection<Sale> sales) {
        this.sales = sales;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seller that = (Seller) o;
        return
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname) &&
                Objects.equals(patronymic, that.patronymic) &&
                Objects.equals(shop_department_id, that.shop_department_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, patronymic, shop_department_id);
    }
}
