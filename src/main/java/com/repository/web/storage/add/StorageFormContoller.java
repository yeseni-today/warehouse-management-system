package com.repository.web.storage.add;

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

import javax.print.DocFlavor;
import javax.servlet.http.HttpSession;

import static com.repository.Constants.*;

/**
 * Created by Finderlo on 2016/11/9.
 */
@Controller
public class StorageFormContoller {

    @Autowired
    private ItemDao itemDao;
    @Autowired
    private SdictionaryDao sdictionaryDao;

    @ModelAttribute
    public void storageForm(HttpSession session) {
        StorageForm storageForm = (StorageForm) session.getAttribute(SESSION_STORAGE_FORM);
        if (storageForm == null) {
            session.setAttribute(SESSION_STORAGE_FORM, new StorageForm(sdictionaryDao.getStorageEntity()));
        }
    }

    @RequestMapping(URL_STORAGE_ADD)
    public String addNew() {
        return HTML_STORAGE_ADD_STORAGE_FORM;
    }

    @RequestMapping(value = URL_STORAGE_ADD_ADDITEM, method = RequestMethod.GET)
    public String addItem() {
        return HTML_STORAGE_ADD_ADDITEM;
    }


    @RequestMapping(value = URL_STORAGE_ADD_ADDITEM, method = RequestMethod.POST)
    public String addItemTosession(ItemForm itemForm,
                                   @RequestParam(name = "isInschool", required = false) boolean isInschool,
                                   HttpSession session) {
        itemForm.setInschool(isInschool);

        StorageForm storageForm = (StorageForm) session.getAttribute(SESSION_STORAGE_FORM);
        storageForm.getItemForms().add(itemForm);
        session.setAttribute(SESSION_STORAGE_FORM, storageForm);
        return HTML_STORAGE_ADD_STORAGE_FORM;
    }

    /**
     * isInschool false，为自带编码，如果仓库中有，只需要更改数量。没有的话，则仍然需要输入信息.
     * true，为校内编码，需要自己输入相关信息.
     * 暂时没写：如果自带编码但是仓库中没有的物品的页面。现在，如果没有则重新返回.
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
