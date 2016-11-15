package com.repository.web.storage.instroage;

import com.repository.entity.ItemCategoryEntity;
import com.repository.entity.ItemCompanyEntity;
import com.repository.entity.ItemEntity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Finderlo on 2016/11/9.
 */
public class ItemInfo {


    public Map<Key_EN_CH, String> entries = new HashMap<>();

    private boolean isInStorage = false;

    public ItemInfo(ItemEntity itemEntity) {

        initKeyench();
        if (itemEntity != null) {
            isInStorage = true;
            initEntries(itemEntity);
        } else {
            isInStorage = false;
        }
    }

    private void initValue(ItemEntity entity) {
        if (entity == null) {
            return;
        }


    }

    private void initKeyench() {
        List<Key_EN_CH> keys = new ArrayList<>();
        dictionary().entrySet().forEach(e -> {
            entries.put(new Key_EN_CH(e.getKey(), e.getValue()), null);
        });
    }

//    private void initNull() {
//        dictionary().entrySet().forEach(entry1 -> {
//            Entry entry = new Entry();
//            entry.setKey_en(entry1.getKey());
//            entry.setKey_cn(entry1.getValue());
//            entries.add(entry);
//        });
//    }

    private void initEntries(ItemEntity entity) {
        if (entity == null) {
            return;
        }
        Field[] fields = entity.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
//                Key_EN_CH keyENCH = new Key_EN_CH();
                field.setAccessible(true);

                String fieldName = field.getName();

                for (Map.Entry<Key_EN_CH, String> entry : entries.entrySet()) {
                    if (entry.getKey().key_en == fieldName) {
                        entries.put(entry.getKey(), field.get(entity).toString());
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public boolean isInStorage() {
        return isInStorage;
    }


    public static Map<String, String> dictionary() {
        Map<String, String> result = new HashMap<>();
        result.put("itemCode", "物品条形码");
        result.put("itemName", "物品名称");
        result.put("itemCount", "物品数量");
        result.put("itemSpec", "物品规格");
        result.put("itemPrice", "物品单价（人民币 元）");
        result.put("itemIntroduce", "物品介绍");
        result.put("itemCompanyID", "物品生产厂商ID");
        result.put("itemCategoryID", "物品分类ID");
        result.put("itemBorrowTimeLimit", "物品允许借取最大时间");
        result.put("itemState", "物品状态");
        result.put("itemExamine", "物品审核信息");
        return result;
    }


    static class Key_EN_CH {
        private String key_en;
        private String key_cn;

        public Key_EN_CH() {
        }

        public Key_EN_CH(String key_en, String key_cn) {
            this.key_cn = key_cn;
            this.key_en = key_en;
        }

        public String getKey_en() {
            return key_en;
        }

        public void setKey_en(String key_en) {
            this.key_en = key_en;
        }

        public String getKey_cn() {
            return key_cn;
        }

        public void setKey_cn(String key_cn) {
            this.key_cn = key_cn;
        }

        @Override
        public int hashCode() {
            int result = key_cn != null ? key_cn.hashCode() : 0;
            result = 31 * result + (key_en != null ? key_en.hashCode() : 0);
            return result;
        }


        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Key_EN_CH entity = (Key_EN_CH) obj;

            if (key_en != null ? !key_en.equals(entity.key_en) : entity.key_en != null)
                return false;
            if (key_cn != null ? !key_cn.equals(entity.key_cn) : entity.key_cn != null)
                return false;

            return true;
        }
    }
}
