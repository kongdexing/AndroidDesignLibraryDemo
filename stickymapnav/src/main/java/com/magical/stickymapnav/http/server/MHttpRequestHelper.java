package com.magical.stickymapnav.http.server;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhangsong on 1/12/15.
 */
public class MHttpRequestHelper {

    // core thread pool
    private static final int CORE_POOL_SIZE = 5;

    private ExecutorService mExecutorService;
    private MHttpRequestThread mRequestThread;

    private Context mContext;

    // thread factory
    private final ThreadFactory mThreadFactory = new ThreadFactory() {

        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            final Thread thread = new Thread(r, "#HTTP Work Thread" + mCount.getAndIncrement());
            thread.setDaemon(true);
            return thread;
        }
    };

    public MHttpRequestHelper(Context context) {
        mContext = context;
        mExecutorService = Executors.newFixedThreadPool(CORE_POOL_SIZE, mThreadFactory);
        mRequestThread = new MHttpRequestThread();
    }

    public void pushHttpRequest(MHttpRequest request) {
        // start execute the HTTP request target
        final MHttpRequestThread thread = mRequestThread.cloneRequestThread();
        if (thread == null) {
            return;
        }
        // push thread to pool
        thread.setParameter(mContext, request);
        mExecutorService.execute(thread);
    }

}
