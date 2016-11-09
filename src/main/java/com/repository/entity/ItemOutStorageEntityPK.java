package com.repository.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * Created by Finderlo on 2016/11/4.
 */
public class ItemOutStorageEntityPK implements Serializable {
    private String outId;
    private String itemCode;

    @Column(name = "out_ID")
    @Id
    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
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

        ItemOutStorageEntityPK pk = (ItemOutStorageEntityPK) o;

        if (outId != null ? !outId.equals(pk.outId) : pk.outId != null) return false;
        if (itemCode != null ? !itemCode.equals(pk.itemCode) : pk.itemCode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = outId != null ? outId.hashCode() : 0;
        result = 31 * result + (itemCode != null ? itemCode.hashCode() : 0);
        return result;
    }
}
