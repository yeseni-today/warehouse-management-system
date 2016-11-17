package com.repository.entity;

import com.repository.dao.ItemBorrowDao;

import java.sql.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by Finderlo on 2016/11/4.
 */
@Entity
@Table(name = "item_borrow")
@IdClass(ItemBorrowEntityPK.class)
public class ItemBorrowEntity {
    private String borrowId;
    private String itemCode;
    private int counts;
    private Date borrowTime;
    private Date returnTime;
    private String borrowText;

//    @ManyToOne(targetEntity = ItemBorrowOperationEntity.class)
////    @JoinColumn(name = "borrow_ID")
//    @JoinTable(name = "item_borrow_operation",inverseJoinColumns = @JoinColumn(name = "borrow_ID"))
//    private ItemBorrowOperationEntity itemBorrowOperationEntity;



    @Id
    @Column(name = "borrow_ID")
    public String getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
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
    @Column(name = "borrow_time")
    public Date getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(Date borrowTime) {
        this.borrowTime = borrowTime;
    }

    @Basic
    @Column(name = "return_time")
    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    @Basic
    @Column(name = "borrow_text")
    public String getBorrowText() {
        return borrowText;
    }

    public void setBorrowText(String borrowText) {
        this.borrowText = borrowText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemBorrowEntity entity = (ItemBorrowEntity) o;

        if (counts != entity.counts) return false;
        if (borrowId != null ? !borrowId.equals(entity.borrowId) : entity.borrowId != null)
            return false;
        if (itemCode != null ? !itemCode.equals(entity.itemCode) : entity.itemCode != null)
            return false;
        if (borrowTime != null ? !borrowTime.equals(entity.borrowTime) : entity.borrowTime != null)
            return false;
        if (returnTime != null ? !returnTime.equals(entity.returnTime) : entity.returnTime != null)
            return false;
        if (borrowText != null ? !borrowText.equals(entity.borrowText) : entity.borrowText != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = borrowId != null ? borrowId.hashCode() : 0;
        result = 31 * result + (itemCode != null ? itemCode.hashCode() : 0);
        result = 31 * result + counts;
        result = 31 * result + (borrowTime != null ? borrowTime.hashCode() : 0);
        result = 31 * result + (returnTime != null ? returnTime.hashCode() : 0);
        result = 31 * result + (borrowText != null ? borrowText.hashCode() : 0);
        return result;
    }
}
