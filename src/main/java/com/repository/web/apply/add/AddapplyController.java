package com.repository.web.apply.add;

import com.repository.base.BaseController;
import com.repository.dao.ItemDao;
import com.repository.dao.SdictionaryDao;
import com.repository.entity.ItemEntity;
import com.repository.model.MessageResponse;
import com.repository.model.SimpleResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import javax.servlet.http.HttpSession;

import static com.repository.Constants.HTML_APPLY_ADD_ADDITEM;
import static com.repository.Constants.HTML_APPLY_ADD_APPLYFORM;
import static com.repository.Constants.REDIRECT;
import static com.repository.Constants.SESSION_APPLY_FORM;
import static com.repository.Constants.TILES_PREFIX;
import static com.repository.Constants.URL_APPLY_ADD;
import static com.repository.Constants.URL_APPLY_ADD_ADDITEM;
import static com.repository.Constants.URL_APPLY_ADD_ADDITEMAJAX;
import static com.repository.Constants.URL_APPLY_ADD_ADDITEMS;
import static com.repository.Constants.URL_APPLY_ADD_AJAX;
import static com.repository.Constants.URL_APPLY_ADD_QUERY_ITEM;
import static com.repository.Constants.URL_APPLY_CLEARFORM_AJAX;

/**
 * Created by Finderlo on 2016/11/15.
 */
@Controller
public class AddapplyController extends BaseController {

    @Autowired
    ItemDao itemDao;

    @Autowired
    SdictionaryDao sdictionaryDao;


    @ModelAttribute
    public void applyForm(HttpSession session) {
        ApplyForm applyForm = (ApplyForm) session.getAttribute(SESSION_APPLY_FORM);
        if (applyForm == null) {
            session.setAttribute(SESSION_APPLY_FORM, new ApplyForm(sdictionaryDao.getApplicationId()));
        }
        logger.info("applyForm: " + applyForm);
    }

    @RequestMapping(URL_APPLY_ADD_AJAX)
    public String applyajax() {
        return HTML_APPLY_ADD_APPLYFORM.concat(" :: content");
    }


    @RequestMapping(URL_APPLY_ADD)
    public String apply() {
        return TILES_PREFIX + HTML_APPLY_ADD_APPLYFORM;
    }

    @RequestMapping(URL_APPLY_CLEARFORM_AJAX)
    @ResponseBody
    public MessageResponse clear(HttpSession session) {
        session.setAttribute(SESSION_APPLY_FORM, null);
        logger.info("clear: ");
        return MessageResponse.success();
    }

    @RequestMapping(value = URL_APPLY_ADD_ADDITEM, method = RequestMethod.GET)
    public String getaddItem() {
        return TILES_PREFIX + HTML_APPLY_ADD_ADDITEM;
    }

    @RequestMapping(value = URL_APPLY_ADD_ADDITEMAJAX, method = RequestMethod.GET)
    public String getaddItemajax() {
        return HTML_APPLY_ADD_ADDITEM.concat(" :: content");
    }

    @RequestMapping(value = URL_APPLY_ADD_ADDITEM, method = RequestMethod.POST)
    @ResponseBody
    public SimpleResponse getaddItemAjax(ApplyItemForm applyItemForm, HttpSession session) {
        ApplyForm applyForm = getApplyForm(session);
        applyForm.getItems().add(applyItemForm);
        logger.info("getaddItemAjax: " + applyItemForm);
        ;
        return new SimpleResponse();
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
        return TILES_PREFIX + HTML_APPLY_ADD_ADDITEM;
    }

    //itemCodes
    @RequestMapping(URL_APPLY_ADD_ADDITEMS)
    public String additems(
            @RequestParam(name = "itemCodes", required = false) String[] itemCodes,
            HttpSession session) {
        ApplyForm applyForm = getApplyForm(session);
        for (String code : itemCodes) {
            applyForm.getItems().add(new ApplyItemForm(itemDao.findById(code)));
        }
        logger.info("additems");
        return REDIRECT + URL_APPLY_ADD;
    }

    public ApplyForm getApplyForm(HttpSession session) {
        ApplyForm applyForm = (ApplyForm) session.getAttribute(SESSION_APPLY_FORM);
        return applyForm;
    }

}
