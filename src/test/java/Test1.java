//
import com.repository.Application;
import com.repository.base.BaseObject;
import com.repository.dao.ItemDao;
import com.repository.dao.DictionaryDao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by Finderlo on 2016/11/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class Test1 extends
        BaseObject {

    @Autowired
    DictionaryDao dictionaryDao;

    @Autowired
    ItemDao itemDao;
    @Test
    public void isNull() {
        System.out.println();
        Assert.assertNotNull(dictionaryDao);
    }

    public void sdictionaryDaoTestId() {
        Assert.assertNotNull(dictionaryDao.findById("000"));
    }

    @Test
    public void one() {
        itemDao.getOne().forEach(entity -> {
            logger.info(entity.getItemCode());
        });
    }
}
