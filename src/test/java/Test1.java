import com.repository.Application;
import com.repository.base.BaseObject;
import com.repository.dao.ItemDao;
import com.repository.dao.ItemInStorageDao;
import com.repository.dao.ItemOutOperationDao;
import com.repository.dao.SdictionaryDao;
import com.repository.entity.ItemInStorageEntity;
import com.repository.entity.ItemOutOperationEntity;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.sql.Date;
import java.util.List;

/**
 * Created by Finderlo on 2016/11/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class Test1 extends
        BaseObject {

    @Autowired
    SdictionaryDao sdictionaryDao;

    @Autowired
    ItemDao itemDao;
    @Test
    public void isNull() {
        Assert.assertNotNull(sdictionaryDao);
    }

    public void sdictionaryDaoTestId() {
        Assert.assertNotNull(sdictionaryDao.findById("000"));
    }

    @Test
    public void one() {
        itemDao.getOne().forEach(entity -> {
            logger.info(entity.getItemCode());
        });
    }
}
