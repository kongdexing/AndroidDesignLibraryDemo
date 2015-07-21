package com.magical.stickymapnav.http.server;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Ceyes-Device        |客户端匹配的设备类型，取值按照设备资源的类型定义，如`M100`、`C100`等 默认M100
 * Ceyes-PushType      |消息推送类型，取值为`CeyesMQTT`或`APNS`，无此值即为`CeyesMQTT`
 * Ceyes-ClientOS      |客户端平台类型，取值为`Android`或`iOS`，无此值即为`Android`
 * Ceyes-ClientType    |客户端类型，取值为`DeviceManager`或`Launcher`，无此值即为`DeviceManager`
 * Ceyes-ClientVersion |客户端版本，默认为""
 */
public class MHttpRequest {

    private String TAG = MHttpRequest.class.getSimpleName();
    public static final int REQUEST_TIME_OUT = 20 * 1000;

    public static final int REQUEST_METHOD_GET = 1;
    public static final int REQUEST_METHOD_POST = 2;
    public static final int REQUEST_METHOD_PUT = 3;
    public static final int REQUEST_METHOD_DELETE = 4;

    private RequestListener listener;

    private int mRequestType;
    private String mRequestUrl;
    private MHttpRequestParameters mParameters;

    public MHttpRequest(int mRequestType, String mRequestUrl, MHttpRequestParameters parameters) {
        this.mRequestType = mRequestType;
        this.mRequestUrl = mRequestUrl != null ? mRequestUrl.trim() : "";
        mParameters = parameters;
    }

    public RequestListener getRequestListener() {
        return listener;
    }

    public void setRequestListener(RequestListener listener) {
        this.listener = listener;
    }

    public HttpURLConnection getHttpConnection() {
        try {
            Log.i("URL", mRequestUrl);
            URL url = new URL(mRequestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (mParameters != null && mParameters.getRequestPropreties() != null) {
                Map<String, String> requestProperties = mParameters.getRequestPropreties();
                for (Map.Entry<String, String> entry : requestProperties.entrySet()) {
                    Log.i(TAG, "head key:" + entry.getKey() + "  head val:" + entry.getValue());
                    conn.addRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            conn.setReadTimeout(REQUEST_TIME_OUT);
            conn.setConnectTimeout(REQUEST_TIME_OUT);
            conn.setDoInput(true);

            if (mRequestType == REQUEST_METHOD_POST
                    || mRequestType == REQUEST_METHOD_PUT) {
                String boundary = "\r\n--MyMultipartBoundary\r\n";
                String end = "\r\n--MyMultipartBoundary--\r\n";
                String jsonContent = "Content-Type:application/json\r\n\r\n";

                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("boundary", "MyMultipartBoundary");
                if (mRequestType == REQUEST_METHOD_POST) {
                    conn.setRequestMethod("POST");
                } else
                    conn.setRequestMethod("PUT");

                DataOutputStream os = new DataOutputStream(
                        conn.getOutputStream());
                if (mParameters.getBodyContent() != null) {
                    Log.i(TAG, "parameter json :" + mParameters.getBodyContent().toString());
                }
                // add boundary and content type for contact upload
                if (mParameters.getIfNeedMultipartBoundary()) {
                    os.write(boundary.getBytes());
                    os.write(jsonContent.getBytes());
                }

                if (mParameters.getBodyContent() != null) {
                    os.write(mParameters.getBodyContent().getBytes());
                }

                if (mParameters.getmImgStream() != null) {
                    String imgType = mParameters.getmImgType() + "\r\n";
                    String imgContentEncoding = "Content-Encoding-Type:Binary\r\n\r\n";

                    os.write(boundary.getBytes());
                    os.write(imgType.getBytes());
                    os.write(imgContentEncoding.getBytes());
                    byte[] bts = InputStreamToByte(mParameters.getmImgStream());

                    os.write(bts);
                }

                // add end flag
                if (mParameters.getIfNeedMultipartBoundary()) {
                    os.write(end.getBytes());
                }

                os.flush();
            } else if (mRequestType == REQUEST_METHOD_GET) {
                conn.setRequestMethod("GET");
            } else if (mRequestType == REQUEST_METHOD_DELETE) {
                conn.setRequestMethod("DELETE");
            }
            conn.connect();
            // result
            return conn;
        } catch (IOException e) {
            Log.i("network", "connect error :" + e.getMessage());
            return null;
        }
    }

    private byte[] InputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        int ch;
        while ((ch = is.read()) != -1) {
            bytestream.write(ch);
        }
        byte data[] = bytestream.toByteArray();
        bytestream.close();
        return data;
    }

}
