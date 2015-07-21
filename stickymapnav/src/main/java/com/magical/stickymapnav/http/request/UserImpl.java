package com.magical.stickymapnav.http.request;

import android.util.Log;

import com.magical.stickymapnav.http.server.BaseProxy;
import com.magical.stickymapnav.http.server.MHttpService;
import com.magical.stickymapnav.http.server.MHttpUrl;
import com.magical.stickymapnav.http.server.RequestListener;

/**
 * Created by kongdexing on 7/20/15.
 */
public class UserImpl extends BaseProxy implements IUserRequest {

    private String TAG = UserImpl.class.getSimpleName();

    @Override
    public void getUserInfo() {
        MHttpService.getInstance().sendHttpGetRequest(MHttpUrl.userServer, null, new RequestListener(null) {
            @Override
            public void onResponse(int code, Object value) {
                super.onResponse(code, value);
                Log.i(TAG, "getUserInfo  " + handleResponseValue(value));
            }
        });
    }
}
