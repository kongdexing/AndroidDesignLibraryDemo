package com.magical.stickymapnav.http.server;

/**
 * Created by zhangsong on 1/13/15.
 */
public interface IResponseListener {

    public void onStart();

    public void onRequestResponse(int code, Object value);
}
