package service;

import com.repository.Application;
import com.repository.service.OutStorageService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by Finderlo on 2016/12/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class OutsRIVECE {

    String itemcode = "0202016001018";
    int count = 5;

    @Autowired
    OutStorageService service;

    @Test
    public void test(){
        service.changeBatchCount(itemcode,5);
    }
}
