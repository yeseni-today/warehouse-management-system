package com.repository.web.apply.add;

import com.google.gson.Gson;
import com.repository.entity.ItemEntity;

import java.util.Collections;

/**
 * Created by Finderlo on 2016/11/26.
 */

public class ApplyItemForm {
    private String itemCode;
    private String itemName;
    private String applyType;
    private int itemCount;
    private String others;

    public ApplyItemForm() {
    }


    public ApplyItemForm(ItemEntity itemEntity) {
        this.itemCode = itemEntity.getItemCode();
        this.itemName = itemEntity.getItemName();
        this.applyType = itemEntity.getCategoryEntity().getCategoryId();
        this.itemCount = itemEntity.getItemCount();
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}