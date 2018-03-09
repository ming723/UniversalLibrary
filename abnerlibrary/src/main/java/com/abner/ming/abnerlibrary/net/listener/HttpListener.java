package com.abner.ming.abnerlibrary.net.listener;

import com.abner.ming.abnerlibrary.net.model.IModel;

/**
 * Created by AbnerMing on 2018/1/3.
 */

public interface HttpListener {
     void httpSuccess(int type,String message);

    void httpFailure(String error);

    <D extends IModel> void httpJavaBeanSuccess(int type,D d);
}
