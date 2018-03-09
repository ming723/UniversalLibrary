package com.abner.ming.abnerlibrary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abner.ming.abnerlibrary.net.fragment.FragmentHttpHelper;
import com.abner.ming.abnerlibrary.net.fragment.FragmentHttpUtils;
import com.abner.ming.abnerlibrary.net.listener.HttpListener;
import com.abner.ming.abnerlibrary.net.model.IModel;
import com.trello.rxlifecycle.components.support.RxAppCompatDialogFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AbnerMing on 17/8/1.
 * Fragment有网络请求可集成此类
 */

public abstract class BaseFragmentHttp extends RxAppCompatDialogFragment implements HttpListener {
    private FragmentHttpHelper mHttpHelper;
    private FragmentHttpUtils mHttpUtils;

    protected abstract View layoutView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);
    protected abstract void initView();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = layoutView(inflater, container, savedInstanceState);
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHttpUtils=new FragmentHttpUtils(this);
        mHttpHelper = new FragmentHttpHelper(mHttpUtils, this);

        initView();
    }

    protected void toast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    private Map<String, String> headerMap = new HashMap<>();


    /**
     * 是否显示加载框
     */
    public void isHttpShow(boolean isShow) {
        createHttp(false,isShow);
    }
    /**
     * 是否显示缓存
     */
    public void isCache(boolean cache) {
        createHttp(cache,false);
    }
    /**
     * 都为true,缓存，加载框都执行
     * */
    public void createHttp(boolean read,boolean isShow){
        mHttpUtils=null;
        mHttpHelper=null;
        mHttpUtils=new FragmentHttpUtils(this);
        mHttpHelper = new FragmentHttpHelper(mHttpUtils, this);
    }
    /**
     * GET获取String请求
     */
    public void httpGetString(int type, String url, Map<String, String> map) {
        mHttpHelper.httpGetString(type,url,map);
    }

    /**
     * POST获取String请求
     */
    public void httpPostString(int type,String url, Map<String, String> map) {
        mHttpHelper.httpPostString(type,url,map);
    }

    /**
     * GET获取JavaBean请求
     */
    public <D extends IModel> void httpGetJavaBean(int type,D d,String url, Map<String, String> map) {
        mHttpHelper.httpGetJavaBean(type,d,url,map);
    }

    /**
     * post获取JavaBean请求
     */
    public <D extends IModel> void httpPostJavaBean(int type,D d,String url, Map<String, String> map) {
        mHttpHelper.httpPostJavaBean(type,d,url,map);
    }


    /**
     * 请求成功
     */
    public void httpSuccess(int type, String message) {

    }

    /**
     * 请求失败
     */
    public void httpFailure(String error) {

    }

    public <D extends IModel> void httpJavaBeanSuccess(int type, D d) {

    }
}
