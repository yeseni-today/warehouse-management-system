package com.repository.web.storage.add;

/**
 * Created by Finderlo on 2016/11/9.
 */
public class ItemForm {

    private String itemCode;// 物品条形码);
    private String itemName;// 物品名称);
    private String itemCount;// 物品数量);
    private String itemSpec;// 物品规格);
    private String itemPrice;// 物品单价（人民币 元）);
    private String itemIntroduce;// 物品介绍);
    private String itemCompanyID;//物品生产厂商ID);
    private String itemCategoryID;//物品分类ID);
    private String itemBorrowTimeLimit;// 物品允许借取最大时间);
    private String itemState;// 物品状态);
    private String itemExamine;//物品审核信息);

    private String billCode;//物品发票代码
    private boolean isInschool;//是否是校内编码

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

    public String getItemCount() {
        return itemCount;
    }

    public void setItemCount(String itemCount) {
        this.itemCount = itemCount;
    }

    public String getItemSpec() {
        return itemSpec;
    }

    public void setItemSpec(String itemSpec) {
        this.itemSpec = itemSpec;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
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
}
