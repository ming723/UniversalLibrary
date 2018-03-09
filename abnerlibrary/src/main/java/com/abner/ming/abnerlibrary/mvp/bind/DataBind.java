package com.abner.ming.abnerlibrary.mvp.bind;


import com.abner.ming.abnerlibrary.mvp.view.IDelegate;
import com.abner.ming.abnerlibrary.net.model.IModel;

/**
 * Created by AbnerMing on 2018/1/3.
 */

public interface DataBind<T extends IDelegate,D extends IModel> {

    void bindViewData(T t, D d);
}
