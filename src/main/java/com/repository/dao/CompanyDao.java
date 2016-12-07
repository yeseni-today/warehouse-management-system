package com.repository.dao;

import com.repository.entity.CompanyEntity;
import com.repository.entity.DictionaryEntity;
import com.repository.util.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public class CompanyDao extends AbstractDao<CompanyEntity> {

    @Autowired
    DictionaryDao dictionaryDao;

    public boolean addCompany(String name, String address, String phone) {
        CompanyEntity companyEntity = new CompanyEntity();
        DictionaryEntity dictionaryEntity = dictionaryDao.findById("company_ID");
        companyEntity.setCompanyAddress(address);
        companyEntity.setCompanyName(name);
        companyEntity.setCompanyPhone(phone);
        companyEntity.setCompanyId(Util.handleCode(dictionaryEntity));
        dictionaryEntity.setIndex(dictionaryEntity.getIndex() + 1);
        save(companyEntity);
        dictionaryDao.update(dictionaryEntity);
        return true;
    }
}
