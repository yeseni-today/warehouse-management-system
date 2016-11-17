package com.repository.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Finderlo on 11/16/2016.
 */
@Entity
@Table(name = "sdictionary")
public class SdictionaryEntity {
    private String field;
    private String table;
    private int index;
    private int indexlength;

    @Id
    @Column(name = "field")
    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    @Basic
    @Column(name = "_table")
    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    @Basic
    @Column(name = "_index")
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Basic
    @Column(name = "_indexlength")
    public int getIndexlength() {
        return indexlength;
    }

    public void setIndexlength(int indexlength) {
        this.indexlength = indexlength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SdictionaryEntity entity = (SdictionaryEntity) o;

        if (indexlength != entity.indexlength) return false;
        if (field != null ? !field.equals(entity.field) : entity.field != null) return false;
        if (table != null ? !table.equals(entity.table) : entity.table != null) return false;
        if (index != 0 ? index == entity.index : entity.index != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = field != null ? field.hashCode() : 0;
        result = 31 * result + (table != null ? table.hashCode() : 0);
        result = 31 * result + (index != 0 ? index : 0);
        result = 31 * result + indexlength;
        return result;
    }
}
