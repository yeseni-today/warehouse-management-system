package com.repository.entity;

import com.google.gson.Gson;

import java.sql.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * Created by Finderlo on 2016/11/4.
 */
@Entity
@Table(name = "item_in_storage")
@IdClass(ItemInStorageEntityPK.class)
public class ItemInStorageEntity implements Cloneable {
    private String storageId;
    private String itemCode;
    private int counts;
    private Double price;
    private String billCode;
    private String itemSlot;
    private String itemBatch;
    private Date itemIndate;
    private int allowCount;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    @Override
    public ItemInStorageEntity clone() {
        try {
            return (ItemInStorageEntity) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Id
    @Column(name = "storage_ID")
    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }

    @Id
    @Column(name = "item_code")
    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    @Basic
    @Column(name = "counts")
    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    @Basic
    @Column(name = "price")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "bill_code")
    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    @Basic
    @Column(name = "item_slot")
    public String getItemSlot() {
        return itemSlot;
    }

    public void setItemSlot(String itemSlot) {
        this.itemSlot = itemSlot;
    }

    @Basic
    @Column(name = "item_batch")
    public String getItemBatch() {
        return itemBatch;
    }

    public void setItemBatch(String itemBatch) {
        this.itemBatch = itemBatch;
    }

    @Basic
    @Column(name = "item_indate")
    public Date getItemIndate() {
        return itemIndate;
    }

    public void setItemIndate(Date itemIndate) {
        this.itemIndate = itemIndate;
    }

    @Basic
    @Column(name = "allow_count")
    public int getAllowCount() {
        return allowCount;
    }

    public void setAllowCount(int allowCount) {
        this.allowCount = allowCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemInStorageEntity entity = (ItemInStorageEntity) o;

        if (counts != entity.counts) return false;
        if (allowCount != entity.allowCount) return false;
        if (storageId != null ? !storageId.equals(entity.storageId) : entity.storageId != null)
            return false;
        if (itemCode != null ? !itemCode.equals(entity.itemCode) : entity.itemCode != null)
            return false;
        if (price != null ? !price.equals(entity.price) : entity.price != null) return false;
        if (billCode != null ? !billCode.equals(entity.billCode) : entity.billCode != null)
            return false;
        if (itemSlot != null ? !itemSlot.equals(entity.itemSlot) : entity.itemSlot != null)
            return false;
        if (itemBatch != null ? !itemBatch.equals(entity.itemBatch) : entity.itemBatch != null)
            return false;
        if (itemIndate != null ? !itemIndate.equals(entity.itemIndate) : entity.itemIndate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = storageId != null ? storageId.hashCode() : 0;
        result = 31 * result + (itemCode != null ? itemCode.hashCode() : 0);
        result = 31 * result + counts;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (billCode != null ? billCode.hashCode() : 0);
        result = 31 * result + (itemSlot != null ? itemSlot.hashCode() : 0);
        result = 31 * result + (itemBatch != null ? itemBatch.hashCode() : 0);
        result = 31 * result + (itemIndate != null ? itemIndate.hashCode() : 0);
        result = 31 * result + allowCount;
        return result;
    }
}
