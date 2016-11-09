package com.repository.web.storage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Finderlo on 2016/11/9.
 */
public class StorageForm {

    private List<ItemForm> itemForms = new ArrayList<>();

    public List<ItemForm> getItemForms() {
        return itemForms;
    }

    public void setItemForms(List<ItemForm> itemForms) {
        this.itemForms = itemForms;
    }
}
