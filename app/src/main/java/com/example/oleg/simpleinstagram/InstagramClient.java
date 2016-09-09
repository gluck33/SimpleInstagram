package com.example.oleg.simpleinstagram;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by oleg on 18.05.16.
 */
public class InstagramClient {
    private static final String CLIENT_ID =	    "a8a3192fc06a46e9be73c36f0681acf7";
    private static final String ACCESS_TOKEN  = "2106698288.a8a3192.8b1ac67197f44747800502a3f54c3646";
    private static final String BASE_URL  =     "https://api.instagram.com/v1/";
    public static final String RECENT_IMAGES_URL = "/media/recent/";
    public static final String URL_PREFIX = "/users/";
    private static AsyncHttpClient sClient = new AsyncHttpClient();

    private InstagramClient(){

    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        sClient.get(url, params, responseHandler);
    }

    public static void getWithToken(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        if (params == null){
            params = new RequestParams();
        }
        params.put("access_token", ACCESS_TOKEN);
        get(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public interface InstagramResponseHandler{
        public void onSuccess(Object model);
        public void onFailure();
    }

}
