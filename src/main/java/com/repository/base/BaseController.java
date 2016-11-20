package com.repository.base;

/**
 * Created by Finderlo on 11/17/2016.
 */

import com.repository.dao.ItemCategoryDao;
import com.repository.dao.ItemDao;
import com.repository.dao.UsersDao;

import org.springframework.beans.factory.annotation.Autowired;

import static com.repository.util.Util.*;
import static com.repository.Constants.*;

public class BaseController extends BaseObject {
    @Autowired
    protected ItemDao itemDao;
    @Autowired
    protected ItemCategoryDao itemCategoryDao;
    @Autowired
    protected UsersDao usersDao;
}
