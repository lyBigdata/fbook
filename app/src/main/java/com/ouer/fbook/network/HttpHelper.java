package com.ouer.fbook.network;


import android.util.Log;

import com.ouer.fbook.network.bean.IBaseDataView;
import com.ouer.fbook.util.LogUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class HttpHelper {

    public static Disposable getUrl(String url, final IBaseDataView<String> iBaseDataView) {
        Disposable disposable = null;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url) // 设置网络请求的Url地址
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava平台
                .client(AppRetrofit.getInstance().getClient())
                .build();

        GetRequestInterface request = retrofit.create(GetRequestInterface.class);

        Observable<String> observable = request.getUrl();
        disposable = observable.subscribeOn(Schedulers.io()) // 在子线程中进行Http访问
                .observeOn(AndroidSchedulers.mainThread()) // UI线程处理返回接口
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        iBaseDataView.onGetDataSuccess(s, 0, null);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        iBaseDataView.onGetDataFail(1, throwable.getMessage());
                    }
                });
        iBaseDataView.addDisposable(disposable);

        return disposable;
    }


    public static Disposable getOkhttp(final String url, final IBaseDataView<String> iBaseDataView) {

        Disposable disposable = Flowable.fromCallable(new Callable<String>() {

            @Override
            public String call() throws Exception {
                return syncGetOkhttp(url);
            }
        }).subscribeOn(Schedulers.io()) // 在子线程中进行Http访问
        .observeOn(AndroidSchedulers.mainThread()) // UI线程处理返回接口
        .subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                iBaseDataView.onGetDataSuccess(s, 0, null);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                iBaseDataView.onGetDataFail(1, throwable.getMessage());
            }
        });
        iBaseDataView.addDisposable(disposable);

        return disposable;
    }


    public static String syncGetOkhttp(final String url) {
        final Request request=new Request.Builder().url(url)
                .addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows 7)")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .addHeader("Connection", "Keep-Alive")
                .build();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        try {
            Response response=okHttpClient.newCall(request).execute();
            if (response.isSuccessful()){
                String body=response.body().string();
                String headers=response.headers().toString();
                LogUtils.i("zfq", "run: " + body + "  headers"+headers);
                return body;
            }else {
                LogUtils.e("zfq", "run: "+response.code()+response.message());
            }
            LogUtils.i("zfq", "run: ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String streamToString(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            is.close();
            byte[] byteArray = baos.toByteArray();
            return new String(byteArray);
        } catch (Exception e) {
            Log.e("TAG", e.toString());
            return null;
        }
    }
}
