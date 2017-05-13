package service;

import com.repository.Application;
import com.repository.dao.MessageDao;
import com.repository.service.MessageService;
import com.repository.web.message.MessageForm;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

/**
 * Created by Finderlo on 2016/11/30.
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
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
    @Transactional
    @Rollback
    public void test() {
        Assert.assertTrue(messageService.send(messageForm));
        //Assert.assertNotNull(messageService.findMessage("4566"));
        Assert.assertTrue(messageService.findWranMessage().size()!=0);
    }

    @Autowired
    MessageDao messageDao;




}
