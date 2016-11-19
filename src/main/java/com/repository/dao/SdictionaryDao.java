package com.repository.dao;

import com.repository.entity.SdictionaryEntity;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import static com.repository.util.Util.*;

/**
 * Created by Finderlo on 11/16/2016.
 */
@Component
@Repository
public class SdictionaryDao extends AbstractDao<SdictionaryEntity> {

    public String getInstorgeId() {
        return handleCode(findById("storage_ID"));
    }

    public String getOutStorageId() {
        return handleCode(findById("out_ID"));
    }

    public String getBorrowId() {
        return handleCode(findById("borrow_ID"));
    }

    public String getInSchoolId(String categoryId) {
        logger.info("categoryId:" + categoryId);
        return handleCode(findById(categoryId.trim()));
    }


    public String getApplicationId() {
        return handleCode(findById("application_ID"));
    }
}
