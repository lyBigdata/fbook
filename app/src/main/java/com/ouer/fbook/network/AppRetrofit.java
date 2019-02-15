package com.ouer.fbook.network;

import android.os.Bundle;

import com.ouer.fbook.util.LogUtils;
import com.ouer.fbook.util.Splits;
import com.ouer.fbook.util.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by yan on 5/15/17.
 */

public class AppRetrofit {
    public static boolean DEBUG = false;

    private static boolean isRemote = false; //是否属于远程

    public static final String RESTAPI_AGENT_ONLINE_URL = "http://restapi.xuanwonainiu.com/";
    public static final String QF_RESTAPI_MDSCJ_URL = "http://qf-restapi.mdscj.com/";
    public static final String QF_BOS_API_AGENT_ONLINE_URL = "http://qf-bos.xuanwonainiu.com/";
    public static final String MALL_API_AGENT_ONLINE_URL = "http://mall.xuanwonainiu.com/";

    private static AppRetrofit appRetrofit;

    private Retrofit retrofit;
    private OkHttpClient client;

    public static AppRetrofit getInstance() {
        if (appRetrofit == null) {
            synchronized (AppRetrofit.class) {
                if (appRetrofit == null) {
                    appRetrofit = new AppRetrofit();
                }
            }
        }
        return appRetrofit;
    }

    private AppRetrofit() {
        OkHttpClient.Builder okhttpBuilder = new OkHttpClient.Builder().connectTimeout(20 * 1000, TimeUnit.MILLISECONDS).readTimeout(20 * 1000, TimeUnit.MILLISECONDS).retryOnConnectionFailure(true).addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtils.e("okHttp log", "Retrofit http data :log: " + message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY)).addInterceptor(dellInterceptor()).cookieJar(new CookieJar() {
            private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                for (Cookie cookie : cookies) {
                    LogUtils.e("Cookie", "saveFromResponse: " + cookie.toString());
                }
                cookieStore.put(url.host(), cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url.host());
                LogUtils.e("Cookie", "loadForRequest: ");
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        LogUtils.e("Cookie", "saveFromResponse: " + cookie.toString());
                    }
                }
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        });


        client = okhttpBuilder.build();

//        retrofit = new Retrofit.Builder().addConverterFactory(ScalarsConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(client).build();
    }


    public OkHttpClient getClient() {
        return client;
    }

    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }

    /**
     * okhttp Interceptor
     */
    private Interceptor dellInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows 7)")
                        .addHeader("token", "111").build();
//                request = resetUrl(request);//debug dell
                return chain.proceed(request);
            }
        };
    }


    public static RequestBody bodyWithParm(Object... args) {
        if (args.length % 2 != 0) {
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < args.length; i += 2) {
            if (args[i + 1] != null) {
                try {
                    if (args[i + 1] instanceof List) {
                        JSONArray jsonArray = new JSONArray();
                        for (Object obj : (List) args[1 + i]) {
                            jsonArray.put(obj);
                        }
                        jsonObject.put(Splits.with(args[i]), jsonArray);
                        continue;
                    }
                    jsonObject.put(Splits.with(args[i]), args[i + 1]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        String jsonData = jsonObject.toString();
        LogUtils.e("jsonData", "pushLoad: " + jsonData);
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonData);
    }
}
