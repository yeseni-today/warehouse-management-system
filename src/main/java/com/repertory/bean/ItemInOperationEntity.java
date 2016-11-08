package com.repertory.bean;

import java.sql.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 物品入库表
 */
@Entity
@Table(name = "item_in_operation", schema = "wms", catalog = "")
public class ItemInOperationEntity {
    private String storageId;
    private Date storageTime;
    private String operationId;

    @Id
    @Column(name = "storage_ID")
    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }

    @Basic
    @Column(name = "storage_time")
    public Date getStorageTime() {
        return storageTime;
    }

    public void setStorageTime(Date storageTime) {
        this.storageTime = storageTime;
    }

    @Basic
    @Column(name = "operation_ID")
    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemInOperationEntity entity = (ItemInOperationEntity) o;

        if (storageId != null ? !storageId.equals(entity.storageId) : entity.storageId != null)
            return false;
        if (storageTime != null ? !storageTime.equals(entity.storageTime) : entity.storageTime != null)
            return false;
        if (operationId != null ? !operationId.equals(entity.operationId) : entity.operationId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = storageId != null ? storageId.hashCode() : 0;
        result = 31 * result + (storageTime != null ? storageTime.hashCode() : 0);
        result = 31 * result + (operationId != null ? operationId.hashCode() : 0);
        return result;
    }
}
