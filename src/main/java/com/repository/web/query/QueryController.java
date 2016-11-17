package com.repository.web.query;

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


@Controller
public class QueryController {

    @Autowired
    ItemDao itemDao;
    @Autowired
    ItemCategoryDao categoryDao;

    @ModelAttribute
    public void init(HttpSession session) {
        session.setAttribute("categories", categories());
    }

    @RequestMapping("/queryItem")
    public String queryItem(@RequestParam(name = "itemCode", required = false, defaultValue = "") String itemCode,
                            @RequestParam(name = "itemName", required = false, defaultValue = "") String itemName,
                            @RequestParam(name = "itemCategoryId", required = false, defaultValue = "") String itemCategoryId,
                            Model model) {
        List<ItemEntity> result = itemDao.query(new String[]{"itemCode", "itemName"}
                , new String[]{itemCode, itemName});
        result.addAll(itemDao.queryByCategoryId(itemCategoryId));
        model.addAttribute("items", result);
        return "tiles/query/list";
    }



    @RequestMapping("/query")
    public String queryTo(Model model) {
        model.addAttribute("items", itemDao.findAll());
        return "tiles/query/list";
    }

    public List<ItemCategoryEntity> categories() {
        return categoryDao.findAll();
    }


    @RequestMapping("/queryItemRes")
    @ResponseBody
    public List<ItemEntity> queryItemPres(@RequestParam(name = "itemCode", required = false, defaultValue = "") String itemCode,
                                          @RequestParam(name = "itemName", required = false, defaultValue = "") String itemName,
                                          @RequestParam(name = "itemCategoryId", required = false, defaultValue = "") String itemCategoryId,
                                          Model model) {
//        List<ItemEntity> result = itemDao.query(new String[]{"itemCode", "itemName"}
//                , new String[]{itemCode, itemName});
//        result.addAll(itemDao.queryByCategoryId(itemCategoryId));
        List<ItemEntity> result = itemDao.findAll();
        System.out.println("success");
        return result;
    }


}
