package com.repository.web.storage.add;

import com.google.gson.Gson;

/**
 * Created by Finderlo on 2016/11/9.
 */
public class ItemForm {

    private String itemCode;// 物品条形码);
    private String itemName;// 物品名称);
    private int itemCount;// 物品数量);
    private String itemSpec;// 物品规格);ui
    private double itemPrice;// 物品单价（人民币 元）);
    private String itemIntroduce;// 物品介绍);
    private String itemCompanyID;//物品生产厂商ID);
    private String itemCompanyPhone;//物品生产厂商联系方式);
    private String itemCategoryID;//物品分类ID);

    private String itemBorrowTimeLimit;// 物品允许借取最大时间);
    private String itemState;// 物品状态);
    private String itemExamine;//物品审核信息);

    private String billCode;//物品发票代码
    private boolean isInschool;//是否是校内编码
    private String itemBatch;//物品批次
    private String itemSlot;//物品库位

    private String itemCategoryName;//物品分类name);
    private String itemOrignCount;//物品分类name);
    private String itemCompanyName;//物品companyname);


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

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public String getItemSpec() {
        return itemSpec;
    }

    public void setItemSpec(String itemSpec) {
        this.itemSpec = itemSpec;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemIntroduce() {
        return itemIntroduce;
    }

    public void setItemIntroduce(String itemIntroduce) {
        this.itemIntroduce = itemIntroduce;
    }

    public String getItemCompanyID() {
        return itemCompanyID;
    }

    public void setItemCompanyID(String itemCompanyID) {
        this.itemCompanyID = itemCompanyID;
    }

    public String getItemCategoryID() {
        return itemCategoryID;
    }

    public void setItemCategoryID(String itemCategoryID) {
        this.itemCategoryID = itemCategoryID;
    }

    public String getItemBorrowTimeLimit() {
        return itemBorrowTimeLimit;
    }

    public void setItemBorrowTimeLimit(String itemBorrowTimeLimit) {
        this.itemBorrowTimeLimit = itemBorrowTimeLimit;
    }

    public String getItemState() {
        return itemState;
    }

    public void setItemState(String itemState) {
        this.itemState = itemState;
    }

    public String getItemExamine() {
        return itemExamine;
    }

    public void setItemExamine(String itemExamine) {
        this.itemExamine = itemExamine;
    }

    public boolean isInschool() {
        return isInschool;
    }

    public void setInschool(boolean inschool) {
        isInschool = inschool;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public String getItemCompanyPhone() {
        return itemCompanyPhone;
    }

    public void setItemCompanyPhone(String itemCompanyPhone) {
        this.itemCompanyPhone = itemCompanyPhone;
    }

    public String getItemBatch() {
        return itemBatch;
    }

    public void setItemBatch(String itemBatch) {
        this.itemBatch = itemBatch;
    }

    public String getItemSlot() {
        return itemSlot;
    }

    public void setItemSlot(String itemSlot) {
        this.itemSlot = itemSlot;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public String getItemCategoryName() {
        return itemCategoryName;
    }

    public void setItemCategoryName(String itemCategoryName) {
        this.itemCategoryName = itemCategoryName;
    }

    public String getItemOrignCount() {
        return itemOrignCount;
    }

    public void setItemOrignCount(String itemOrignCount) {
        this.itemOrignCount = itemOrignCount;
    }

    public String getItemCompanyName() {
        return itemCompanyName;
    }

    public void setItemCompanyName(String itemCompanyName) {
        this.itemCompanyName = itemCompanyName;
    }
}
