package com.repository.web.query;

import com.google.gson.Gson;
import com.repository.base.BaseController;
import com.repository.dao.ItemInStorageDao;
import com.repository.entity.CategoryEntity;
import com.repository.entity.ItemEntity;
import com.repository.entity.ItemInStorageEntity;
import com.repository.model.SimpleRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.repository.common.Constants.*;


@Controller
public class QueryController extends BaseController {

    @ModelAttribute
    public void init(HttpSession session) {
        session.setAttribute(SESSION_CATEGORIES, categories());
        session.setAttribute(SESSION_CATEGORIES_A, categoriesA());
        session.setAttribute(SESSION_COMPANIES, companyDao.findAll());
        logger.trace(categories());
        logger.trace(categoriesA());
    }

    @Autowired
    private ItemInStorageDao itemInStorageDao;

    /**
     * 通过物品id来查
     */
    @RequestMapping(URL_QUERY_ITEMINFO)
    @ResponseBody
    public SimpleRes queryItem(@RequestParam("itemCode") String itemCode) {
        if (itemCode == null || itemCode.trim().equals("")) {
            return SimpleRes.error("物品编码为空");
        }


        Item item = new Item();
        item.itemEntity = itemDao.findById(itemCode);

        List<ItemInStorageEntity> storages = itemInStorageDao.query("itemCode", itemCode, false);
        storages.sort(Comparator.comparingLong(e -> e.getItemIndate().getTime()));
        if (storages.size() > 0) {
            item.slot = storages.get(0).getItemSlot();
        } else item.slot = "没有库位信息";
        System.out.println(item.toString());
        return SimpleRes.success(item);
    }

    static class Item {
        public ItemEntity itemEntity;
        public String slot;

        @Override
        public String toString() {
            return new Gson().toJson(this);
        }
    }


    /**
     * query
     *
     * @return tiles html view
     */
    @RequestMapping(URL_QUERY)
    public String queryTo() {
        logger.info("query");
        System.out.println(TILES_PREFIX + HTML_QUERY_LIST);
        return TILES_PREFIX + HTML_QUERY_LIST;
    }

    @RequestMapping("/hello")
    @ResponseBody
    public List<ItemEntity> queryTo1(Model model) {
        return new ArrayList<>();
    }

    /**
     * 查询物品
     *
     * @param principal .
     * @param itemCode  .
     * @param itemName  .
     * @param model     .
     * @return list
     */
    @RequestMapping(URL_QUERY_QUERYITEM)
    @ResponseBody
    public List<ItemEntity> queryItem(
            @RequestParam(name = "itemCode", required = false, defaultValue = "") String itemCode,
            @RequestParam(name = "itemName", required = false, defaultValue = "") String itemName,
            @RequestParam(name = "itemCategoryId", required = false, defaultValue = "") String itemCategoryId,
            ModelMap model,
            Principal principal) {
        model.clear();
        List<ItemEntity> result = itemDao.query(new String[]{"itemCode", "itemName"}
                , new String[]{itemCode, itemName});
        result.addAll(itemDao.queryByCategoryId(itemCategoryId));
        model.addAttribute("items", result);
        if (result == null) {
            result = new ArrayList<>();
        }

        if (!itemCode.trim().equals("")) {
            logSerivce.queryItem(principal.getName(), itemCode);
        }
        if (!itemName.trim().equals("")) {
            logSerivce.queryItem(principal.getName(), itemName);
        }
        if (!itemCategoryId.trim().equals("")) {
            logSerivce.queryItem(principal.getName(), itemCategoryId);
        }

        return result;
    }


    public List<CategoryEntity> categories() {
        return categoryDao.findAll();
    }

    private List<CategoryEntity> categoriesA() {
        return categoryDao.findAll().stream().filter(entity -> entity.getCategoryName().endsWith("A")).collect(Collectors.toList());
    }
}
