package service;

import com.repository.Application;
import com.repository.dao.UmessageDao;
import com.repository.service.MessageService;
import com.repository.web.message.MessageForm;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by Finderlo on 2016/11/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class MessageTest {

    @Autowired
    MessageService messageService;

    MessageForm messageForm;

    @Before
    public void before() {
        messageForm = new MessageForm();
        messageForm.setSend_ID("4565");
        messageForm.setMessageTitle("title");
        messageForm.setMessageContent("conetn");
        messageForm.setReceive_ID("4566");
    }

    @Test
    public void test() {
        Assert.assertTrue(messageService.send(messageForm));
        Assert.assertNotNull(messageService.findMessage("4566"));
        Assert.assertTrue(messageService.findWranMessage().size()!=0);
    }

    @Autowired
    UmessageDao umessageDao;

    @After
    public void after(){
        messageService.delete(messageForm);
    }


}
