import com.repository.dao.ItemInStorageDao;
import com.repository.dao.ItemOutOperationDao;
import com.repository.entity.ItemInStorageEntity;
import com.repository.entity.ItemOutOperationEntity;

import java.sql.Date;
import java.util.List;

/**
 * Created by Finderlo on 2016/11/10.
 */
public class Test1 {

    public static void main(String[] args) {
        ItemOutOperationDao inStorageDao = new ItemOutOperationDao();
        ItemOutOperationEntity entity = new ItemOutOperationEntity();

        entity.setApplyId(String.valueOf(51));
        entity.setOperationId(String.valueOf(45));
        entity.setOutAddress("555");
        entity.setOutId("4555");
        entity.setOutStates("25555");
        entity.setOutTime(new Date(System.currentTimeMillis()));
        entity.setUsersId(String.valueOf(552.2));

        inStorageDao.save(entity);
    }
}
