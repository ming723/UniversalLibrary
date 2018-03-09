package com.abner.ming.abnerlibrary.net.Api;

import com.abner.ming.abnerlibrary.net.listener.HttpOnNextListener;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by Administrator on 2018/3/7.
 * Api管理类
 */
public abstract class HttpApi extends BaseApi{
    public static final int GETHTTP=0;
    public static final int POSTHTTP=1;
    public static final String HTTP_URL="http://www.weekandroid.cn";

    public HttpApi(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity) {
        super(listener, rxAppCompatActivity);
        setBaseUrl(HTTP_URL);
        setShowProgress(true);
        setCancel(true);
        setCache(true);
        setMethod("/week/txt/week_day_news.txt");
        setCookieNetWorkTime(60);
        setCookieNoNetWorkTime(24*60*60);
    }
    /**
     * 是否显示加载
     * */
    public void isProgress(boolean isShow){
        setShowProgress(isShow);
    }
    /**
     * 是否能取消加载
     * */
    public void isProgressCancle(boolean isShow){
        setCancel(isShow);
    }
    /**
     * 是否阅读缓存
     * */
    public void readCacle(boolean read){
        setCache(read);
    }

    /**
     *设置请求域名
     * */
    public void setUrl(String url){
        setBaseUrl(url);
    }
    /**
     * 设置请求接口，域名后面的
     * */
    public void setMethodUrl(String url){
        setMethod(url);
    }


}
