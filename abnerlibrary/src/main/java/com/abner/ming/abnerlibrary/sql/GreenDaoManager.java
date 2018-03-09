package com.abner.ming.abnerlibrary.sql;

import android.content.Context;

import com.abner.ming.abnerlibrary.AbnerApplication;
import com.abner.ming.abnerlibrary.net.download.DaoMaster;
import com.abner.ming.abnerlibrary.net.download.DaoSession;

/**
 * Created by Administrator on 2018/3/8.
 */

public class GreenDaoManager {
    private DaoMaster.DevOpenHelper helper;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private static GreenDaoManager greenDaoManager;

    public GreenDaoManager() {
        helper = new DaoMaster.DevOpenHelper(AbnerApplication.getContext(), "GreenDao_DB");
        daoMaster = new DaoMaster(helper.getWritableDb());
        daoSession = daoMaster.newSession();
    }

    public static GreenDaoManager getInstance() {
        if (greenDaoManager == null) {
            greenDaoManager = new GreenDaoManager();
        }
        return greenDaoManager;
    }

    public DaoMaster.DevOpenHelper getDevOpenHelper() {
        return helper;
    }

    public DaoMaster getDaoMaster() {
        return daoMaster;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public void closeDB() {
        if (helper != null) {
            helper.close();
        }
    }
}
