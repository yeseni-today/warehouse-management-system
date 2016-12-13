package com.repository.web.apply;

import com.repository.base.BaseController;
import com.repository.dao.ItemApplicationDao;
import com.repository.dao.ItemApplicationOperationDao;
import com.repository.entity.ItemApplicationOperationEntity;

import com.repository.model.SimpleRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.repository.common.Constants.*;

@Controller
public class ApplyController extends BaseController {

    @Autowired
    ItemApplicationDao applicationDao;

    @Autowired
    ItemApplicationOperationDao applicationOperationDao;

    /**
     * 申请历史
     *
     * @return html view
     */
    @RequestMapping(URL_APPLY)
    public String apply(Model model) {
        logger.trace("apply/");
        List<ItemApplicationOperationEntity> entities = applicationOperationDao.findAll();
        reverse(entities);
        model.addAttribute("history", entities);
        logger.info("apply: applications size:" + entities.size());
        entities.forEach(entity -> logger.info("apply: id:" + entity.getApplicationId()));
        return TILES_PREFIX + HTML_APPLY_HISTORY;
    }
    /**
     * 获取申请单列表，
     *@param day 最近多少天的列表
     * @return SimpleRes
     */
    @RequestMapping(URL_APPLY_LIST_AJAX)
    @ResponseBody
    public SimpleRes applyday(@RequestParam(name = "day") int day) {
        // TODO: 2016/12/9 参数比较多，只写了最近实践的参数
        List<ItemApplicationOperationEntity> entities = applicationOperationDao.findByDayBefore(day);
        reverse(entities);
        return SimpleRes.success(entities);
    }

    public void reverse(List<ItemApplicationOperationEntity> list) {
        //先排序
        list.sort(Comparator.comparingLong(e -> e.getApplicationTime().getTime()));
        //降序排列
        Collections.reverse(list);
    }

    /**
     * 申请历史 ajax
     *
     * @return div view
     */
    @RequestMapping(URL_APPLY_AJAX)
    public String applyajax(Model model) {
        logger.trace("apply/");
        List<ItemApplicationOperationEntity> entities = applicationOperationDao.findAll();
        model.addAttribute("history", entities);
        logger.info("apply: applications size:" + entities.size());
        entities.forEach(entity -> logger.info("apply: id:" + entity.getApplicationId()));
        return HTML_APPLY_HISTORY.concat(" ::content");
    }
}
