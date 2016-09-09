package com.example.oleg.simpleinstagram.model;

import com.example.oleg.simpleinstagram.InstagramClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.example.oleg.simpleinstagram.InstagramClient.*;

public class User {
    private String mUsername;
    private String mId;

    public User(String username, String id) {
        this.mUsername = username;
        this.mId = id;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        this.mUsername = username;
    }

    public String getUserId() {
        return mId;
    }

    public void setUserId(String id) {
        this.mId = id;
    }



    public static void searchUser (final String username, final InstagramResponseHandler responseHandler){
        InstagramClient.getWithToken("users/self", null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONObject data = response.getJSONObject("data");
                    String username = data.getString("username");
                    String userId = data.getString("id");
                    User user = new User(username, userId);
                    responseHandler.onSuccess(user);

                } catch (JSONException e) {
                    e.printStackTrace();
                    responseHandler.onFailure();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                responseHandler.onFailure();
            }
        });
    }

}
