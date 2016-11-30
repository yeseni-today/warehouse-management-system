package com.repository.web.storage.add;

import com.repository.base.BaseController;
import com.repository.dao.ItemDao;
import com.repository.dao.ItemInOperationDao;
import com.repository.dao.SdictionaryDao;
import com.repository.entity.ItemEntity;
import com.repository.model.SimpleResponse;
import com.repository.service.StorageFormService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import static com.repository.Constants.HTML_STORAGE_ADD_ADDITEM;
import static com.repository.Constants.HTML_STORAGE_ADD_GETINFO;
import static com.repository.Constants.HTML_STORAGE_ADD_SETINFO;
import static com.repository.Constants.HTML_STORAGE_ADD_STORAGE_FORM;
import static com.repository.Constants.REDIRECT;
import static com.repository.Constants.SESSION_STORAGE_FORM;
import static com.repository.Constants.TILES_PREFIX;
import static com.repository.Constants.URL_STORAGE;
import static com.repository.Constants.URL_STORAGE_ADD;
import static com.repository.Constants.URL_STORAGE_ADD_ADDITEM;
import static com.repository.Constants.URL_STORAGE_ADD_ADDITEM_AJAX;
import static com.repository.Constants.URL_STORAGE_ADD_AJAX;
import static com.repository.Constants.URL_STORAGE_ADD_DELETEALL_AJAX;
import static com.repository.Constants.URL_STORAGE_ADD_DELETEITEM_AJAX;
import static com.repository.Constants.URL_STORAGE_ADD_SET_ITEM_INFO;
import static com.repository.Constants.URL_STORAGE_ADD_SET_ITEM_INFO_AJAX;
import static com.repository.Constants.URL_STORAGE_ADD_SUBMIT;
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

    @RequestMapping(URL_STORAGE_ADD_DELETEITEM_AJAX)
    @ResponseBody
    public SimpleResponse deleteItem(HttpSession session, @RequestParam("itemCode") String itemcode) {
        StorageForm storageForm = (StorageForm) session.getAttribute(SESSION_STORAGE_FORM);
        List one = storageForm.getItemForms().stream().filter(e -> e.getItemCode().equals(itemcode.trim())).collect(Collectors.toList());
        if (!one.isEmpty()) {
            storageForm.getItemForms().remove(one.get(0));
        }
        return new SimpleResponse();
    }

    @RequestMapping(URL_STORAGE_ADD_DELETEALL_AJAX)
    @ResponseBody
    public SimpleResponse deleteItem(HttpSession session) {
        StorageForm storageForm = (StorageForm) session.getAttribute(SESSION_STORAGE_FORM);
        storageForm.getItemForms().clear();
        return new SimpleResponse();

    }

    /**
     * 用来返回当前入库单列表视图
     */
    @RequestMapping(URL_STORAGE_ADD)
    public String addNew() {
        return TILES_PREFIX + HTML_STORAGE_ADD_STORAGE_FORM;
    }

    @RequestMapping(URL_STORAGE_ADD_AJAX)
    public String addNewajax() {
        return HTML_STORAGE_ADD_STORAGE_FORM.concat(" :: content");
    }

    /**
     * 返回入库单中增加物品的页面
     */
    @RequestMapping(value = URL_STORAGE_ADD_ADDITEM, method = RequestMethod.GET)
    public String addItem() {
        return TILES_PREFIX + HTML_STORAGE_ADD_ADDITEM;
    }

    @RequestMapping(value = URL_STORAGE_ADD_ADDITEM_AJAX, method = RequestMethod.GET)
    public String addItemajax() {
        return HTML_STORAGE_ADD_ADDITEM + " :: content";
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

    @Autowired
    StorageFormService service;

    /**
     * 2016/11/24 递交储存于session的入库单
     **/
    @RequestMapping(value = URL_STORAGE_ADD_SUBMIT, method = RequestMethod.GET)
    public String submitStorageForm(Principal principal, HttpSession session) {
        try {
            service.save(principal, storageForm);
            session.setAttribute(SESSION_STORAGE_FORM, null);
            return REDIRECT + URL_STORAGE;
        } catch (Exception e) {
            e.printStackTrace();
            return REDIRECT + URL_STORAGE_ADD;
        }
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
                model.addAttribute("isInschool", 0);
                return TILES_PREFIX + HTML_STORAGE_ADD_SETINFO;
            } else {
                model.addAttribute("item", itemEntity);
                return TILES_PREFIX + HTML_STORAGE_ADD_GETINFO;
            }
        } else {
            model.addAttribute("isInschool", 1);
            return TILES_PREFIX + HTML_STORAGE_ADD_SETINFO;
        }
    }

    @RequestMapping(URL_STORAGE_ADD_SET_ITEM_INFO_AJAX)
    public String setItemInfoajax(
            @RequestParam(name = "isInschool") boolean isInschool,
            @RequestParam(name = "itemCode", required = false) String itemCode,
            Model model) {
        return setItemInfo(isInschool, itemCode, model).substring(6).concat(" :: content");
    }

    @RequestMapping("/test")
    public String test() {
        return "storage/add/storage_form :: content";
    }
}
