package com.repertory.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * Created by Finderlo on 2016/11/4.
 */
public class ItemApplicationEntityPK implements Serializable {
    private String applicationId;
    private String itemCode;

    @Column(name = "application_ID")
    @Id
    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
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

        ItemApplicationEntityPK pk = (ItemApplicationEntityPK) o;

        if (applicationId != null ? !applicationId.equals(pk.applicationId) : pk.applicationId != null)
            return false;
        if (itemCode != null ? !itemCode.equals(pk.itemCode) : pk.itemCode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = applicationId != null ? applicationId.hashCode() : 0;
        result = 31 * result + (itemCode != null ? itemCode.hashCode() : 0);
        return result;
    }
}
