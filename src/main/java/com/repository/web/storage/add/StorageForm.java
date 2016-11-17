package com.repository.web.storage.add;

import com.repository.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Finderlo on 2016/11/9.
 */
public class StorageForm {

    private String inStorageId = Util.getInstorgeId();

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
}
