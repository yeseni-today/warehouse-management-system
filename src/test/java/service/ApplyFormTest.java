package service;

import com.repository.Application;
import com.repository.dao.ItemApplicationDao;
import com.repository.dao.ItemApplicationOperationDao;
import com.repository.dao.DictionaryDao;
import com.repository.service.ApplyFormService;
import com.repository.web.apply.add.ApplyForm;
import com.repository.web.apply.add.ApplyItemForm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

/**
 * Created by Finderlo on 2016/12/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ApplyFormTest {

    ApplyForm applyForm;

    @Autowired
    DictionaryDao dictionaryDao;

    @Autowired
    ApplyFormService applyFormService;

    @Autowired
    ItemApplicationDao applicationDao;
    @Autowired
    ItemApplicationOperationDao applicationOperationDao;
    @Before
    public void before(){
        applyForm = new ApplyForm(dictionaryDao.getApplicationId(),"6666");

        ApplyItemForm itemForm = new ApplyItemForm();
        itemForm.setItemCode("0000000000009");
        itemForm.setApplyType("type");
        itemForm.setItemCount(1);
        itemForm.setOthers("备注");
        
        applyForm.getItems().add(itemForm);

//        ApplyItemForm item2 = new ApplyItemForm();
//        item2.setItemCode("0202016000025");
//        item2.setApplyType("type");
//        item2.setItemCount(1);
//        item2.setOthers("备注");
//
//        applyForm.getItems().add(item2);

    }

    @Test
    @Transactional
    @Rollback
    public void save(){
//        Assert.assertTrue(applyFormService.save(applyForm));
    }

    @After
    public void delete(){
//        applicationDao.delete();
    }
}
