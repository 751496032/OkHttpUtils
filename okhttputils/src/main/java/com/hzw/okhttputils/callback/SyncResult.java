package com.hzw.okhttputils.callback;


/**
 * @author HZWei
 * @date 2024/8/13
 * @desc
 */
public class SyncResult {

    public String data;
    public int code;

    public SyncResult() {
    }

    public SyncResult(int code, String result) {
        this.code = code;
        this.data = result;
    }

    public boolean isSuccessful() {
        return code >= 200 && code < 300;
    }


    @Override
    public String toString() {
        return data;
    }
}
