package com.repository.util;

import com.repository.dao.ItemDao;
import com.repository.entity.SdictionaryEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Finderlo on 11/16/2016.
 */
@Component
public class Util {

    @Autowired
    public ItemDao itemDao;
    @Autowired
    public static Util util;




    public static String handleCode(SdictionaryEntity sdictionaryEntity) {
        String code = null;
        Date _date = new Date();
        String date = String.format("%tY", _date) + String.format("%tm", _date) + String.format("%td", _date);
        switch (sdictionaryEntity.getField().trim()) {
            case "company_ID":
                code = String.valueOf(sdictionaryEntity.getIndex());
                break;
            case "application_ID":
                code = date + "3" + sdictionaryEntity.getIndex();
                break;
            case "borrow_ID":
                code = date + "2" + sdictionaryEntity.getIndex();
                break;
            case "storage_ID":
                code = date + "1" + sdictionaryEntity.getIndex();
                break;
            case "out_ID":
                code = date + "4" + sdictionaryEntity.getIndex();
                break;
            default:
                code = sdictionaryEntity.getField().trim() + String.format("%tY", _date) + "0" + String.format("%05d", sdictionaryEntity.getIndex());
                break;
        }
        return code;
    }


    public static String number() {
        Date _date = new Date(System.currentTimeMillis());

        int index = (int) (Math.random() * 1000);
        String date1 = String.format("%tY", _date) + String.format("%tm", _date) + String.format("%td", _date);
        return new StringBuilder().append(date1).append(index).toString();
    }

    public static void main(String[] args) {
        System.out.println(number());
    }
}
