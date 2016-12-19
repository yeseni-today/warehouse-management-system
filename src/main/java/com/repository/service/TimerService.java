package com.repository.service;

import com.repository.common.Constants;
import com.repository.dao.ItemOutOperationDao;
import com.repository.dao.ItemOutStorageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.PrintStream;
import java.util.Date;

import static com.repository.common.Constants.DAY_1_MILLIS;

/**
 * Created by finderlo on 16-12-19.
 */
@Component
public class TimerService implements Runnable {

    private static long ONE_HOUR = 1000 * 60 * 60;

    private static PrintStream out = System.out;

    @Override
    public void run() {
        out.println("定时服务开始启动   Timer Service start");
        while (true) {
            doing();
            threadSleep();
        }
    }

    private void threadSleep() {
        int count = 0;
        for (int i = 0; i < 24; i++) {
            try {
                out.println("开始睡眠");
                Thread.sleep(ONE_HOUR);
                out.println("睡眠完成，时间：" + ONE_HOUR + " 毫秒");
                count++;
                if (count == 24) {
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Autowired
    ItemOutOperationDao _outOperationDao;


    private void doing() {
        checkOutStorage();
    }

    private void checkOutStorage() {
        _outOperationDao.findByState(Constants.OUTSTOAGR_DEFAULT_STATUS).forEach(storage -> {
            long storage_date = storage.getOutTime().getTime();
            if ((System.currentTimeMillis() - storage_date) > (DAY_1_MILLIS * 7)) {
                storage.setOutStates(Constants.OUTSTOAGR_FAIL_STATUS);
                _outOperationDao.update(storage);
            }
        });
    }
}
