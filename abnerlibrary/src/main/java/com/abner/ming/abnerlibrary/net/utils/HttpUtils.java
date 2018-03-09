package com.abner.ming.abnerlibrary.net.utils;

import com.abner.ming.abnerlibrary.net.Api.HttpApi;
import com.abner.ming.abnerlibrary.net.Api.UniversalApi;
import com.abner.ming.abnerlibrary.net.http.HttpManager;
import com.abner.ming.abnerlibrary.net.listener.HelperListener;
import com.abner.ming.abnerlibrary.net.listener.HttpJavaBeanCallBackListener;
import com.abner.ming.abnerlibrary.net.listener.HttpOnNextListener;
import com.abner.ming.abnerlibrary.net.model.IModel;
import com.abner.ming.abnerlibrary.utils.NetworkUtils;
import com.google.gson.Gson;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;

/**
 * Created by AbnerMing on 2018/3/7.
 */

public  class HttpUtils{
    private RxAppCompatActivity activity;
    public  HttpUtils(RxAppCompatActivity activity){
      this.activity=activity;
    }
    private boolean isReadCache=true,isShow=true;
    public  HttpUtils(RxAppCompatActivity activity,boolean isReadCache,boolean isShow){
        this.activity=activity;
        this.isReadCache=isReadCache;
        this.isShow=isShow;
    }
    private HelperListener listener;

    /**
     * get获取字符串
     * */
    public void getString(String url, Map<String,String> map, HelperListener listener){
        this.listener=listener;
        if(map==null){
            map=new HashMap<>();
        }
        doMethod(url,map,HttpApi.GETHTTP);
    }
    /**
     * post获取字符串
     * */
    public void postString(String url, Map<String,String> map, HelperListener listener){
        this.listener=listener;
        if(map==null){
            map=new HashMap<>();
        }
        doMethod(url,map,HttpApi.POSTHTTP);
    }

    private void doMethod(String url, Map<String,String> map,int type){
        UniversalApi getEntity = new UniversalApi(simpleOnNextListener,activity, type,url,map);
        getEntity.readCacle(isReadCache);
        getEntity.isProgress(isShow);
        HttpManager manager = HttpManager.getInstance();
        manager.doHttpDeal(getEntity);
    }

    HttpOnNextListener simpleOnNextListener = new HttpOnNextListener<ResponseBody>() {
        @Override
        public void onNext(ResponseBody responseBody) {
            try {
                listener.httpSuccess(responseBody.string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onCacheNext(String string) {
            super.onCacheNext(string);
            listener.httpSuccess(string);
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            listener.httpFailure(NetworkUtils.networkMessage);
        }
    };


    private HttpJavaBeanCallBackListener javaBeanCallBackListener;
    private Class mClassJava;
    /**
     * get获取JavaBean
     * */
    public <D extends IModel> void getJavaBean(Class mClass, final String url, Map<String, String> map,  HttpJavaBeanCallBackListener listener) {
        javaBeanCallBackListener=listener;
        mClassJava=mClass;
        if(map==null){
            map=new HashMap<>();
        }
        doJavaBeanMethod(url,map,HttpApi.GETHTTP);
    }
    /**
     * POST获取JavaBean
     * */
    public <D extends IModel> void postJavaBean(Class mClass, final String url, Map<String, String> map,  HttpJavaBeanCallBackListener listener) {
        javaBeanCallBackListener=listener;
        mClassJava=mClass;
        if(map==null){
            map=new HashMap<>();
        }
        doJavaBeanMethod(url,map,HttpApi.POSTHTTP);
    }
    private  <D extends IModel> void doJavaBeanMethod(String url, Map<String,String> map,int type){
        UniversalApi getEntity = new UniversalApi(new HttpOnNextListener<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String result = "{'datas':" + responseBody.string() + "}";
                   D clss = (D) new Gson().fromJson(result, mClassJava);
                   javaBeanCallBackListener.success(clss);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCacheNext(String string) {
                super.onCacheNext(string);
                String result = "{'datas':" + string + "}";
                D clss = (D) new Gson().fromJson(result, mClassJava);
                javaBeanCallBackListener.success(clss);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                javaBeanCallBackListener.failure(NetworkUtils.networkMessage);
            }
        }, activity, type, url, map);
        getEntity.readCacle(isReadCache);
        getEntity.isProgress(isShow);
        HttpManager manager = HttpManager.getInstance();
        manager.doHttpDeal(getEntity);
    }

}
