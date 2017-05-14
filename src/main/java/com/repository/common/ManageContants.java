package com.repository.common;

/**
 * Created by finderlo on 16-12-12.
 */
public interface ManageContants {

    //manage
    String URL_MANAGE = "/manage";
    /**
     * 管理需要审核的申请网页 返回view
     *
     * @return view
     */
    String URL_MANAGE_EXAMEINE = URL_MANAGE + "/examine";
    /**
     * show 返回HTML view<br/>
     * 返回值中带有<span>history<span/>属性
     * */
    String URL_MANAGE_OUTSTORAGE = URL_MANAGE + "/outStorage";

    String URL_MANAGE_GETEXAMLNE = URL_MANAGE + "/getlist";
    /**
     * 审核申请单，传入参数：states:true、false；apply_id:
     * true 为通过审核申请单，false 为审核不通过
     * apply_id 为测试审核单的id
     */
    String URL_MANAGE_PASSEXAMINE = URL_MANAGE + "/passexamine";
    //out storage
    String URL_MANAGE_OUTSTORAGE_OUTSTOAGE_INFO ="/manage/outopreationinfo.json";

    String URL_MANAGE_OUTSTORAGE_COMFORM = "/manage/confirmOut";
    /**
     * 查询物品有效期
     * @parm itemName
     * @parm itemCode
     * @return SimpRes
     * */
    String URL_MANAGE_ITEMINDATE_QUERYINFO = "/manage/queryItemInDateById.json";
    /**
     * 返回有效期管理界面
     */
    String URL_MANAGE_ITEMINDATE = URL_MANAGE + "/itemindate";

    String HTML_MANAGE_PREFIX = "manage/";
    /**
     * 有效期管理的view视图
     */
    String HTML_MANAHE_ITEMINDATE = HTML_MANAGE_PREFIX + "itemindate";
    String HTML_MANAGE_EXAMINE = HTML_MANAGE_PREFIX + "examine";
    String HTML_MANAGE_OUTSTORAGE = HTML_MANAGE_PREFIX + "outStorage";
}
