package com.repository.web.other;

import com.repository.dao.CategoryDao;
import com.repository.dao.CompanyDao;
import com.repository.model.SimpleRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.stream.Collectors;

import static com.repository.common.Constants.*;

/**
 * Created by Finderlo on 2016/12/7.
 */
@Controller
public class  Add {

    @Autowired
    CategoryDao categoryDao;
    @Autowired
    CompanyDao companyDao;

    /**
     * 增加新的分类
     *
     * @param category isA type
     * @param name     分类名称
     * @param session
     * @return 成功或者失败
     */
    @RequestMapping(URL_ADD_NEW_CATEGORY)
    @ResponseBody
    public SimpleRes addcate(
            @RequestParam(name = "itemCategory") String category,
            @RequestParam(name = "categoryName") String name,
            HttpSession session) {
        boolean isSuccess = categoryDao.addCategory(category.equals("A类") ? true : false, name);
        if (isSuccess) {
            session.setAttribute(SESSION_CATEGORIES, categoryDao.findAll());
            session.setAttribute(SESSION_CATEGORIES_A,
                    categoryDao.findAll()
                            .stream()
                            .filter(entity -> entity.getCategoryName().endsWith("A"))
                            .collect(Collectors.toList()));
        }
        return isSuccess ? SimpleRes.success() : SimpleRes.error();
    }

    /**
     * 增加新的公司
     *
     * @param companyName    厂家名称
     * @param companyAddress 厂家地址
     * @param comanyPhone    厂家电话
     * @param session
     * @return 成功或者失败
     */
    @RequestMapping(URL_ADD_NEW_COMPANY)
    @ResponseBody
    public SimpleRes addcompany(
            @RequestParam(name = "companyName") String companyName,
            @RequestParam(name = "companyAddress") String companyAddress,
            @RequestParam(name = "companyPhone") String comanyPhone,
            HttpSession session) {
        boolean isSuccess = companyDao.addCompany(companyName, companyAddress, comanyPhone);
        if (isSuccess) {
            session.setAttribute(SESSION_COMPANIES, companyDao.findAll());
        }
        return isSuccess ? SimpleRes.success() : SimpleRes.error();
    }
}
