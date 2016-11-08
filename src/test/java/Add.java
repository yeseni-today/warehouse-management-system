import com.repertory.bean.ItemEntity;
import com.repertory.bean.ItemInOperationEntity;
import com.repertory.bean.ItemInStorageEntity;
import com.repertory.dao.ItemDao;
import com.repertory.dao.ItemInOperationDao;
import com.repertory.dao.ItemInStorageDao;

import java.sql.Date;
import java.util.Random;

/**
 * Created by Finderlo on 2016/11/7.
 */
public class Add {

    public static void main(String[] args) {
//
//        Random random = new Random();
//
//        for (int i = 0; i < 100; i++) {
//            System.out.println(random.nextInt(5));
//        }
//        addInOpreation();
//        addItem();
        new ItemInStorageDao().findAll().forEach(entity -> {
            entity.setCounts(153);
            new ItemInStorageDao().saveOrUpdate(entity);
        });
    }


    public static void addInOpreation() {
        ItemInOperationDao inOperationDao = new ItemInOperationDao();

        for (int i = 0; i < 50; i++) {
            ItemInOperationEntity entity = new ItemInOperationEntity();
            entity.setOperationId("00" + i);
            entity.setStorageTime(new Date(System.currentTimeMillis()));
            entity.setStorageId("000000000" + i);
            inOperationDao.save(entity);
        }

    }

    public static void addItem() {
        ItemDao itemDao = new ItemDao();

        String[] names = new String[]{
                "粉笔", "小书包", "乞丐的砖头", "台灯", "天下唯一的一本书"
        };
        String[] descr = new String[]{
                "漂亮的粉笔","天一样大的小书包","金色的石头","小台灯","书"
        };
        Random random = new Random();

        for (int i = 100; i < 999; i++) {
            ItemEntity entity = new ItemEntity();
            entity.setItemCode("0000000000" + i);
            entity.setItemName(names[random.nextInt(5)] + i);
            entity.setItemCount(i * 40 + 5 - 68);
            entity.setItemSpec("spec" + i);
            entity.setItemPrice((double) (i / 3 - 8 + 58 * 5 + 8 * 65));
            entity.setItemIntroduce(descr[random.nextInt(5)]);
            entity.setItemCompanyId("000"+String.valueOf(random.nextInt(10)));
            entity.setItemCategoryId("00"+random.nextInt(10));
            entity.setItemBorrowTimelimit(i*10+58-92);
            entity.setItemState("enable");
            entity.setItemExamine("examine"+i);
            entity.setItemRemind(10);
            itemDao.saveOrUpdate(entity);
        }
    }

    public static void addInstorage() {
        ItemInStorageDao inStorageDao = new ItemInStorageDao();

        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            ItemInStorageEntity entity = new ItemInStorageEntity();
            entity.setStorageId("000000000" + i);
            entity.setItemCode("0000000000"+(100+random.nextInt(800)));
            entity.setCounts(i-5+78+35);
            entity.setPrice((double) (654+56+i));
            entity.setBillCode("00000000"+(10+random.nextInt(100)));
            entity.setItemSlot("itemslot"+i);
            entity.setItemBatch("item_batch"+i);
            entity.setItemIndate(new Date(System.currentTimeMillis()));
            entity.setAllowCount(i*5+98-85+5);
            inStorageDao.saveOrUpdate(entity);
        }
    }
}
