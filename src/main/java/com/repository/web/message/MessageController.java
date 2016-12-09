package com.repository.web.message;

import com.repository.base.BaseController;
import com.repository.model.SimpleRes;
import com.repository.service.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

import static com.repository.Constants.*;

@Controller
public class MessageController extends BaseController {


    @Autowired
    MessageService messageService;

    /**
     * 消息主页 返回view
     * @return view
     */
    @RequestMapping(URL_MESSAGE)
    public String message() {
        return HTML_MESSAGE_LIST;
    }

    /**
     * 新建消息 返回view
     * @return view
     */
    @RequestMapping(URL_MESSAGE_NEW)
    public String newmessage() {
        return HTML_MESSAGE_NEWMESSAGE;
    }


    /**
     * 发送一条消息，参数/message/new中表单中的值
     *
     * @param messageForm ..
     * @param principal   发送人
     * @return
     */
    @RequestMapping(value = URL_MESSAGE_SEND_AJAX, method = RequestMethod.POST)
    @ResponseBody
    public SimpleRes send(MessageForm messageForm, Principal principal) {
        messageForm.setSend_ID(principal.getName());
        if (messageService.send(messageForm)) {
            return SimpleRes.success();
        } else return SimpleRes.error();
    }

    /**
     * 获取当前用户收到的所有消息
     *
     * @param principal .
     * @return .
     */
    @RequestMapping(URL_MESSAGE_FINDMESSAGE_BY_ID_AJAX)
    @ResponseBody
    public SimpleRes findmsg(Principal principal) {
        SimpleRes simpleRes = new SimpleRes();
        simpleRes.setContent(messageService.findMessage(principal.getName()));
        return simpleRes;
    }

    /**
     * 获取提醒消息，库存提醒，返回为message的list
     *
     * @return
     */
    @RequestMapping(URL_MESSAGE_FIND_WARNTYPE_AJAX)
    @ResponseBody
    public SimpleRes warn() {
        return SimpleRes.withObject(messageService.findWranMessage());
    }

    /**
     * 把信息设为已读，参数为消息的id
     *
     * @param msgId 消息Id
     * @return success or error
     */
    @RequestMapping(URL_MESSAGE_READWHITID_AJAX)
    @ResponseBody
    public SimpleRes read(
            @RequestParam(name = "messageID", required = false, defaultValue = "") String msgId) {
        if (msgId != null && !msgId.trim().equals("")) {
            messageService.read(msgId);
            return SimpleRes.success();
        } else {
            return SimpleRes.error("消息id为空");
        }

    }

    /**
     * 通过id获得消息
     *
     * @param msgId 消息Id
     * @return
     */
    @RequestMapping(URL_MESSAGE_FINDBYID_AJAX)
    @ResponseBody
    public SimpleRes findById(
            @RequestParam(name = "messageID", required = false, defaultValue = "") String msgId) {
        if (msgId != null && !msgId.trim().equals("")) {
            return SimpleRes.withObject(messageService.findById(msgId));
        } else {
            return SimpleRes.error("消息id为空");
        }

    }

    /**
     * 删除一条信息，参数为消息的id
     *
     * @param msgId 消息Id
     * @return .
     */
    @RequestMapping(URL_MESSAGE_DELETWHITID_AJAX)
    @ResponseBody
    public SimpleRes delete(@RequestParam(name = "messageID", required = false, defaultValue = "") String msgId) {
        if (msgId != null && !msgId.trim().equals("")) {
            messageService.delete(msgId);
            return SimpleRes.success();
        } else {
            return SimpleRes.error("消息id为空");
        }
    }
}
