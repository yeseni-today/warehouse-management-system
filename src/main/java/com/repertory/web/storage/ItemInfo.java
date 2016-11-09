package com.repertory.web.storage;

import com.repertory.bean.ItemCategoryEntity;
import com.repertory.bean.ItemCompanyEntity;
import com.repertory.bean.ItemEntity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Finderlo on 2016/11/9.
 */
public class ItemInfo {


    public List<Entry> entries = new ArrayList<>();

    private boolean isInStorage;

    public ItemInfo(ItemEntity itemEntity) {
        if (itemEntity != null) {
            isInStorage = true;
            initEntries(itemEntity);
        }
        isInStorage = false;
    }

    private void initEntries(ItemEntity entity) {
        Field[] fields = entity.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                Entry entry = new Entry();
                field.setAccessible(true);
                String fieldName = field.getName();
                if (dictionary().get(fieldName.trim().toLowerCase()) == null) {
                    if (fieldName.equals("itemCategoryEntity")) {
                        entry.setKey_cn(dictionary().get("itemcategoryID"));
                        entry.setKey_en(fieldName);
                        entry.setValue(((ItemCategoryEntity) field.get(entity)).getCategoryId().toString());
                    } else if (fieldName.equals("itemCompanyEntity")) {
                        entry.setKey_cn(dictionary().get("itemcategoryID"));
                        entry.setKey_en(fieldName);
                        entry.setValue(((ItemCompanyEntity) field.get(entity)).getCompanyId().toString());
                    }
                    continue;
                } else {
                    entry.setKey_cn(dictionary().get(fieldName.trim().toLowerCase()));
                    entry.setKey_en(fieldName);
                    entry.setValue(field.get(entity).toString());
                }
                entries.add(entry);
            }
        } catch (Exception e) {

        }
    }


    public boolean isInStorage() {
        return isInStorage;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public static Map<String, String> dictionary() {
        Map<String, String> result = new HashMap<>();
        result.put("itemcode", "物品条形码");
        result.put("itemname", "物品名称");
        result.put("itemcount", "物品数量");
        result.put("itemspec", "物品规格");
        result.put("itemprice", "物品单价（人民币 元）");
        result.put("itemintroduce", "物品介绍");
        result.put("itemcompanyID", "物品生产厂商ID");
        result.put("itemcategoryID", "物品分类ID");
        result.put("itemborrowtimelimit", "物品允许借取最大时间");
        result.put("itemstate", "物品状态");
        result.put("itemexamine", "物品审核信息");
        return result;
    }


    static class Entry {
        private String key_en;
        private String key_cn;
        private String value;

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

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
