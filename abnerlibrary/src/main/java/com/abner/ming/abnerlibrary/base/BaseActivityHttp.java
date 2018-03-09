package com.abner.ming.abnerlibrary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.abner.ming.abnerlibrary.R;
import com.abner.ming.abnerlibrary.net.helper.HttpHelper;
import com.abner.ming.abnerlibrary.net.listener.HttpListener;
import com.abner.ming.abnerlibrary.net.model.IModel;
import com.abner.ming.abnerlibrary.net.utils.HttpUtils;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AbnerMing on 2018/1/3.
 * Activity中有网络请求可集成此类
 */

public abstract class BaseActivityHttp extends RxAppCompatActivity implements HttpListener {
    private RelativeLayout titleLayout;
    private TextView baseTitle, titleRight;
    private ImageView baseBack;
    private HttpHelper mHttpHelper;
    private HttpUtils mHttpUtils;

    protected abstract boolean showBack();

    protected abstract String baseTitle();

    protected abstract View layoutView(Bundle savedInstanceState);

    protected abstract void initData();

    /**
     * 设置标题
     */
    protected void setTitle(String title) {
        baseTitle.setText(title);
    }

    protected void setShowTitle(boolean isShow) {
        if (isShow) {
            titleLayout.setVisibility(View.GONE);
        } else {
            titleLayout.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        LinearLayout baseView = (LinearLayout) findViewById(R.id.base_view);
        titleLayout = (RelativeLayout) findViewById(R.id.base_layout_title);
        baseTitle = (TextView) findViewById(R.id.base_title);
        baseBack = (ImageView) findViewById(R.id.base_view_back);
        titleRight = (TextView) findViewById(R.id.base_title_right);
        View v = layoutView(savedInstanceState);
        baseView.addView(v);
        if (!TextUtils.isEmpty(baseTitle())) {
            baseTitle.setText(baseTitle());
        }
        if (showBack()) {
            baseBack.setVisibility(View.VISIBLE);
        } else {
            baseBack.setVisibility(View.GONE);
        }

        baseBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mHttpUtils=new HttpUtils(this);
        mHttpHelper = new HttpHelper(mHttpUtils, this);
        initData();
    }



    protected void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }


    private Map<String, String> headerMap = new HashMap<>();


    /**
     * 是否显示加载框
     */
    public void isHttpShow(boolean isShow) {
        createHttp(false,isShow);
    }

    /**
     * 都为true,缓存，加载框都执行
     * */
    public void createHttp(boolean read,boolean isShow){
        mHttpUtils=null;
        mHttpHelper=null;
        mHttpUtils=new HttpUtils(this,read,isShow);
        mHttpHelper = new HttpHelper(mHttpUtils, this);
    }

    /**
     * 是否显示缓存
     */
    public void isCache(boolean cache) {
        createHttp(cache,false);
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
