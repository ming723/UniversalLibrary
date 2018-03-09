package com.abner.ming.abnerlibrary.net.service;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Administrator on 2018/3/7.
 */

public interface WeekApiService {
    @GET("/week/txt/week_day_news.txt")
    Observable<ResponseBody> getWeekDayList();
}
