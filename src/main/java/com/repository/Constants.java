package com.repository;

/**
 * Created by Finderlo on 11/17/2016.
 */
public interface Constants {
    String TILES_PREFIX = "tiles/";
    String REDIRECT = "redirect:";
    int ALLOW_COUNT = 5;
    //session
    String SESSION_STORAGE_FORM = "storageForm";
    String SESSION_APPLY_FORM = "apply_form";
    //login
    String URL_INDEX = "/";
    String SIGNIN = "/signin";
    String HTML_LOGIN_SIGNIN = TILES_PREFIX + "login/signin";

    //query
    String URL_QUERY = "/query";
    String URL_QUERY_QUERYITEM = "/queryItem";
    String HTML_QUERY_LIST = TILES_PREFIX + "query/list";

    //storage
    String URL_STORAGE = "/storage";
    String HTML_STORAGE_PREFIX = TILES_PREFIX + "storage/";
    String HTML_STORAGE_HISTORY = HTML_STORAGE_PREFIX + "history";
    //storage-add
    String URL_STORAGE_ADD = "/storage/add";
    String URL_STORAGE_ADD_ADDITEM = URL_STORAGE_ADD + "/additem";
    String URL_STORAGE_ADD_SET_ITEM_INFO = URL_STORAGE_ADD + "/set_item_info";
    String URL_STORAGE_ADD_SUBMIT = URL_STORAGE_ADD + "/submit";
    String HTML_STORAGE_ADD_PREFIX = HTML_STORAGE_PREFIX + "add/";
    String HTML_STORAGE_ADD_STORAGE_FORM = HTML_STORAGE_ADD_PREFIX + "storage_form";
    String HTML_STORAGE_ADD_ADDITEM = HTML_STORAGE_ADD_PREFIX + "additem";
    String HTML_STORAGE_ADD_GETINFO = HTML_STORAGE_ADD_PREFIX + "getinfo";
    String HTML_STORAGE_ADD_SETINFO = HTML_STORAGE_ADD_PREFIX + "setinfo";

    //apply
    String URL_APPLY = "/apply";
    String HTML_APPLY_PREFIX = TILES_PREFIX + "apply/";
    String HTML_APPLY_HISTORY = HTML_APPLY_PREFIX + "history";
    //APPLY ADD
    String URL_APPLY_ADD = URL_APPLY + "/add";
    String URL_APPLY_ADD_ADDITEM = URL_APPLY_ADD + "/additem";
    String URL_APPLY_ADD_ADDITEMS = URL_APPLY_ADD + "/additems";
    String URL_APPLY_ADD_QUERY_ITEM = URL_APPLY_ADD + "/queryItem";

    String HTML_APPLY_ADD_PREFIX = HTML_APPLY_PREFIX + "add/";
    String HTML_APPLY_ADD_APPLYFORM = HTML_APPLY_ADD_PREFIX + "apply_form";
    String HTML_APPLY_ADD_ADDITEM = HTML_APPLY_ADD_PREFIX + "additem";


    //borrow
    String URL_BORROW = "/borrow";
    String HTML_BORROW_PREFIX = TILES_PREFIX + "borrow/";
    String HTML_BORROW_HISTORY = HTML_BORROW_PREFIX + "history";


    //message
    String URL_MESSAGE = "/message";
    String HTML_MESSAGE_PREFIX = TILES_PREFIX + "message/";
    String HTML_MESSAGE_LIST = HTML_MESSAGE_PREFIX + "list";

    //log
    String URL_LOG = "/log";
    String HTML_LOG_PREFIX = TILES_PREFIX + "log/";
    String HTML_LOG_HISTORY = HTML_LOG_PREFIX + "history";

    //error
    String URL_ERROR_GENERAL = "/generalError";
    String HTML_ERROR_GENERAL = "error/general";
    String HTML_ERROR_404 = "/404error";
}
