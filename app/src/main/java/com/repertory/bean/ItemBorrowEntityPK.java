package com.repertory.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * Created by Finderlo on 2016/11/4.
 */
public class ItemBorrowEntityPK implements Serializable {
    private String borrowId;
    private String itemCode;

    @Column(name = "borrow_ID")
    @Id
    public String getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }

    @Column(name = "item_code")
    @Id
    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemBorrowEntityPK pk = (ItemBorrowEntityPK) o;

        if (borrowId != null ? !borrowId.equals(pk.borrowId) : pk.borrowId != null) return false;
        if (itemCode != null ? !itemCode.equals(pk.itemCode) : pk.itemCode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = borrowId != null ? borrowId.hashCode() : 0;
        result = 31 * result + (itemCode != null ? itemCode.hashCode() : 0);
        return result;
    }
}
