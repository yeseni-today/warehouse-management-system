package com.repository.web.log;

import com.repository.model.SimpleRes;
import com.repository.service.LogSerivce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.repository.common.Constants.*;

@Controller
public class LogController {

    @Autowired
    LogSerivce logSerivce;

    /**
     * 显示页
     *
     * @return view
     */
    @RequestMapping(URL_LOG)
    public String log() {
        return TILES_PREFIX + HTML_LOG_LOG;
    }

    /**
     * 返回系统日志 ajax
     *
     * @return simpleRes.content: log-list
     */
    @RequestMapping(URL_LOG_SYSTEM_AJAX)
    @ResponseBody
    public SimpleRes system() {
        return SimpleRes.success(logSerivce.findSystem());
    }

    /**
     * 返回入库日志 ajax
     *
     * @return simpleRes.content: log-list
     */
    @RequestMapping(URL_LOG_INSTORAGE_AJAX)
    @ResponseBody
    public SimpleRes findInstorage() {
        return SimpleRes.success(logSerivce.findInstorage());
    }

    /**
     * 返回出库日志 ajax
     *
     * @return simpleRes.content: log-list
     */
    @RequestMapping(URL_LOG_OUTSTORAGE_AJAX)
    @ResponseBody
    public SimpleRes findOutstorage() {
        return SimpleRes.success(logSerivce.findOutstorage());
    }

    /**
     * 返回申请日志 ajax
     *
     * @return simpleRes.content: log-list
     */
    @RequestMapping(URL_LOG_APPLY_AJAX)
    @ResponseBody
    public SimpleRes findApply() {
        return SimpleRes.success(logSerivce.findApply());
    }

    /**
     * 返回借用日志 ajax
     *
     * @return simpleRes.content: log-list
     */
    @RequestMapping(URL_LOG_BORROW_AJAX)
    @ResponseBody
    public SimpleRes findBorrow() {
        return SimpleRes.success(logSerivce.findBorrow());
    }

    /**
     * 返回维护日志 ajax
     *
     * @return simpleRes.content: log-list
     */
    @RequestMapping(URL_LOG_MAINTAIN_AJAX)
    @ResponseBody
    public SimpleRes findMaintain() {
        return SimpleRes.success(logSerivce.findMaintain());
    }


}
