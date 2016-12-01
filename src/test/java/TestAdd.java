import com.repository.Application;
import com.repository.dao.ItemDao;
import com.repository.dao.SdictionaryDao;
import com.repository.entity.ItemEntity;
import com.repository.entity.SdictionaryEntity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

/**
 * Created by Finderlo on 2016/11/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class TestAdd {

    @Autowired
    ItemDao itemDao;
    @Autowired
    SdictionaryDao sdictionaryDao;
    @Autowired
    SessionFactory sessionFactory;
    SdictionaryEntity entity;

    @Before
    public void before() {
        entity = new SdictionaryEntity();
        entity.setIndex(55);
        entity.setField("9999");
        entity.setIndexlength(88);
        entity.setTable("adsdsd");
    }

    @Test
    @Transactional
    public void set() {
        sdictionaryDao.save(entity);
    }

    @After
    public void after() {
        sdictionaryDao.delete(entity);
    }
}
