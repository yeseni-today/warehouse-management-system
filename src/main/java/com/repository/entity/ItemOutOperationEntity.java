package com.repository.entity;

import java.sql.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Created by Finderlo on 2016/11/4.
 */
@Entity
@Table(name = "item_out_operation", schema = "wms", catalog = "")
public class ItemOutOperationEntity {
    private String outId;
    private String usersId;
    private String applyId;
    private String outAddress;
    @Basic
    @Column(name = "out_time")
    @Temporal(TemporalType.DATE)
    private Date outTime;
    private String outStates;
    private String operationId;

    @Id
    @Column(name = "out_ID")
    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    @Basic
    @Column(name = "users_ID")
    public String getUsersId() {
        return usersId;
    }

    public void setUsersId(String usersId) {
        this.usersId = usersId;
    }

    @Basic
    @Column(name = "apply_ID")
    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    @Basic
    @Column(name = "out_address")
    public String getOutAddress() {
        return outAddress;
    }

    public void setOutAddress(String outAddress) {
        this.outAddress = outAddress;
    }


    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    @Basic
    @Column(name = "out_states")
    public String getOutStates() {
        return outStates;
    }

    public void setOutStates(String outStates) {
        this.outStates = outStates;
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

        ItemOutOperationEntity entity = (ItemOutOperationEntity) o;

        if (outId != null ? !outId.equals(entity.outId) : entity.outId != null) return false;
        if (usersId != null ? !usersId.equals(entity.usersId) : entity.usersId != null)
            return false;
        if (applyId != null ? !applyId.equals(entity.applyId) : entity.applyId != null)
            return false;
        if (outAddress != null ? !outAddress.equals(entity.outAddress) : entity.outAddress != null)
            return false;
        if (outTime != null ? !outTime.equals(entity.outTime) : entity.outTime != null)
            return false;
        if (outStates != null ? !outStates.equals(entity.outStates) : entity.outStates != null)
            return false;
        if (operationId != null ? !operationId.equals(entity.operationId) : entity.operationId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = outId != null ? outId.hashCode() : 0;
        result = 31 * result + (usersId != null ? usersId.hashCode() : 0);
        result = 31 * result + (applyId != null ? applyId.hashCode() : 0);
        result = 31 * result + (outAddress != null ? outAddress.hashCode() : 0);
        result = 31 * result + (outTime != null ? outTime.hashCode() : 0);
        result = 31 * result + (outStates != null ? outStates.hashCode() : 0);
        result = 31 * result + (operationId != null ? operationId.hashCode() : 0);
        return result;
    }
}
