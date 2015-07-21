package com.magical.stickymapnav.http.server;

import android.os.Build;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 */
public class MHttpRequestParameters implements BaseHttpRequestParameters {

    private static final String TAG = MHttpRequestParameters.class.getSimpleName();

    public static final String CLIENT_OS = "Android";
    public static final String CLIENT_TYPE = "DeviceManager";
    public static final String CLIENT_VERSION = "v1.2.0";
    public static final String CLIENT_DEVICE = "C100";
    public static final String CLIENT_PUSHTYPE = "CeyesMQTT";
    public static final String AUTH_KEY = "37c654ab9ec2caee80a21f0afe863afe";

    private String bodyContent;
    private Map<String, String> requestPropreties;
    private InputStream mImgStream = null;
    private String mImgType = null;
    private boolean mIsNeedMultipartBoundary = false;

    public String getBodyContent() {
        return bodyContent;
    }

    public MHttpRequestParameters() {
        requestPropreties = new HashMap<String, String>();

        /* add all default parameters */
        requestPropreties.put("Ceyes-ClientOS", CLIENT_OS);
        requestPropreties.put("Ceyes-ClientType", CLIENT_TYPE);
        requestPropreties.put("Ceyes-ClientVersion", CLIENT_VERSION);
        requestPropreties.put("Ceyes-Device", CLIENT_DEVICE);
        requestPropreties.put("Ceyes-PushType", CLIENT_PUSHTYPE);
    }

    public MHttpRequestParameters getVideoCallParameters() {

        requestPropreties.put("SerialNo", Build.SERIAL);
        requestPropreties.put(MHttpUrl.Authorization, AUTH_KEY);
        return this;
    }

    public MHttpRequestParameters setBodyContent(String bodyContent) {
        this.bodyContent = bodyContent;
        return this;
    }

    public MHttpRequestParameters setImgStream(InputStream inputStream) {
        mImgStream = inputStream;
        return this;
    }

    public InputStream getmImgStream() {
        return mImgStream;
    }

    public MHttpRequestParameters setImgType(String type) {
        mImgType = type;
        return this;
    }

    public String getmImgType() {
        return mImgType;
    }

    public MHttpRequestParameters setIfNeedMultipartBoundary(boolean b) {
        mIsNeedMultipartBoundary = b;
        return this;
    }

    @Override
    public MHttpRequestParameters clearRequestProperty() {
        return this;
    }

    public boolean getIfNeedMultipartBoundary() {
        return mIsNeedMultipartBoundary;
    }

    // add the request headers.
    public MHttpRequestParameters addRequestProperty(String key, String value) {
        requestPropreties.put(key, value);
        return this;
    }

    public Map<String, String> getRequestPropreties() {
        return requestPropreties;
    }

}
