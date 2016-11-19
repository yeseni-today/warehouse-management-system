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

    //    @Test
    @Transactional
    public void set() {
        Session session = sessionFactory.getCurrentSession();
//        Transaction transaction = session.beginTransaction();
//        transaction.begin();
        SdictionaryEntity entity = new SdictionaryEntity();
        entity.setIndex(55);
        entity.setField("9999");
        entity.setIndexlength(88);
        entity.setTable("adsdsd");
        sdictionaryDao.save(entity);
//        transaction.commit();
//        transaction.rollback();
    }
}
