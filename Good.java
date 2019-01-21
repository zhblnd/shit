package database.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "good", schema = "public", catalog = "trade_org")
public class Good {
    private UUID id;
    private String name;
    private String manufacturer;
    private int nomenclaturenumber;
    private BigDecimal price;
    private String advertisinginformation;
    private UUID goodtype_id;
    private GoodType goodType;
    private UUID shop_department_id;
    private ShopDepartment shopDepartment;
    private int amount;
    private Collection<ExpenditureInvoice_Good> expenditureInvoice_goods;
    private Collection<Receipt_Good> receipt_goods;
    private Collection<Sale_Good> sale_goods;
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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "manufacturer")
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Basic
    @Column(name = "amount")
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "nomenclaturenumber")
    public int getNomenclaturenumber() {
        return nomenclaturenumber;
    }

    public void setNomenclaturenumber(int nomenclaturenumber) {
        this.nomenclaturenumber = nomenclaturenumber;
    }

    @Basic
    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Basic
    @Column(name = "advertisinginformation")
    public String getAdvertisinginformation() {
        return advertisinginformation;
    }

    public void setAdvertisinginformation(String advertisinginformation) {
        this.advertisinginformation = advertisinginformation;
    }

    @Basic
    @Type(type = "pg-uuid")
    @Column(name = "goodtypeid")
    public UUID getGoodtype_id() {
        return goodtype_id;
    }

    public void setGoodtype_id(UUID goodtype_id) {
        this.goodtype_id = goodtype_id;
    }

//    @Basic
//    @Type(type = "pg-uuid")
//    @Column(name = "shopdepartmentid", nullable = true)
//    public UUID getShop_department_id() {
//        return shop_department_id;
//    }
//
//    public void setShop_department_id(UUID shop_department_id) {
//        this.shop_department_id = shop_department_id;
//    }

    @ManyToOne
    @JoinColumn(name = "goodtypeid", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public GoodType getGoodType() {
        return goodType;
    }

    public void setGoodType(GoodType goodType) {
        this.setGoodtype_id(goodType.getId());
        this.goodType = goodType;
    }
//
//    @ManyToOne
//    @JoinColumn(name = "shopdepartmentid", referencedColumnName = "id", nullable = true, insertable = false, updatable = false)
//    public ShopDepartment getShopDepartment() {
//        return shopDepartment;
//    }
//
//    public void setShopDepartment(ShopDepartment shopDepartment) {
//        this.setShop_department_id(shopDepartment.getId());
//        this.shopDepartment = shopDepartment;
//    }

    @OneToMany(mappedBy = "good")
    public Collection<ExpenditureInvoice_Good> getExpenditureInvoice_goods() {
        if(expenditureInvoice_goods == null){
            this.setExpenditureInvoice_goods(new ArrayList<>());
        }
        return expenditureInvoice_goods;
    }

    public void setExpenditureInvoice_goods(Collection<ExpenditureInvoice_Good> expenditureInvoice_goods) {
        this.expenditureInvoice_goods = expenditureInvoice_goods;
    }

    @OneToMany(mappedBy = "good")
    public Collection<Receipt_Good> getReceipt_goods() {
        if(receipt_goods == null){
            this.setReceipt_goods(new ArrayList<>());
        }
        return receipt_goods;
    }

    public void setReceipt_goods(Collection<Receipt_Good> receipt_goods) {
        this.receipt_goods = receipt_goods;
    }

    @OneToMany(mappedBy = "good")
    public Collection<Sale_Good> getSale_goods() {
        if(sale_goods == null){
            this.setSale_goods(new ArrayList<>());
        }
        return sale_goods;
    }

    public void setSale_goods(Collection<Sale_Good> sale_goods) {
        this.sale_goods = sale_goods;
    }

    @OneToMany(mappedBy = "good")
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
        Good that = (Good) o;
        return
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(manufacturer, that.manufacturer) &&
                Objects.equals(nomenclaturenumber, that.nomenclaturenumber) &&
                Objects.equals(price, that.price) &&
                Objects.equals(advertisinginformation, that.advertisinginformation) &&
                Objects.equals(goodtype_id, that.goodtype_id) &&
                Objects.equals(shop_department_id, that.shop_department_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, manufacturer, nomenclaturenumber,
                price,  advertisinginformation, goodtype_id, shop_department_id);
    }
}
