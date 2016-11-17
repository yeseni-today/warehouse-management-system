package com.repository.entity;

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
@Table(name = "item_out_storage")
@IdClass(ItemOutStorageEntityPK.class)
public class ItemOutStorageEntity {
    private String outId;
    private String itemCode;
    private int counts;

    @Id
    @Column(name = "out_ID")
    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemOutStorageEntity entity = (ItemOutStorageEntity) o;

        if (counts != entity.counts) return false;
        if (outId != null ? !outId.equals(entity.outId) : entity.outId != null) return false;
        if (itemCode != null ? !itemCode.equals(entity.itemCode) : entity.itemCode != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = outId != null ? outId.hashCode() : 0;
        result = 31 * result + (itemCode != null ? itemCode.hashCode() : 0);
        result = 31 * result + counts;
        return result;
    }
}
