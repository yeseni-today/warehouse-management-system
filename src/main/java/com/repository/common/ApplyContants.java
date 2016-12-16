package com.repository.common;

/**
 * Created by finderlo on 16-12-12.
 */
public interface ApplyContants {

    String APPLY_NEED_EXAMINE = "need";
    String APPLY_NONEED_EXAMINE = "pass";
    String APPLY_FAIL_EXAMINE = "fail";


    //apply
    String URL_APPLY = "/apply";
    String URL_APPLY_LIST_AJAX = URL_APPLY + "/listajax";
    String HTML_APPLY_PREFIX = "apply/";
    String HTML_APPLY_HISTORY = HTML_APPLY_PREFIX + "history";
    String URL_APPLY_INFO_JSON = URL_APPLY + "/applyinfo.json";
    String URL_APPLY_AJAX = "/applyajax";
    //APPLY ADD
    String URL_APPLY_ADD = URL_APPLY + "/add";
    String URL_APPLY_ADD_ADDITEM = URL_APPLY_ADD + "/additem";
    String URL_APPLY_ADD_ADDITEMS = URL_APPLY_ADD + "/additems";
    String URL_APPLY_ADD_QUERY_ITEM = URL_APPLY_ADD + "/queryItem";
    String URL_APPLY_ADD_SUBMIT = URL_APPLY_ADD + "/submit";

    String URL_APPLY_ADD_AJAX = URL_APPLY + "/addajax";
    String URL_APPLY_CLEARFORM_AJAX = URL_APPLY_ADD + "/clearformajax";
    String URL_APPLY_ADD_DELETE_AJAX = URL_APPLY_ADD + "/deleteajax";
    String URL_APPLY_ADD_ADDITEMAJAX = URL_APPLY_ADD + "/additemajax";

    String HTML_APPLY_ADD_PREFIX = HTML_APPLY_PREFIX + "add/";
    String HTML_APPLY_ADD_APPLYFORM = HTML_APPLY_ADD_PREFIX + "apply_form";
    String HTML_APPLY_ADD_ADDITEM = HTML_APPLY_ADD_PREFIX + "additem";

}
