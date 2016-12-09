package com.repository.web.manage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.repository.Constants.*;

/**
 * Created by Finderlo on 2016/12/8.
 */
@Controller
public class Manage {

    /**
     * 管理主页 返回view
     * @return view
     */
    @RequestMapping(URL_MANAGE_EXAMEINE)
    public String manage() {
        return TILES_PREFIX + HTML_MANAGE_EXAMINE;
    }

}


