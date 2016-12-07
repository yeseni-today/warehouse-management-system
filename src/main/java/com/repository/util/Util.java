package com.repository.util;

import com.repository.dao.ItemDao;
import com.repository.entity.ItemInStorageEntity;
import com.repository.entity.DictionaryEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by Finderlo on 11/16/2016.
 */
@Component
public class Util {

    @Autowired
    public ItemDao itemDao;
    @Autowired
    public static Util util;

    /**
    *2016/12/7
     * @param list 一个物品对应的所有可用批次的list
     * @param count 提取的数量
    **/
    public static List<ItemInStorageEntity> changecount(List<ItemInStorageEntity> list, int count) {
        for (ItemInStorageEntity itembean : list) {
            int temp = itembean.getAllowCount();  //temp表示该批次允许提取数量
            if (count == 0) break;
            else if (temp >= count) {   //该批次剩余数量大于需求
                temp -= count;
                itembean.setAllowCount(temp);   //设置 处理后的值
                count = 0;
            } else if (temp < count) {           //该批次剩余数量小于需求
                count -= temp;
                itembean.setAllowCount(0);        //设置 处理后的值为0
            }

        }
        return list;
    }

    public static String handleCode(DictionaryEntity dictionaryEntity) {
        String code = null;
        Date _date = new Date();
        String date = String.format("%tY", _date) + String.format("%tm", _date) + String.format("%td", _date);
        switch (dictionaryEntity.getField().trim()) {
            case "company_ID":
                code = String.valueOf(dictionaryEntity.getIndex());
                break;
            case "application_ID":
                code = date + "3" + dictionaryEntity.getIndex();
                break;
            case "borrow_ID":
                code = date + "2" + dictionaryEntity.getIndex();
                break;
            case "storage_ID":
                code = date + "1" + dictionaryEntity.getIndex();
                break;
            case "out_ID":
                code = date + "4" + dictionaryEntity.getIndex();
                break;
            default:
                code = dictionaryEntity.getField().trim() + String.format("%tY", _date) + "0" + String.format("%05d", dictionaryEntity.getIndex());
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
