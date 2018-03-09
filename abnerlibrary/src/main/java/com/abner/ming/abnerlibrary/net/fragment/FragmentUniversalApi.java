package com.abner.ming.abnerlibrary.net.fragment;

import com.abner.ming.abnerlibrary.net.Api.HttpApi;
import com.abner.ming.abnerlibrary.net.listener.HttpOnNextListener;
import com.abner.ming.abnerlibrary.net.service.RetrofitService;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle.components.support.RxAppCompatDialogFragment;

import java.util.Map;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by AbnerMing on 2018/3/7.
 * get  post请求
 */

public class FragmentUniversalApi extends FragmentHttpApi{
    private int type;
    private String url;
    private Map<String, String> map;
    public FragmentUniversalApi(HttpOnNextListener listener,
                                RxAppCompatDialogFragment rxAppCompatActivity, int type, String url, Map<String, String> map) {
        super(listener, rxAppCompatActivity);
        this.type=type;
        this.url=url;
        this.map=map;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        RetrofitService service = retrofit.create(RetrofitService.class);
        if(type==GETHTTP){
            return service.getRxRequest(url,map);
        }else{
            return service.postRxRequest(url,map);
        }
    }
}
