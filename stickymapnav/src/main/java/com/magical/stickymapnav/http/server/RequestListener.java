package com.magical.stickymapnav.http.server;


/**
 * Get a interceptor from the service layer is generally achieved by the
 * activity
 */
public class RequestListener {

    // interceptor state
    public static final int NETWORK_UNCONNECTED = 909;
    private static IResponseListener callback = null;

    public RequestListener(IResponseListener response) {
        callback = response;
    }

    public void onStart() {
        if (callback != null) {
            callback.onStart();
        }
    }

    public void onResponse(int code, Object value) {

    }

}
