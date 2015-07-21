package com.magical.stickymapnav;

import android.app.Application;

import com.magical.stickymapnav.http.server.MHttpService;

/**
 * Created by kongdexing on 7/20/15.
 */
public class StickyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MHttpService.getInstance().init(this);
    }
}
