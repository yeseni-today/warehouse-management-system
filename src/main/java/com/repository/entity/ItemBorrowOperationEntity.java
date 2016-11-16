package com.repository.entity;

import com.repository.dao.ItemBorrowOpreationDao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by Finderlo on 2016/11/4.
 */
@Entity
@Table(name = "item_borrow_operation", schema = "wms", catalog = "")
public class ItemBorrowOperationEntity {
    private String borrowId;
    private String usersId;
    private String examineId;
    private String states;
    private Date statesTime;
    private String returnOperationId;
    private String returnText;


    private List<ItemBorrowEntity> itemBorrowEntities = new ArrayList<>();

    public static void main(String[] args) {
        ItemBorrowOpreationDao dao = new ItemBorrowOpreationDao();
        dao.findAll().forEach(e -> System.out.println(e.getBorrowId() + ";" + e.itemBorrowEntities.size()));

    }

    @Id
    @Column(name = "borrow_ID")
    public String getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
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
    @Column(name = "return_operation_ID")
    public String getReturnOperationId() {
        return returnOperationId;
    }

    public void setReturnOperationId(String returnOperationId) {
        this.returnOperationId = returnOperationId;
    }

    @Basic
    @Column(name = "return_text")
    public String getReturnText() {
        return returnText;
    }

    public void setReturnText(String returnText) {
        this.returnText = returnText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemBorrowOperationEntity entity = (ItemBorrowOperationEntity) o;

        if (borrowId != null ? !borrowId.equals(entity.borrowId) : entity.borrowId != null)
            return false;
        if (usersId != null ? !usersId.equals(entity.usersId) : entity.usersId != null)
            return false;
        if (examineId != null ? !examineId.equals(entity.examineId) : entity.examineId != null)
            return false;
        if (states != null ? !states.equals(entity.states) : entity.states != null) return false;
        if (statesTime != null ? !statesTime.equals(entity.statesTime) : entity.statesTime != null)
            return false;
        if (returnOperationId != null ? !returnOperationId.equals(entity.returnOperationId) : entity.returnOperationId != null)
            return false;
        if (returnText != null ? !returnText.equals(entity.returnText) : entity.returnText != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = borrowId != null ? borrowId.hashCode() : 0;
        result = 31 * result + (usersId != null ? usersId.hashCode() : 0);
        result = 31 * result + (examineId != null ? examineId.hashCode() : 0);
        result = 31 * result + (states != null ? states.hashCode() : 0);
        result = 31 * result + (statesTime != null ? statesTime.hashCode() : 0);
        result = 31 * result + (returnOperationId != null ? returnOperationId.hashCode() : 0);
        result = 31 * result + (returnText != null ? returnText.hashCode() : 0);
        return result;
    }

    @Transient
    public List<ItemBorrowEntity> getItemBorrowEntities() {
        return itemBorrowEntities;
    }
}
