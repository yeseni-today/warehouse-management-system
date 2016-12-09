package web;

import com.repository.Application;
import com.repository.service.MessageService;
import com.repository.web.message.MessageController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by Finderlo on 2016/12/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class TestMsg {

    @Autowired
    MessageController controller;
    @Autowired
    MessageService service;

    @Test
    public void test() {
        System.out.println("before:state:"+service.findById("66674").getMessageState());
        System.out.println("doing");
        controller.read("66674");
        System.out.println("complete"+service.findById("66674").getMessageState());
    }
}
