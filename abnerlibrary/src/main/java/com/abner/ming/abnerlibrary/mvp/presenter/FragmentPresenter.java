package com.abner.ming.abnerlibrary.mvp.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abner.ming.abnerlibrary.base.BaseFragmentHttp;
import com.abner.ming.abnerlibrary.mvp.view.IDelegate;


/**
 * Created by AbnerMing on 2018/1/3.
 */

public abstract class FragmentPresenter<T extends IDelegate> extends BaseFragmentHttp {

    @Override
    protected View layoutView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        delegate.create(inflater, container, savedInstanceState);
        return delegate.getRootView();
    }

    @Override
    protected void initView() {
        delegate.initWeight();
        bindEnvsListener();
    }

    public T delegate;

    public abstract Class<T> getDelegateClass();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            delegate = getDelegateClass().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bindEnvsListener() {

    }
}
