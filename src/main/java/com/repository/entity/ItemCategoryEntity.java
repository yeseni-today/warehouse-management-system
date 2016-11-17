package com.repository.entity;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Created by Finderlo on 2016/11/4.
 */
@Entity
@Table(name = "item_category")
public class ItemCategoryEntity {
    private String categoryId;
    private String categoryName;

    @Id
    @Column(name = "category_ID")
    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Basic
    @Column(name = "category_name")
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemCategoryEntity entity = (ItemCategoryEntity) o;

        if (categoryId != null ? !categoryId.equals(entity.categoryId) : entity.categoryId != null)
            return false;
        if (categoryName != null ? !categoryName.equals(entity.categoryName) : entity.categoryName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = categoryId != null ? categoryId.hashCode() : 0;
        result = 31 * result + (categoryName != null ? categoryName.hashCode() : 0);
        return result;
    }
}
