package com.abner.ming.abnerlibrary;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.abner.ming.abnerlibrary.net.RxRetrofitApp;
import com.abner.ming.abnerlibrary.sql.GreenDaoManager;

/**
 * Created by Administrator on 2018/3/8.
 */

public class AbnerApplication extends MultiDexApplication {
    public static Context app;
    @Override
    public void onCreate() {
        super.onCreate();
        app=getApplicationContext();
        GreenDaoManager.getInstance();
        RxRetrofitApp.init(this,BuildConfig.DEBUG);
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    public static Context getContext(){
        return app;
    }
}
