package com.repository.common;

/**
 * Created by finderlo on 16-12-12.
 */
public interface ManageContants {

    //manage
    String URL_MANAGE_EXAMEINE = "/manage/examine";
    String URL_MANAGE_OUTSTORAGE = "/manage/outStorage";

    String URL_MANAGE_GETEXAMLNE = "/manage/getlist";
    /**
     * 审核申请单，传入参数：states:true、false；apply_id:
     * true 为通过审核申请单，false 为审核不通过
     * apply_id 为测试审核单的id
     * */
    String URL_MANAGE_PASSEXAMINE="/manage/passexamine";
    String HTML_MANAGE_EXAMINE = "/manage/examine";
    String HTML_MANAGE_OUTSTORAGE = "/manage/outStorage";
}
