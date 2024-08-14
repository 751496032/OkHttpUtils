package com.hzw.okhttputils.request;




import com.hzw.okhttputils.media.MediaTypes;
import com.hzw.okhttputils.method.Method;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author HZWei
 * @date 2024/8/13
 * @desc
 */
public class PostFileRequestBuilder extends RequestBuilder {

    private File file;
    private String fileKey;
    private CountingRequestBody.Listener listener;


    public PostFileRequestBuilder setFileKey(String fileKey)   {
        this.fileKey = fileKey;
        return this;
    }

    public PostFileRequestBuilder setFile(File file) {
        this.file = file;
        return this;
    }

    public PostFileRequestBuilder setProgressListener(CountingRequestBody.Listener listener){
        this.listener = listener;
        return this;
    }

    public PostFileRequestBuilder setFile(String filePath) {
        this.file = new File(filePath);
        return this;
    }

    @Override
    protected Request buildRequest(Request.Builder builder) {


        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        for (String key: parameters.keySet()){
            multipartBuilder.addFormDataPart(key, parameters.get(key));
        }
        // file一定要放在最后，否则会导致文件上传失败
        String key = fileKey == null ? "file" : fileKey;
        multipartBuilder.addFormDataPart(key, file.getName(),
                RequestBody.create(MediaTypes.MEDIA_TYPE_MULTIPART_FORM, file));

        CountingRequestBody body = new CountingRequestBody(multipartBuilder.build(), this.listener);
        return buildRequestWithBody(builder, body, Method.POST);
    }




}
