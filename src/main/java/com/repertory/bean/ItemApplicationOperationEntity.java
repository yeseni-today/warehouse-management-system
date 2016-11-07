package com.repertory.bean;

import java.sql.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Finderlo on 2016/11/4.
 */
@Entity
@Table(name = "item_application_operation", schema = "wms", catalog = "")
public class ItemApplicationOperationEntity {
    private String applicationId;
    private String usersId;
    private String examineId;
    private String states;
    private Date statesTime;
    private Date applicationTime;

    @Id
    @Column(name = "application_ID")
    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
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
    @Column(name = "examine_ID")
    public String getExamineId() {
        return examineId;
    }

    public void setExamineId(String examineId) {
        this.examineId = examineId;
    }

    @Basic
    @Column(name = "states")
    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    @Basic
    @Column(name = "states_time")
    public Date getStatesTime() {
        return statesTime;
    }

    public void setStatesTime(Date statesTime) {
        this.statesTime = statesTime;
    }

    @Basic
    @Column(name = "application_time")
    public Date getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(Date applicationTime) {
        this.applicationTime = applicationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemApplicationOperationEntity entity = (ItemApplicationOperationEntity) o;

        if (applicationId != null ? !applicationId.equals(entity.applicationId) : entity.applicationId != null)
            return false;
        if (usersId != null ? !usersId.equals(entity.usersId) : entity.usersId != null)
            return false;
        if (examineId != null ? !examineId.equals(entity.examineId) : entity.examineId != null)
            return false;
        if (states != null ? !states.equals(entity.states) : entity.states != null) return false;
        if (statesTime != null ? !statesTime.equals(entity.statesTime) : entity.statesTime != null)
            return false;
        if (applicationTime != null ? !applicationTime.equals(entity.applicationTime) : entity.applicationTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = applicationId != null ? applicationId.hashCode() : 0;
        result = 31 * result + (usersId != null ? usersId.hashCode() : 0);
        result = 31 * result + (examineId != null ? examineId.hashCode() : 0);
        result = 31 * result + (states != null ? states.hashCode() : 0);
        result = 31 * result + (statesTime != null ? statesTime.hashCode() : 0);
        result = 31 * result + (applicationTime != null ? applicationTime.hashCode() : 0);
        return result;
    }
}
