package com.abner.ming.abnerlibrary.net.fragment;


import com.abner.ming.abnerlibrary.net.listener.HelperListener;
import com.abner.ming.abnerlibrary.net.listener.HttpJavaBeanCallBackListener;
import com.abner.ming.abnerlibrary.net.listener.HttpListener;
import com.abner.ming.abnerlibrary.net.model.IModel;

import java.util.Map;

/**
 * Created by Administrator on 2018/3/7.
 */

public class FragmentHttpHelper {
    private FragmentHttpUtils mHttpUtils;
    private  HttpListener listener;
    public FragmentHttpHelper(FragmentHttpUtils mHttpUtils, HttpListener listener){
        this.mHttpUtils=mHttpUtils;
        this. listener=  listener;
    }
    /**
     * GET获取String请求
     */
    public void httpGetString(final int type,String url,Map<String, String> map) {
        mHttpUtils.getString(url, map, new HelperListener() {
            @Override
            public void httpSuccess(String message) {
                listener.httpSuccess(type,message);
            }

            @Override
            public void httpFailure(String error) {
                listener.httpFailure(error);
            }
        });
    }
    /**
     * POST获取String请求
     */
    public void httpPostString(final int type, String url, Map<String, String> map) {
        mHttpUtils.postString(url, map, new HelperListener() {
            @Override
            public void httpSuccess(String message) {
                listener.httpSuccess(type,message);
            }

            @Override
            public void httpFailure(String error) {
                listener.httpFailure(error);
            }
        });
    }

    /**
     * GET获取JavaBean请求
     */
    public <D extends IModel> void httpGetJavaBean(final int type, D d, String url, Map<String, String> map) {
        mHttpUtils.getJavaBean(d.getClass(), url, map, new HttpJavaBeanCallBackListener() {
            @Override
            public void success(IModel iModel) {
                listener.httpJavaBeanSuccess(type,iModel);
            }

            @Override
            public void failure(String error) {
                listener.httpFailure(error);
            }
        });
    }

    /**
     * POST获取JavaBean请求
     */
    public <D extends IModel> void httpPostJavaBean(final int type, D d, String url, Map<String, String> map) {
        mHttpUtils.postJavaBean(d.getClass(), url, map, new HttpJavaBeanCallBackListener() {
            @Override
            public void success(IModel iModel) {
                listener.httpJavaBeanSuccess(type,iModel);
            }

            @Override
            public void failure(String error) {
                listener.httpFailure(error);
            }
        });
    }
}
