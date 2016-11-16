package com.repository.util;

import com.repository.entity.SdictionaryEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Finderlo on 11/16/2016.
 */
public class Util {

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

    public static void main(String[] args) {
        List<SdictionaryEntity> tests = new ArrayList<>();

        SdictionaryEntity a = new SdictionaryEntity();
        a.setField("021");
        a.setIndex(1);
        a.setIndexlength(5);
        tests.add(a);
        SdictionaryEntity b = new SdictionaryEntity();
        b.setField("company_ID");
        b.setIndex(1);
        b.setIndexlength(5);
        tests.add(b);
        SdictionaryEntity c = new SdictionaryEntity();
        c.setField("application_ID");
        c.setIndex(100);
        c.setIndexlength(5);
        tests.add(c);
        SdictionaryEntity d = new SdictionaryEntity();
        d.setField("borrow_ID");
        d.setIndex(100);
        d.setIndexlength(5);
        tests.add(d);
        SdictionaryEntity e = new SdictionaryEntity();
        e.setField("storage_ID");
        e.setIndex(100);
        e.setIndexlength(5);
        tests.add(e);
        SdictionaryEntity f = new SdictionaryEntity();
        f.setField("out_ID");
        f.setIndex(100);
        f.setIndexlength(5);
        tests.add(f);
        SdictionaryEntity g = new SdictionaryEntity();
        g.setField("029");
        g.setIndex(1);
        g.setIndexlength(5);
        tests.add(g);
        for (SdictionaryEntity sdictionaryEntity : tests) {
            System.out.println(Util.handleCode(sdictionaryEntity));
        }

        //System.out.println(String.format("%05d",12));

    }
}
