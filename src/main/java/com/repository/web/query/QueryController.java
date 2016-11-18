package com.repository.web.query;

import com.repository.base.BaseController;
import com.repository.entity.ItemCategoryEntity;
import com.repository.entity.ItemEntity;
import com.repository.dao.ItemCategoryDao;
import com.repository.dao.ItemDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import javax.servlet.http.HttpSession;

import static com.repository.Constants.*;

@RequestMapping(URL_QUERY)
@Controller
public class QueryController extends BaseController {

    @Autowired
    ItemDao itemDao;
    @Autowired
    ItemCategoryDao categoryDao;


    @ModelAttribute
    public void init(HttpSession session) {
        session.setAttribute("categories", categories());
    }

    @RequestMapping()
    public String queryTo(Model model) {
        model.addAttribute("items", itemDao.findAll());
        return HTML_QUERY_LIST;
    }

    @RequestMapping(URL_QUERY_QUERYITEM)
    public String queryItem(@RequestParam(name = "itemCode", required = false, defaultValue = "") String itemCode,
                            @RequestParam(name = "itemName", required = false, defaultValue = "") String itemName,
                            @RequestParam(name = "itemCategoryId", required = false, defaultValue = "") String itemCategoryId,
                            Model model) {
        List<ItemEntity> result = itemDao.query(new String[]{"itemCode", "itemName"}
                , new String[]{itemCode, itemName});
        result.addAll(itemDao.queryByCategoryId(itemCategoryId));
        model.addAttribute("items", result);
        return HTML_QUERY_LIST;
    }


    public List<ItemCategoryEntity> categories() {
        return categoryDao.findAll();
    }


}
