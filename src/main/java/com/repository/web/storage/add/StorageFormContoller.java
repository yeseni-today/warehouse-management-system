package com.repository.web.storage.add;

import com.repository.base.BaseController;
import com.repository.dao.ItemInOperationDao;
import com.repository.dao.SdictionaryDao;
import com.repository.entity.ItemEntity;
import com.repository.dao.ItemDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

import javax.print.DocFlavor;
import javax.servlet.http.HttpSession;

import static com.repository.Constants.*;
import static com.repository.util.Util.*;
/**
 * Created by Finderlo on 2016/11/9.
 */
@Controller
public class StorageFormContoller extends BaseController {

    @Autowired
    private ItemDao itemDao;
    @Autowired
    private SdictionaryDao sdictionaryDao;
    @Autowired
    private ItemInOperationDao itemInOperationDao;

    StorageForm storageForm;

    @ModelAttribute
    public void storageForm(HttpSession session, Principal principal) {
        storageForm = (StorageForm) session.getAttribute(SESSION_STORAGE_FORM);
        if (storageForm == null) {

            storageForm = new StorageForm(sdictionaryDao.getInstorgeId(), principal.getName());
            session.setAttribute(SESSION_STORAGE_FORM, storageForm);
        }
    }

    /**
     * 用来返回当前入库单列表视图
     */
    @RequestMapping(URL_STORAGE_ADD)
    public String addNew() {
        return HTML_STORAGE_ADD_STORAGE_FORM;
    }

    /**
     * 返回入库单中增加物品的页面
     */
    @RequestMapping(value = URL_STORAGE_ADD_ADDITEM, method = RequestMethod.GET)
    public String addItem() {
        return HTML_STORAGE_ADD_ADDITEM;
    }

    /**
     * 增加一个item到入库单中，成功后重定向到当前入库单列表视图
     */
    @RequestMapping(value = URL_STORAGE_ADD_ADDITEM, method = RequestMethod.POST)
    public String addItemTosession(ItemForm itemForm,
                                   @RequestParam(name = "isInschool", required = false) boolean isInschool,
                                   HttpSession session) {
        itemForm.setInschool(isInschool);
        if (itemForm.isInschool()) {
            itemForm.setItemCode(sdictionaryDao.getInSchoolId(itemForm.getItemCategoryID()));
        }
        storageForm.getItemForms().add(itemForm);
        session.setAttribute(SESSION_STORAGE_FORM, storageForm);
        return REDIRECT + URL_STORAGE_ADD;
    }

    @RequestMapping(value = URL_STORAGE_ADD_SUBMIT, method = RequestMethod.POST)
    public String submitStorageForm() {

//        itemInOperationDao.save(storageForm,principal.getName());
        return REDIRECT + URL_STORAGE;
    }

    /**
     * 用户输入表格
     *
     * @param isInschool false，为自带编码，如果仓库中有，只需要更改数量。没有的话，则仍然需要输入信息.
     *                   true，为校内编码，需要自己输入相关信息.
     */
    @RequestMapping(URL_STORAGE_ADD_SET_ITEM_INFO)
    public String setItemInfo(
            @RequestParam(name = "isInschool") boolean isInschool,
            @RequestParam(name = "itemCode", required = false) String itemCode,
            Model model) {
        if (!isInschool) {
            ItemEntity itemEntity = itemDao.findById(itemCode);
            if (itemEntity == null) {
                return HTML_STORAGE_ADD_ADDITEM;
            } else {
                model.addAttribute("item", itemEntity);
                return HTML_STORAGE_ADD_GETINFO;
            }
        } else {
            return HTML_STORAGE_ADD_SETINFO;
        }
    }
}
