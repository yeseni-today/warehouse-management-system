package com.repository.base;

/**
 * Created by Finderlo on 11/17/2016.
 */

import com.repository.dao.ItemCategoryDao;
import com.repository.dao.ItemCompanyDao;
import com.repository.dao.ItemDao;
import com.repository.dao.UmessageDao;
import com.repository.dao.UsersDao;
import com.repository.service.LogSerivce;

import org.springframework.beans.factory.annotation.Autowired;

public class BaseController extends BaseObject {
    @Autowired
    protected ItemDao itemDao;
    @Autowired
    protected ItemCategoryDao itemCategoryDao;
    @Autowired
    protected UsersDao usersDao;

    @Autowired
    protected ItemCompanyDao itemCompanyDao;

    @Autowired
    protected LogSerivce logSerivce;

    @Autowired
    protected UmessageDao messageDao;
}
