package com.abner.ming.abnerlibrary.net.listener;

/**
 * Created by AbnerMing on 2018/1/3.
 */

public interface HelperListener {
     void httpSuccess(String message);
    void httpFailure( String error);

}
