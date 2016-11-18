package com.repository.web.apply.add;

import com.repository.base.BaseController;
import com.repository.dao.ItemDao;
import com.repository.entity.ItemEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import static com.repository.Constants.*;

/**
 * Created by Finderlo on 2016/11/15.
 */
@Controller
public class AddapplyController extends BaseController {


    @Autowired
    ItemDao itemDao;

    @ModelAttribute
    public void storageForm(HttpSession session) {
        ApplyForm applyForm = (ApplyForm) session.getAttribute(SESSION_APPLY_FORM);
        if (applyForm == null) {
            session.setAttribute(SESSION_APPLY_FORM, new ApplyForm());
        }
        logger.info("123");
    }

    @RequestMapping(URL_APPLY_ADD)
    public String apply() {
        return HTML_APPLY_ADD_APPLYLIST;
    }

    @RequestMapping(value = URL_APPLY_ADD_ADDITEM, method = RequestMethod.GET)
    public String getaddItem() {
        return HTML_APPLY_ADD_ADDITEM;
    }

    @RequestMapping(URL_APPLY_ADD_QUERY_ITEM)
    public String queryItem(@RequestParam(name = "itemCode", required = false, defaultValue = "") String itemCode,
                            @RequestParam(name = "itemName", required = false, defaultValue = "") String itemName,
                            @RequestParam(name = "itemCategoryId", required = false, defaultValue = "") String itemCategoryId,
                            Model model) {
        List<ItemEntity> result = itemDao.query(new String[]{"itemCode", "itemName"}
                , new String[]{itemCode, itemName});
        result.addAll(itemDao.queryByCategoryId(itemCategoryId));
        model.addAttribute("items", result);
        return HTML_APPLY_ADD_ADDITEM;
    }

    //itemCodes
    @RequestMapping(URL_APPLY_ADD_ADDITEMS)
    public String additems(
            @RequestParam(name = "itemCodes", required = false) String[] itemCodes,
            HttpSession session) {
        ApplyForm applyForm = getApplyForm(session);
        for (String code : itemCodes) {
            applyForm.getItems().add(new ApplyItem(itemDao.findById(code)));
        }
        logger.info("additems");
        return REDIRECT + URL_APPLY_ADD;
    }

    public ApplyForm getApplyForm(HttpSession session) {
        ApplyForm applyForm = (ApplyForm) session.getAttribute(SESSION_APPLY_FORM);
        if (applyForm == null) {
            applyForm = new ApplyForm();
        }
        return applyForm;
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
        private String itemCategroyId;
        private int itemCount;
        private String others;

        public ApplyItem() {
        }

        public ApplyItem(ItemEntity itemEntity) {
            this.itemCode = itemEntity.getItemCode();
            this.itemName = itemEntity.getItemName();
            this.itemCategroyId = itemEntity.getItemCategoryEntity().getCategoryId();
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

        public String getItemCategroyId() {
            return itemCategroyId;
        }

        public void setItemCategroyId(String itemCategroyId) {
            this.itemCategroyId = itemCategroyId;
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
