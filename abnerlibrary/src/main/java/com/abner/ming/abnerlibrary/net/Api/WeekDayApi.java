package com.abner.ming.abnerlibrary.net.Api;

import com.abner.ming.abnerlibrary.net.listener.HttpOnNextListener;
import com.abner.ming.abnerlibrary.net.service.WeekApiService;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Administrator on 2018/3/7.
 */

public class WeekDayApi extends HttpApi{
    public WeekDayApi(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity) {
        super(listener, rxAppCompatActivity);
    }
    @Override
    public Observable getObservable(Retrofit retrofit) {
        WeekApiService service = retrofit.create(WeekApiService.class);
        return service.getWeekDayList();
    }

}
