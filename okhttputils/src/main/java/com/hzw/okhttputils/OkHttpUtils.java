package com.hzw.okhttputils;


import com.hzw.okhttputils.request.GetRequestBuilder;
import com.hzw.okhttputils.request.PostFileRequestBuilder;
import com.hzw.okhttputils.request.PostRequestBuilder;
import com.hzw.okhttputils.request.RequestBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;


/**
 * @author HZWei
 * @date 2024/8/10
 * @desc
 */
public class OkHttpUtils {

    private  OkHttpClient client = null;

    private static final class OkHttpUtilsHolder {
        private static final OkHttpUtils instance = new OkHttpUtils();
    }

    public static OkHttpUtils getInstance() {
        return OkHttpUtilsHolder.instance;
    }

    public void setClient(OkHttpClient client){
        this.client = client;
    }

    public OkHttpClient getClient() {
        if (client == null) {
            client = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        Request request = chain.request();
                        return chain.proceed(request);
                    })
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .writeTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .build();
        }
        return this.client;
    }


    public static PostRequestBuilder post() {
        return new PostRequestBuilder();
    }

    public static PostFileRequestBuilder postFile() {
        return new PostFileRequestBuilder();
    }

    public static RequestBuilder get() {
        return new GetRequestBuilder();
    }





}
