package com.repository.web.query;

import com.repository.base.BaseController;
import com.repository.entity.ItemCategoryEntity;
import com.repository.entity.ItemEntity;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import static com.repository.Constants.HTML_QUERY_LIST;
import static com.repository.Constants.SESSION_COMPANY;
import static com.repository.Constants.SESSION_ITEMCATOARY;
import static com.repository.Constants.SESSION_ITEMCATOARY_A;
import static com.repository.Constants.URL_QUERY;
import static com.repository.Constants.URL_QUERY_QUERYITEM;

@RequestMapping(URL_QUERY)
@Controller
public class QueryController extends BaseController {

    @ModelAttribute
    public void init(HttpSession session) {
        session.setAttribute(SESSION_ITEMCATOARY, categories());
        session.setAttribute(SESSION_ITEMCATOARY_A,categoriesA());
        session.setAttribute(SESSION_COMPANY,itemCompanyDao.findAll());
    }



    @RequestMapping()
    public String queryTo(Model model) {
        return HTML_QUERY_LIST;
    }

    @RequestMapping("/hello")
    @ResponseBody
    public List<ItemEntity> queryTo1(Model model) {
        return new ArrayList<>();
    }

    @RequestMapping(URL_QUERY_QUERYITEM)
    @ResponseBody
    public List<ItemEntity> queryItem(@RequestParam(name = "itemCode", required = false, defaultValue = "") String itemCode,
                            @RequestParam(name = "itemName", required = false, defaultValue = "") String itemName,
                            @RequestParam(name = "itemCategoryId", required = false, defaultValue = "") String itemCategoryId,
                            ModelMap model,
                            HttpRequest request) {
        model.clear();
        List<ItemEntity> result = itemDao.query(new String[]{"itemCode", "itemName"}
                , new String[]{itemCode, itemName});
        result.addAll(itemDao.queryByCategoryId(itemCategoryId));
        model.addAttribute("items", result);
        logger.info("itemCode:" + itemCode);
        logger.info("itemName:" + itemName);
        logger.info("itemCategoryId:" + itemCategoryId);
        logger.info(result.size());
        if (result == null) {
            result = new ArrayList<>();
        }
        return result;
    }


    public List<ItemCategoryEntity> categories() {
        return itemCategoryDao.findAll();
    }

    private List<ItemCategoryEntity> categoriesA() {
        return itemCategoryDao.findAll().stream().filter(entity -> entity.getCategoryName().endsWith("A")).collect(Collectors.toList());
    }
}
