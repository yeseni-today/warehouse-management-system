package com.repository.web.apply.add;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Finderlo on 2016/11/26.
 */
public class ApplyForm {
    private List<ApplyItemForm> items = new ArrayList();

    private String applicationId;

    public ApplyForm(String applicationId) {
        this.applicationId = applicationId;
    }

    public List<ApplyItemForm> getItems() {
        return items;
    }

    public void setItems(List<ApplyItemForm> items) {
        this.items = items;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}