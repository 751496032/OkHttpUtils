package com.hzw.okhttputils.request;


import static com.hzw.okhttputils.method.Method.DELETE;
import static com.hzw.okhttputils.method.Method.GET;
import static com.hzw.okhttputils.method.Method.POST;
import static com.hzw.okhttputils.method.Method.PUT;

import com.hzw.okhttputils.OkHttpUtils;
import com.hzw.okhttputils.callback.ICallback;
import com.hzw.okhttputils.callback.SyncResult;
import com.hzw.okhttputils.method.Method;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author HZWei
 * @date 2024/8/10
 * @desc
 */
public abstract class RequestBuilder {
    protected String url;
    protected Map<String, String> parameters = new HashMap<>();
    protected Request.Builder requsetBuilder = new Request.Builder();
    protected OkHttpClient client = OkHttpUtils.getInstance().getClient();
    protected Map<String, String> headers = new HashMap<>();



    public RequestBuilder url(String url) {
        this.url = url;
        return this;
    }

    public RequestBuilder parameters(Map<String, String> parameters) {
        this.parameters = parameters;
        return this;
    }


    public RequestBuilder addParameter(String name, String value) {
        if (this.parameters != null) {
            this.parameters.put(name, value);
        }
        return this;
    }


    public RequestBuilder addHeader(String name, String value) {
        if (this.headers != null) {
            this.headers.put(name, value);
        }
        return this;
    }

    public RequestBuilder headers(Map<String, String> headers){
        this.headers = headers;
        return this;
    }


    protected boolean isSuccessful(Response response) {
        return response.isSuccessful();
    }


    /**
     * 同步方法 在Android需要放在子线程调用
     * @return data
     */
    @Nullable
    protected SyncResult requestSync() {
        SyncResult syncResult = new SyncResult();
        try {
            Request request = buildRequest(getRequestBuilder());
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            if (isSuccessful(response)) {
                fillData(syncResult, response.code(), body != null ? body.string() : null);
            }else {
                String msg = "fail code: " + response.code() + " msg: " + response.message();
                fillData(syncResult, response.code(), msg);
            }
        } catch (Exception e) {
            fillData(syncResult, -1, e.getMessage());

        }
        return syncResult;

    }

    private Request.Builder getRequestBuilder() {
        for (String key : this.headers.keySet()) {
            String value = this.headers.get(key);
            if (value != null) {
                this.requsetBuilder.addHeader(key, value);
            }
        }
        return this.requsetBuilder;
    }

    protected Request buildRequestWithBody(Request.Builder builder,
                                           RequestBody body,
                                           Method method) {
        switch (method){
            case GET:
                return builder.url(url).get().build();
            case POST:
                return builder.url(url).post(body).build();
            case PUT:
                return builder.url(url).put(body).build();
            case DELETE:
                return builder.url(url).delete(body).build();
            default:
                return builder.url(url).build();
        }

    }

    private void fillData(SyncResult syncResult, int code, @Nullable String data) {
        syncResult.code = code;
        syncResult.data = data;
    }

    protected void request(ICallback callback){
        Request request = buildRequest(getRequestBuilder());
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.onFailure(-1, e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (isSuccessful(response)){
                        ResponseBody body = response.body();
                        if (body != null) {
                            callback.onSuccess(body.string());
                        } else {
                            callback.onSuccess(null);
                        }

                    }else {
                        callback.onFailure(response.code(), response.message());
                    }
            }
        });
    }

    protected abstract Request buildRequest(Request.Builder builder);

    public void execute(ICallback callback) {
        request(callback);
    }

    public SyncResult executeSync() {
        return requestSync();
    }


}
