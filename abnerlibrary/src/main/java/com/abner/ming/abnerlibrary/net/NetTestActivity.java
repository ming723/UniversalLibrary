package com.abner.ming.abnerlibrary.net;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.abner.ming.abnerlibrary.R;
import com.abner.ming.abnerlibrary.dialog.DialogLoading;
import com.abner.ming.abnerlibrary.net.Api.BaseResultEntity;
import com.abner.ming.abnerlibrary.net.Api.SubjectPostApi;
import com.abner.ming.abnerlibrary.net.download.DownInfo;
import com.abner.ming.abnerlibrary.net.download.HttpDownManager;
import com.abner.ming.abnerlibrary.net.http.HttpManager;
import com.abner.ming.abnerlibrary.net.listener.HelperListener;
import com.abner.ming.abnerlibrary.net.listener.HttpDownOnNextListener;
import com.abner.ming.abnerlibrary.net.listener.HttpJavaBeanCallBackListener;
import com.abner.ming.abnerlibrary.net.listener.HttpOnNextListener;
import com.abner.ming.abnerlibrary.net.listener.upload.ProgressRequestBody;
import com.abner.ming.abnerlibrary.net.listener.upload.UploadProgressListener;
import com.abner.ming.abnerlibrary.net.model.IModel;
import com.abner.ming.abnerlibrary.net.test.HttpPostService;
import com.abner.ming.abnerlibrary.net.test.RetrofitEntity;
import com.abner.ming.abnerlibrary.net.test.SubjectResulte;
import com.abner.ming.abnerlibrary.net.test.WeekNewsBean;
import com.abner.ming.abnerlibrary.net.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by AbnerMing on 2018/3/7.
 * 联网测试
 */

public class NetTestActivity extends RxAppCompatActivity implements View.OnClickListener {
    private HttpPostService apiService;
    private TextView tvMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nettest);
        tvMessage=(TextView)findViewById(R.id.net_message);
        findViewById(R.id.net_get).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.net_get){
            getJavaBean();
        }
    }


    private void getString(){
        new HttpUtils(this,true,false).getString("/week/txt/week_day_news.txt", null, new HelperListener() {
            @Override
            public void httpSuccess(String message) {
                tvMessage.setText(message);
            }

            @Override
            public void httpFailure(String error) {

            }
        });
    }
    private void getJavaBean(){
        new HttpUtils(this).getJavaBean(WeekNewsBean.class, "/week/txt/week_day_news.txt", null, new HttpJavaBeanCallBackListener() {
            @Override
            public void success(IModel iModel) {
                WeekNewsBean bean=(WeekNewsBean)iModel;
                tvMessage.setText(bean.getDatas().size()+"");
            }

            @Override
            public void failure(String error) {

            }
        });
    }


   /** ----------------------------- 完美封装简化版----------------------------------------------- */
    private void simpleDo() {
        SubjectPostApi postEntity = new SubjectPostApi(simpleOnNextListener,this);
        postEntity.setBaseUrl("https://www.izaodao.com/Api/");
        postEntity.setAll(true);
        HttpManager manager = HttpManager.getInstance();
        manager.doHttpDeal(postEntity);
    }

    //   回调一一对应
    HttpOnNextListener simpleOnNextListener = new HttpOnNextListener<List<SubjectResulte>>() {
        @Override
        public void onNext(List<SubjectResulte> subjects) {
            tvMessage.setText("网络返回：\n" + subjects.toString());
        }

        @Override
        public void onCacheNext(String cache) {
            /*缓存回调*/
            Gson gson=new Gson();
            java.lang.reflect.Type type = new TypeToken<BaseResultEntity<List<SubjectResulte>>>() {}.getType();
            BaseResultEntity resultEntity= gson.fromJson(cache, type);
            tvMessage.setText("缓存返回：\n"+resultEntity.getData().toString() );
        }

        /*用户主动调用，默认是不需要覆写该方法*/
        @Override
        public void onError(Throwable e) {
            super.onError(e);
            tvMessage.setText("失败：\n" + e.toString());
        }

        /*用户主动调用，默认是不需要覆写该方法*/
        @Override
        public void onCancel() {
            super.onCancel();
            tvMessage.setText("取消請求");
        }
    };


    /**
     * 正常请求----没有封装下
     * Retrofit加入rxjava实现http请求
     */
    private void onButton9Click() {
        String BASE_URL="http://www.izaodao.com/Api/";
        //手动创建一个OkHttpClient并设置超时时间
        okhttp3.OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.SECONDS);
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        final   DialogLoading pd=new DialogLoading.Builder(this).alertBg().create();
        HttpPostService apiService = retrofit.create(HttpPostService.class);
        Observable<RetrofitEntity> observable = apiService.getAllVedioBy(true);
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Subscriber<RetrofitEntity>() {
                            @Override
                            public void onCompleted() {
                                if (pd != null && pd.isShowing()) {
                                    pd.dismiss();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (pd != null && pd.isShowing()) {
                                    pd.dismiss();
                                }
                            }

                            @Override
                            public void onNext(RetrofitEntity retrofitEntity) {
                                tvMessage.setText("无封装：\n" + retrofitEntity.getData().toString());
                            }

                            @Override
                            public void onStart() {
                                super.onStart();
                                pd.show();
                            }
                        }

                );
    }

    private void uploadeDo(){
        File file=new File("/storage/emulated/0/Download/11.jpg");
        RequestBody requestBody=RequestBody.create(MediaType.parse("image/jpeg"),file);
        MultipartBody.Part part= MultipartBody.Part.createFormData("file_name", file.getName(), new ProgressRequestBody(requestBody,
                new UploadProgressListener() {
                    @Override
                    public void onProgress(final long currentBytesCount, final long totalBytesCount) {

                /*回到主线程中，可通过timer等延迟或者循环避免快速刷新数据*/
                        Observable.just(currentBytesCount).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {

                            @Override
                            public void call(Long aLong) {
//                                tvMsg.setText("提示:上传中");
//                                progressBar.setMax((int) totalBytesCount);
//                                progressBar.setProgress((int) currentBytesCount);
                            }
                        });
                    }
                }));
//        UploadApi uplaodApi = new UploadApi(httpOnNextListener,this);
//        uplaodApi.setPart(part);
//        HttpManager manager = HttpManager.getInstance();
//        manager.doHttpDeal(uplaodApi);

    }

    /**
     * 上传回调
     */
//    HttpOnNextListener httpOnNextListener=new HttpOnNextListener<UploadResulte>() {
//        @Override
//        public void onNext(UploadResulte o) {
//            tvMsg.setText("成功");
//            Glide.with(MainActivity.this).load(o.getHeadImgUrl()).skipMemoryCache(true).into(img);
//        }
//
//        @Override
//        public void onError(Throwable e) {
//            super.onError(e);
//            tvMsg.setText("失败："+e.toString());
//        }
//
//    };

    private void downLoad(){
        HttpDownManager manager= HttpDownManager.getInstance();
        DownInfo info=new DownInfo();
         /*第一次恢复 */
        switch (info.getState()){
            case START:
                /*起始状态*/
                break;
            case PAUSE:
               // tvMsg.setText("暂停中");
                break;
            case DOWN:
               // manager.startDown(apkApi);
                break;
            case STOP:
             //   tvMsg.setText("下载停止");
                break;
            case ERROR:
            //    tvMsg.setText("下載錯誤");
                break;
            case  FINISH:
             //   tvMsg.setText("下载完成");
                break;
        }
        info.setListener(httpProgressOnNextListener);
        manager.startDown(info);
    }
    /*下载回调*/
    HttpDownOnNextListener<DownInfo> httpProgressOnNextListener=new HttpDownOnNextListener<DownInfo>() {
        @Override
        public void onNext(DownInfo baseDownEntity) {
           // tvMsg.setText("提示：下载完成");
           // Toast.makeText(getContext(),baseDownEntity.getSavePath(),Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStart() {
          //  tvMsg.setText("提示:开始下载");
        }

        @Override
        public void onComplete() {
          //  tvMsg.setText("提示：下载结束");
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
           // tvMsg.setText("失败:"+e.toString());
        }


        @Override
        public void onPuase() {
            super.onPuase();
           // tvMsg.setText("提示:暂停");
        }

        @Override
        public void onStop() {
            super.onStop();
        }

        @Override
        public void updateProgress(long readLength, long countLength) {
          //  tvMsg.setText("提示:下载中");
          //  progressBar.setMax((int) countLength);
          //  progressBar.setProgress((int) readLength);
        }
    };
}
