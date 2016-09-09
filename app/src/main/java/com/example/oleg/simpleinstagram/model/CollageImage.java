package com.example.oleg.simpleinstagram.model;

import android.net.Uri;

/**
 * Created by oleg on 27.05.16.
 */
public class CollageImage {
    public static final String COLLAGE_URI_KEY = "com.com.example.oleg.appkodetest.COLLAGE_URI";
    private Uri mUri;

    public CollageImage(Uri mUri) {
        this.mUri = mUri;
    }

    public String getUri() {
        return mUri.toString();
    }

    public void setUri(Uri mUri) {
        this.mUri = mUri;
    }
}
