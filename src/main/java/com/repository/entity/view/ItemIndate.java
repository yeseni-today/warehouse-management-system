package com.repository.entity.view;

import com.google.gson.Gson;

import java.sql.Date;

import javax.persistence.*;

/**
 * Created by Finderlo on 2016/11/30.
 */
@Entity
@Table(name = "vcyk_itemindate")
public class ItemIndate {


    String itemCode;
    String itemName;
    String itemBatch;
    Date itemIndate;
    String itemSlot;
    int allowCount;


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    @Basic
    @Column(name = "item_code")
    @Id
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
    @Column(name = "item_slot")
    public String getItemSlot() {
        return itemSlot;
    }

    public void setItemSlot(String itemSlot) {
        this.itemSlot = itemSlot;
    }

    @Basic
    @Column(name = "allow_count")
    public int getAllowCount() {
        return allowCount;
    }

    public void setAllowCount(int allowCount) {
        this.allowCount = allowCount;
    }
}
