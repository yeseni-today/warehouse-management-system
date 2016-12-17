package com.repository.web.apply.add;

import com.repository.base.BaseController;
import com.repository.dao.DictionaryDao;
import com.repository.dao.ItemDao;
import com.repository.entity.ItemEntity;
import com.repository.model.SimpleRes;
import com.repository.service.ApplyFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

import static com.repository.common.Constants.*;


/**
 * Created by Finderlo on 2016/11/15.
 */
@Controller
public class AddapplyController extends BaseController {

    @Autowired
    ItemDao itemDao;

    @Autowired
    DictionaryDao dictionaryDao;


    @ModelAttribute
    public void applyForm(HttpSession session, Principal principal) {
        ApplyForm applyForm = (ApplyForm) session.getAttribute(SESSION_APPLY_FORM);
        if (applyForm == null) {
            session.setAttribute(SESSION_APPLY_FORM, new ApplyForm(dictionaryDao.getApplicationId(), principal.getName()));
        }
        logger.info("applyForm: " + applyForm);
    }

    /**
     * @return 返回添加申请单的div界面 ajax
     */
    @RequestMapping(URL_APPLY_ADD_AJAX)
    public String applyajax() {
        return HTML_APPLY_ADD_APPLYFORM.concat(" :: content");
    }


    /**
     * @return 返回添加申请单的view界面
     */
    @RequestMapping(URL_APPLY_ADD)
    public String apply() {
        return TILES_PREFIX + HTML_APPLY_ADD_APPLYFORM;
    }

    /**
     * @return 清空申请单 ajax
     */
    @RequestMapping(URL_APPLY_CLEARFORM_AJAX)
    @ResponseBody
    public SimpleRes clear(HttpSession session) {
        session.setAttribute(SESSION_APPLY_FORM, null);
        logger.info("clear: ");
        return SimpleRes.success();
    }

    /**
     * @return 删除申请单 itemCode 物品 ajax
     */
    @RequestMapping(URL_APPLY_ADD_DELETE_AJAX)
    @ResponseBody
    public SimpleRes delete(@RequestParam(name = "itemCode") String itemCode, HttpSession session) {
        ApplyForm applyForm = (ApplyForm) session.getAttribute(SESSION_APPLY_FORM);
        for (ApplyItemForm itemForm : applyForm.getItems()) {
            if (itemForm.getItemCode().equals(itemCode)) {
                applyForm.getItems().remove(itemForm);
                session.setAttribute(SESSION_APPLY_FORM, applyForm);
                return SimpleRes.success();
            }
        }
        return SimpleRes.error();
    }

    /**
     * @return 返回增加物品的view试图
     */
    @RequestMapping(value = URL_APPLY_ADD_ADDITEM, method = RequestMethod.GET)
    public String getaddItem() {
        return TILES_PREFIX + HTML_APPLY_ADD_ADDITEM;
    }

    /**
     * @return 返回增加物品的div视图，ajax
     */
    @RequestMapping(value = URL_APPLY_ADD_ADDITEMAJAX, method = RequestMethod.GET)
    public String getaddItemajax() {
        return HTML_APPLY_ADD_ADDITEM.concat(" :: content");
    }

    /**
     * 向申请单增加物品，ajax
     *
     * @param applyItemForm .
     */
    @RequestMapping(value = URL_APPLY_ADD_ADDITEM, method = RequestMethod.POST)
    @ResponseBody
    public SimpleRes getaddItemAjax(ApplyItemForm applyItemForm, HttpSession session) {
        if (applyItemForm == null){
            return SimpleRes.error();
        }
        ApplyForm applyForm = getApplyForm(session);
        boolean flag = false;
        for (ApplyItemForm e : applyForm.getItems()) {
            if (e.getItemCode().equals(applyItemForm.getItemCode())) {
                e.setItemCount(e.getItemCount() + applyItemForm.getItemCount());
                e.setOthers(applyItemForm.getOthers());
                flag = true;
                return SimpleRes.success();
            }
        }
        if (!flag){
            applyForm.getItems().add(applyItemForm);
            return SimpleRes.success();
        }
        logger.info("getaddItemAjax: " + applyItemForm);
        return SimpleRes.error();
    }

    /**
     * 查询物品
     *
     * @param itemCode       .
     * @param itemName       .
     * @param itemCategoryId .
     */
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

    @Autowired
    ApplyFormService service;

    /**
     * 2016/11/24 递交储存于session的申请单
     *
     * @param principal .
     * @param session   .
     * @return
     **/
    @RequestMapping(value = URL_APPLY_ADD_SUBMIT, method = RequestMethod.POST)
    @ResponseBody
    public SimpleRes submit(Principal principal, HttpSession session) {
        try {
            service.save(principal, getApplyForm(session));
            session.removeAttribute(SESSION_APPLY_FORM);
            session.setAttribute(SESSION_APPLY_FORM,null);
            return SimpleRes.success();
        } catch (Exception e) {
            e.printStackTrace();
            return SimpleRes.error(e.getMessage());
        }
    }

    /**
     * 向申请单增加多个物品，
     *
     * @param itemCodes
     * @return view
     */
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
