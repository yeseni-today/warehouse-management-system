package com.repository.base;

/**
 * Created by Finderlo on 11/17/2016.
 */

import com.repository.dao.CategoryDao;
import com.repository.dao.CompanyDao;
import com.repository.dao.ItemDao;
import com.repository.dao.MessageDao;
import com.repository.dao.UsersDao;
import com.repository.service.LogSerivce;

import org.springframework.beans.factory.annotation.Autowired;

public class BaseController extends BaseObject {
    @Autowired
    protected ItemDao itemDao;
    @Autowired
    protected CategoryDao categoryDao;
    @Autowired
    protected UsersDao usersDao;

    @Autowired
    protected CompanyDao companyDao;

    @Autowired
    protected LogSerivce logSerivce;

    @Autowired
    protected MessageDao messageDao;
}
