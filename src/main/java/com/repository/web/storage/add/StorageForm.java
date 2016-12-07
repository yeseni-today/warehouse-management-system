package com.repository.web.storage.add;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Finderlo on 2016/11/9.
 */
public class StorageForm {


    public StorageForm(String inStorageId, String opreationId) {
        this.inStorageId = inStorageId;
        this.opreationId = opreationId;
    }

    private String opreationId;

    private String inStorageId;

    private List<ItemForm> itemForms = new ArrayList<>();

    public List<ItemForm> getItemForms() {
        return itemForms;
    }

    public void setItemForms(List<ItemForm> itemForms) {
        this.itemForms = itemForms;
    }

    public String getInStorageId() {
        return inStorageId;
    }

    public void setInStorageId(String inStorageId) {
        this.inStorageId = inStorageId;
    }

    public String getOpreationId() {
        return opreationId;
    }

    public void setOpreationId(String opreationId) {
        this.opreationId = opreationId;
    }
}
