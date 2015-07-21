package com.magical.stickymapnav.http.server;

import android.content.Context;
import android.util.Log;

/**
 */
public class MHttpService {

    private static final String TAG = "GlassHttpService";

    private static MHttpService sharedInstance = new MHttpService();

    private MHttpRequestHelper mRequestHelper;
    private Context mContext;

    private MHttpService() {
    }

    public static MHttpService getInstance() {
        return sharedInstance;
    }

    public void init(Context context) {
        if (mContext != null) {
            Log.e(TAG, "This method can only be called in HoemApplication!!!");
            return;
        }
        mContext = context;
        mRequestHelper = new MHttpRequestHelper(mContext);
    }

    public int sendHttpGetRequest(String url, MHttpRequestParameters parameters, RequestListener listener) {
        return sendRequest(new MHttpRequest(MHttpRequest.REQUEST_METHOD_GET, url, parameters), listener);
    }

    public int sendHttpPostRequest(String url, MHttpRequestParameters parameters, RequestListener listener) {
        return sendRequest(new MHttpRequest(MHttpRequest.REQUEST_METHOD_POST, url, parameters), listener);
    }

    public int sendHttpPutRequest(String url, MHttpRequestParameters parameters, RequestListener listener) {
        return sendRequest(new MHttpRequest(MHttpRequest.REQUEST_METHOD_PUT, url, parameters), listener);
    }

    public int sendHttpDeleteRequest(String url, MHttpRequestParameters parameters, RequestListener listener) {
        return sendRequest(new MHttpRequest(MHttpRequest.REQUEST_METHOD_DELETE, url, parameters), listener);
    }

    private int sendRequest(MHttpRequest request, RequestListener listener) {

        if (listener == null || request == null)
            return -1;
        // init
        request.setRequestListener(listener);
        // start execute http request thread
        mRequestHelper.pushHttpRequest(request);
        return 1;
    }
}
