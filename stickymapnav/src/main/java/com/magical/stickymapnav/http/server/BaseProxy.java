package com.magical.stickymapnav.http.server;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kongdexing on 2/28/15.
 */
public class BaseProxy {

    private String TAG = BaseProxy.class.getSimpleName();
    public String result = "";

    public String handleResponseValue(Object value) {
        if (value instanceof byte[]) {
            byte[] data = (byte[]) value;
            result = new String(data);
        } else if (value instanceof String) {
            result = value.toString();
        }
        Log.i(TAG, "responseValue:" + result);
        return result;
    }


    /**
     * 解析访问服务器返回的错误码
     *
     * @param result
     * @return
     * @throws JSONException
     */
    protected int analyseErrorCode(String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        return Integer.parseInt(jsonObject.getString("code"));
    }

    /**
     * 解析访问服务器返回的错误信息
     *
     * @param result
     * @return
     * @throws JSONException
     */
    protected String analyseErrorText(String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        return jsonObject.getString("text");
    }

}
