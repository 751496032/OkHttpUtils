package com.hzw.okhttputils.media;

import okhttp3.MediaType;

/**
 * @author HZWei
 * @date 2024/8/14
 * @desc
 */
public class MediaTypes {

    public static final MediaType MEDIA_TYPE_FORM = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");
    public static final MediaType MEDIA_TYPE_MULTIPART_FORM = MediaType.parse("multipart/form-data");
    public static final MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json;charset=utf-8");

}
