package com.hzw.okhttputils.request;


import com.hzw.okhttputils.media.MediaTypes;
import com.hzw.okhttputils.method.Method;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author HZWei
 * @date 2024/8/12
 * @desc
 */
public class PostRequestBuilder extends RequestBuilder {

    private boolean isForm = false;

    public PostRequestBuilder asForm(){
        isForm = true;
        return  this;
    }

    @Override
    protected Request buildRequest(Request.Builder builder) {
        RequestBody body = null;
        if (isForm) {
            FormBody.Builder fBuilder = new FormBody.Builder();
            for (String key : parameters.keySet()) {
                String value = parameters.get(key);
                if (value != null) {
                    fBuilder.add(key, value);
                }

            }
            body = fBuilder.build();
        } else {
            body = RequestBody.create(new JSONObject(parameters).toString(), MediaTypes.MEDIA_TYPE_JSON);

        }
        return buildRequestWithBody(builder, body, Method.POST);
    }

}
