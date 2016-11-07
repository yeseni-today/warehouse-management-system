import com.repertory.bean.ItemCategoryEntity;
import com.repertory.bean.ItemCompanyEntity;
import com.repertory.bean.ItemEntity;
import com.repertory.dao.ItemCategoryDao;
import com.repertory.dao.ItemCompanyDao;
import com.repertory.dao.ItemDao;

/**
 * Created by Finderlo on 2016/11/5.
 */
public class AddItemCate {


    public static void main(String[] args) {
        AddItemCate addItemCate = new AddItemCate();
//        addItemCate.addCategory();
//        addItemCate.addCompany();
        addItemCate.addItem();
    }


    public void addCategory(){
         String index = "00";
        ItemCategoryDao itemCategoryDao = new ItemCategoryDao();
        for (int i = 0; i < 10; i++) {
            ItemCategoryEntity entity = new ItemCategoryEntity();
            entity.setCategoryId(index+i);
            entity.setCategoryName("分类"+i);
            itemCategoryDao.save(entity);
        }
    }

    public void addCompany(){
        ItemCompanyDao companyDao = new ItemCompanyDao();
        for (int i = 0; i < 10; i++) {
            ItemCompanyEntity entity = new ItemCompanyEntity();
            entity.setCompanyId("000"+i);
            entity.setCompanyName("公司名称"+i);
            entity.setCompanyPhone("1111111"+i);
            entity.setCompanyAddress("这是地址"+i);
            companyDao.save(entity);
        }
    }

    public void addItem(){
        ItemDao companyDao = new ItemDao();
        for (int i = 0; i < 10; i++) {
            ItemEntity entity = new ItemEntity();
            entity.setItemCode("000000000000"+i);
            entity.setItemName("名称"+i);
            entity.setItemCount((int) (Math.random()*1000));
            entity.setItemSpec("这是spec"+i);
            entity.setItemPrice(Math.random()*100);
            entity.setItemIntroduce("这是介绍"+i);
            ItemCompanyEntity companyEntity = new ItemCompanyEntity();
            companyEntity.setCompanyId("000"+i);
            entity.setItemCompanyEntity(companyEntity);
            ItemCategoryEntity categoryEntity = new ItemCategoryEntity();
            categoryEntity.setCategoryId("00"+i);
            entity.setItemCategoryEntity(categoryEntity);
            entity.setItemBorrowTimelimit(i*10);
            entity.setItemState("这是state"+i);
            entity.setItemExamine("examine"+i);
            entity.setItemRemind(i*10+5-8-5+6);
            companyDao.save(entity);
        }
    }
}
