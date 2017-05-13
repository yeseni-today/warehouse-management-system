import com.repository.Application;
import com.repository.dao.ItemDao;
import com.repository.dao.DictionaryDao;
import com.repository.entity.DictionaryEntity;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.junit.After;
import org.junit.Before;

/**
 * Created by Finderlo on 2016/11/18.
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)@WebAppConfiguration
public class TestAdd {

    @Autowired
    ItemDao itemDao;
    @Autowired
    DictionaryDao dictionaryDao;
    @Autowired
    SessionFactory sessionFactory;
    DictionaryEntity entity;

    @Before
    public void before() {
        entity = new DictionaryEntity();
        entity.setIndex(55);
        entity.setField("9999");
        entity.setIndexlength(88);
        entity.setTable("adsdsd");
    }

    @Test
    @Transactional
    public void set() {
        dictionaryDao.save(entity);
    }

    @After
    public void after() {
        dictionaryDao.delete(entity);
    }
}
