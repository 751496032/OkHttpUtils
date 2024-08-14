package com.hzw.okhttputils.callback;

/**
 * @author HZWei
 * @date 2024/8/12
 * @desc
 */
public interface ICallback {


    void onSuccess(String response);

    void onFailure(int code, String message);

}
