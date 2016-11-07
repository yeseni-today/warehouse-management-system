package com.repertory.bean;

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
@Table(name = "item_application", schema = "wms", catalog = "")
@IdClass(ItemApplicationEntityPK.class)
public class ItemApplicationEntity {
    private String applicationId;
    private String itemCode;
    private int counts;
    private String applicationText;
    private String applicationType;

    @Id
    @Column(name = "application_ID")
    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
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
    @Column(name = "application_text")
    public String getApplicationText() {
        return applicationText;
    }

    public void setApplicationText(String applicationText) {
        this.applicationText = applicationText;
    }

    @Basic
    @Column(name = "application_type")
    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemApplicationEntity entity = (ItemApplicationEntity) o;

        if (counts != entity.counts) return false;
        if (applicationId != null ? !applicationId.equals(entity.applicationId) : entity.applicationId != null)
            return false;
        if (itemCode != null ? !itemCode.equals(entity.itemCode) : entity.itemCode != null)
            return false;
        if (applicationText != null ? !applicationText.equals(entity.applicationText) : entity.applicationText != null)
            return false;
        if (applicationType != null ? !applicationType.equals(entity.applicationType) : entity.applicationType != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = applicationId != null ? applicationId.hashCode() : 0;
        result = 31 * result + (itemCode != null ? itemCode.hashCode() : 0);
        result = 31 * result + counts;
        result = 31 * result + (applicationText != null ? applicationText.hashCode() : 0);
        result = 31 * result + (applicationType != null ? applicationType.hashCode() : 0);
        return result;
    }
}
