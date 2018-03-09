package com.abner.ming.abnerlibrary.net.listener;


import com.abner.ming.abnerlibrary.net.model.IModel;

/**
 * Created by AbnerMing on 2018/1/2.
 * 请求返回JavaBean回调
 */

public interface HttpJavaBeanCallBackListener<D extends IModel> {
    void success(D d);
    void failure(String error);
}
