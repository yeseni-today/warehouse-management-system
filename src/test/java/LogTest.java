import com.repository.Application;
import com.repository.service.LogSerivce;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by Finderlo on 2016/11/27.
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)@WebAppConfiguration
public class LogTest {

    @Autowired
    LogSerivce serivce;

    @Test
    public void addlog() {
        System.out.println("中文测试");
        serivce.queryItem("500", "sdasd");
    }
}
