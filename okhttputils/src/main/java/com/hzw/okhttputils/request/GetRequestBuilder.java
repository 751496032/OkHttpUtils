package com.hzw.okhttputils.request;


import com.hzw.okhttputils.method.Method;

import java.util.Objects;
import java.util.Set;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * @author HZWei
 * @date 2024/8/12
 * @desc
 */
public class GetRequestBuilder extends RequestBuilder {


    @Override
    protected Request buildRequest(Request.Builder builder) {
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
        Set<String> keySet = parameters.keySet();
        for (String key : keySet) {
            String value = parameters.get(key);
            if (value != null) {
                urlBuilder.addQueryParameter(key, value);
            }
        }
        url = urlBuilder.build().toString();
        return buildRequestWithBody(builder, null, Method.GET);
    }


}
