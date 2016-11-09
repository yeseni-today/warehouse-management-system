package com.repository.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * Created by Finderlo on 2016/11/4.
 */
public class ItemInStorageEntityPK implements Serializable {
    private String storageId;
    private String itemCode;

    @Column(name = "storage_ID")
    @Id
    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
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

        ItemInStorageEntityPK pk = (ItemInStorageEntityPK) o;

        if (storageId != null ? !storageId.equals(pk.storageId) : pk.storageId != null)
            return false;
        if (itemCode != null ? !itemCode.equals(pk.itemCode) : pk.itemCode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = storageId != null ? storageId.hashCode() : 0;
        result = 31 * result + (itemCode != null ? itemCode.hashCode() : 0);
        return result;
    }
}
