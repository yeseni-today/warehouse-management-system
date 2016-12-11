package com.repository.entity;

import com.google.gson.Gson;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by Finderlo on 2016/11/4.
 */
@Entity
@Table(name = "item")
public class ItemEntity implements Cloneable {
    private String itemCode;
    private String itemName;

    private String itemSpec;
    private Double itemPrice;
    private String itemIntroduce;
    private int itemBorrowTimelimit;
    private String itemState;
    private String itemExamine;//物品审核信息
    private int itemRemind;    //物品库存提醒
    private CategoryEntity categoryEntity;
    private CompanyEntity companyEntity;

    private int itemCount;

    @Override
    public ItemEntity clone() {
        try {
            return (ItemEntity) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    //    @Transient
//    private boolean isInSchool;
    @Id
    @Column(name = "item_code")
    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    @Basic
    @Column(name = "item_name")
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Basic
    @Column(name = "item_count")
    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    @Basic
    @Column(name = "item_spec")
    public String getItemSpec() {
        return itemSpec;
    }

    public void setItemSpec(String itemSpec) {
        this.itemSpec = itemSpec;
    }

    @Basic
    @Column(name = "item_price")
    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    @Basic
    @Column(name = "item_introduce")
    public String getItemIntroduce() {
        return itemIntroduce;
    }

    public void setItemIntroduce(String itemIntroduce) {
        this.itemIntroduce = itemIntroduce;
    }

    @Basic
    @Column(name = "item_borrow_timelimit")
    public int getItemBorrowTimelimit() {
        return itemBorrowTimelimit;
    }

    public void setItemBorrowTimelimit(int itemBorrowTimelimit) {
        this.itemBorrowTimelimit = itemBorrowTimelimit;
    }

    @Basic
    @Column(name = "item_state")
    public String getItemState() {
        return itemState;
    }

    public void setItemState(String itemState) {
        this.itemState = itemState;
    }

    @Basic
    @Column(name = "item_examine")
    public String getItemExamine() {
        return itemExamine;
    }

    public void setItemExamine(String itemExamine) {
        this.itemExamine = itemExamine;
    }

    @Basic
    @Column(name = "item_remind")
    public int getItemRemind() {
        return itemRemind;
    }

    public void setItemRemind(int itemRemind) {
        this.itemRemind = itemRemind;
    }


    @OneToOne(targetEntity = CategoryEntity.class,cascade = CascadeType.REFRESH)
    @JoinColumn(name = "item_categoryId")
    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }

    public void setItemCategoryId(String itemCategoryId){
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryId(itemCategoryId);
        this.categoryEntity = categoryEntity;
    }

    @OneToOne(targetEntity = CompanyEntity.class,cascade = CascadeType.REFRESH)
    @JoinColumn(name = "item_companyId")
    public CompanyEntity getCompanyEntity() {
        return companyEntity;
    }

    public void setCompanyEntity(CompanyEntity companyEntity) {
        this.companyEntity = companyEntity;
    }

    public void setItemCompanyId(String itemCompanyId){
        CompanyEntity itemCategoryEntity = new CompanyEntity();
        itemCategoryEntity.setCompanyId(itemCompanyId);
        this.companyEntity = itemCategoryEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemEntity entity = (ItemEntity) o;

        if (itemCount != entity.itemCount) return false;
        if (itemBorrowTimelimit != entity.itemBorrowTimelimit) return false;
        if (itemRemind != entity.itemRemind) return false;
        if (itemCode != null ? !itemCode.equals(entity.itemCode) : entity.itemCode != null)
            return false;
        if (itemName != null ? !itemName.equals(entity.itemName) : entity.itemName != null)
            return false;
        if (itemSpec != null ? !itemSpec.equals(entity.itemSpec) : entity.itemSpec != null)
            return false;
        if (itemPrice != null ? !itemPrice.equals(entity.itemPrice) : entity.itemPrice != null)
            return false;
        if (itemIntroduce != null ? !itemIntroduce.equals(entity.itemIntroduce) : entity.itemIntroduce != null)
            return false;
        if (itemState != null ? !itemState.equals(entity.itemState) : entity.itemState != null)
            return false;
        if (itemExamine != null ? !itemExamine.equals(entity.itemExamine) : entity.itemExamine != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = itemCode != null ? itemCode.hashCode() : 0;
        result = 31 * result + (itemName != null ? itemName.hashCode() : 0);
        result = 31 * result + itemCount;
        result = 31 * result + (itemSpec != null ? itemSpec.hashCode() : 0);
        result = 31 * result + (itemPrice != null ? itemPrice.hashCode() : 0);
        result = 31 * result + (itemIntroduce != null ? itemIntroduce.hashCode() : 0);
        result = 31 * result + itemBorrowTimelimit;
        result = 31 * result + (itemState != null ? itemState.hashCode() : 0);
        result = 31 * result + (itemExamine != null ? itemExamine.hashCode() : 0);
        result = 31 * result + itemRemind;
        return result;
    }

//    public boolean isInSchool() {
//        return isInSchool;
//    }
//
//    public void setInSchool(boolean inSchool) {
//        isInSchool = inSchool;
//    }
}
