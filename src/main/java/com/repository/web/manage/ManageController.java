package com.repository.web.manage;

import com.google.gson.Gson;
import com.repository.common.Constants;
import com.repository.dao.ItemApplicationDao;
import com.repository.dao.ItemApplicationOperationDao;
import com.repository.dao.ItemOutOperationDao;
import com.repository.dao.ItemOutStorageDao;
import com.repository.dao.view.ItemInDateDao;
import com.repository.entity.ItemApplicationEntity;
import com.repository.entity.ItemApplicationOperationEntity;
import com.repository.entity.ItemOutOperationEntity;
import com.repository.entity.ItemOutStorageEntity;
import com.repository.entity.view.ItemIndate;
import com.repository.model.SimpleRes;
import com.repository.service.ApplyFormService;
import com.repository.service.OutStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.repository.common.Constants.*;

/**
 * Created by Finderlo on 2016/12/8.
 */
@Controller
public class ManageController {

    @Autowired
    ItemApplicationOperationDao _applicationOperationDao;

    @Autowired
    ItemApplicationDao _applicationDao;

    @Autowired
    ItemInDateDao _itemInDateDao;

    @Autowired
    OutStorageService _outStorageService;

    @Autowired
    ItemOutOperationDao _itemOutOpreationDao;
    @Autowired
    ItemOutStorageDao _itemOutStorageDao;

    @Autowired
    private ApplyFormService _applyFormService;

    /*
    * 通过出库单id来查询出库单信息
    * @parm out_id
    * */
    @RequestMapping(URL_MANAGE_OUTSTORAGE_OUTSTOAGE_INFO)
    @ResponseBody
    public SimpleRes outstuage(@RequestParam(name = "out_id") String out_id) {
        OutStorageCom com = new OutStorageCom();
        com.operation = _itemOutOpreationDao.findById(out_id);
        com.outStorages = _itemOutStorageDao.findBy("outId", out_id, false);
        System.out.println(com.operation.toString());
        System.out.println(com.outStorages.size());
        return SimpleRes.success(com.toString());
    }

    private static class OutStorageCom {
        public ItemOutOperationEntity operation;
        public List<ItemOutStorageEntity> outStorages = new ArrayList<>();

        @Override
        public String toString() {
            return new Gson().toJson(this);
        }
    }

    /**
     * 有效期管理界面
     *
     * @return html view
     */
    @RequestMapping(URL_MANAGE_ITEMINDATE)
    public String itemindate(Model model) {
        List<ItemIndate> result =  _itemInDateDao.findByDayBefore(30);
        model.addAttribute("itemindates",result);
        reverse(result);
        return TILES_PREFIX + HTML_MANAHE_ITEMINDATE;
    }

    /**
     * @return view
     * @see com.repository.common.ManageContants#URL_MANAGE_EXAMEINE
     * 管理需要审核的申请网页 返回view
     */
    @RequestMapping(URL_MANAGE_EXAMEINE)
    public String manage(Model model) {
        List datas = _applicationOperationDao.findBy("states", APPLY_NEED_EXAMINE, true);
        System.out.println("申请单数量：" + datas.size());
        model.addAttribute("history", datas);
        return TILES_PREFIX + HTML_MANAGE_EXAMINE;
    }

    /**
     * @see com.repository.common.ManageContants#URL_MANAGE_OUTSTORAGE
     * show 返回HTML view<br/>
     * 返回值中带有<span>history<span/>属性
     */
    @RequestMapping(URL_MANAGE_OUTSTORAGE)
    public String manageOutStorage(Model model) {
        model.addAttribute("history", _itemOutOpreationDao.findByState(Constants.OUTSTOAGR_DEFAULT_STATUS));
        return TILES_PREFIX + HTML_MANAGE_OUTSTORAGE;
    }

    /**
     * @return SimpRes
     * @parm itemName
     * @parm itemCode
     * @see com.repository.common.ManageContants#URL_MANAGE_ITEMINDATE_QUERYINFO
     * 查询物品有效期
     */
    @RequestMapping(URL_MANAGE_ITEMINDATE_QUERYINFO)
    @ResponseBody
    public SimpleRes quera(@RequestParam(value = "itemCode", required = false) String itemCode,
                           @RequestParam(value = "itemName", required = false) String itemName) {
        System.out.println(itemCode + "    " + itemName);
        List<ItemIndate> result = new ArrayList<>();
        result.addAll(_itemInDateDao.findByItemCode(itemCode));
        result.addAll(_itemInDateDao.findByItemName(itemName));
        reverse(result);
        System.out.println(result.size());
        return SimpleRes.success(result);
    }

    public void reverse(List<ItemIndate> list) {
        //先排序
        list.sort(Comparator.comparingLong(e -> e.getItemIndate().getTime()));
        //降序排列
//        Collections.reverse(list);
    }


    /**
     * 获取申请单的详情
     *
     * @retuen simres.conent=ApplyCompound
     */
    @RequestMapping(value = URL_APPLY_INFO_JSON, method = RequestMethod.GET)
    @ResponseBody
    public SimpleRes getApplyInfo(@RequestParam(value = "application_id", required = false) String application_id) {
        if (application_id == null || application_id.trim().equals("")) {
            return SimpleRes.error("ID不能为空");
        }
        return SimpleRes.success(
                new ApplyCompound(
                        _applicationOperationDao.findById(application_id),
                        _applicationDao.findBy("applicationId", application_id, false))
        );
    }

    @RequestMapping(value = URL_MANAGE_OUTSTORAGE_COMFORM, method = RequestMethod.POST)
    @ResponseBody
    public SimpleRes comform(@RequestParam(name = "out_id") String out_id) {
        return _outStorageService.outState(out_id, true) ? SimpleRes.success() : SimpleRes.error();
    }


    /**
     * 审核申请单，传入参数：states:true、false；apply_id:
     * true 为通过审核申请单，false 为审核不通过
     * apply_id 为审核单的id
     */
    @RequestMapping(value = URL_MANAGE_PASSEXAMINE, method = RequestMethod.POST)
    @ResponseBody
    public SimpleRes passExamine(
            Principal principal,
            @RequestParam("states") boolean states,
            @RequestParam("apply_id") String apply_id) {
        //通过：修改申请单状态，创建出库单，修改物品表，修改入库管理表，发送成功消息（给用户），记录日志
        try {
            _applyFormService.examine(principal, apply_id, states);
        } catch (Exception e) {
            return SimpleRes.error();
        }

        return SimpleRes.success();
    }

    private static class ApplyCompound {
        private String applicationId;
        private String usersId;
        private String examineId;
        private String states;
        private Date statesTime;
        private Date applicationTime;
        private List<ItemApplicationEntity> items;

        public ApplyCompound(ItemApplicationOperationEntity applicationOperation, List<ItemApplicationEntity> itemApplicationEntities) {
            this.applicationId = applicationOperation.getApplicationId();
            this.usersId = applicationOperation.getUsersId();
            this.examineId = applicationOperation.getExamineId();
            this.states = applicationOperation.getStates();
            this.statesTime = applicationOperation.getStatesTime();
            this.applicationTime = applicationOperation.getApplicationTime();
            this.items = itemApplicationEntities;
        }

        public String getApplicationId() {
            return applicationId;
        }

        public void setApplicationId(String applicationId) {
            this.applicationId = applicationId;
        }

        public String getUsersId() {
            return usersId;
        }

        public void setUsersId(String usersId) {
            this.usersId = usersId;
        }

        public String getExamineId() {
            return examineId;
        }

        public void setExamineId(String examineId) {
            this.examineId = examineId;
        }

        public String getStates() {
            return states;
        }

        public void setStates(String states) {
            this.states = states;
        }

        public Date getStatesTime() {
            return statesTime;
        }

        public void setStatesTime(Date statesTime) {
            this.statesTime = statesTime;
        }

        public Date getApplicationTime() {
            return applicationTime;
        }

        public void setApplicationTime(Date applicationTime) {
            this.applicationTime = applicationTime;
        }

        public List<ItemApplicationEntity> getItems() {
            return items;
        }

        public void setItems(List<ItemApplicationEntity> items) {
            this.items = items;
        }
    }

}


