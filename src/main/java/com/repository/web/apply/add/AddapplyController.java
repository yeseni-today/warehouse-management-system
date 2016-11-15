package com.repository.web.apply.add;

import com.repository.web.storage.instroage.StorageFormBean;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

/**
 * Created by Finderlo on 2016/11/15.
 */
@Controller
@RequestMapping("/apply/add")
public class AddapplyController {

    private static final String APPLY_FORM = "apply_form";

    private static final String PREFIX = "tiles/apply/add/";

    @ModelAttribute
    public void storageForm(HttpSession session) {
        ApplyForm applyForm = (ApplyForm) session.getAttribute(APPLY_FORM);
        if (applyForm == null) {
            session.setAttribute(APPLY_FORM, new ApplyForm());
        }
    }

    @RequestMapping(value = {"/", ""})
    public String apply() {
        return PREFIX + "applyList";
    }

    @RequestMapping(value = "/additem", method = RequestMethod.GET)
    public String getaddItem() {
        return PREFIX + "addItem";
    }


    static class ApplyForm {
        private List<ApplyItem> items = new ArrayList();

        public List<ApplyItem> getItems() {
            return items;
        }

        public void setItems(List<ApplyItem> items) {
            this.items = items;
        }
    }

    private static class ApplyItem {
        private String itemCode;
        private String itemName;
        private String itemCategroy;
        private int itemCount;
        private String others;


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

        public String getItemCategroy() {
            return itemCategroy;
        }

        public void setItemCategroy(String itemCategroy) {
            this.itemCategroy = itemCategroy;
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
    }
}
