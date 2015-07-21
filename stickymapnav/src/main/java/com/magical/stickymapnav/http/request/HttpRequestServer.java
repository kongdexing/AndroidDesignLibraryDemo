package com.magical.stickymapnav.http.request;

/**
 * Created by kongdexing on 7/20/15.
 */
public class HttpRequestServer {

    private static HttpRequestServer mServer = new HttpRequestServer();
    private UserImpl userRequest = new UserImpl();

    private void HttpRequestServer() {
    }

    public static HttpRequestServer getInstance() {
        return mServer;
    }

    public UserImpl getUserRequest() {
        return userRequest;
    }

}
