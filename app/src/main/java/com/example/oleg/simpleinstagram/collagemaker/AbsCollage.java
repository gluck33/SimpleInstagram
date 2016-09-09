package com.example.oleg.simpleinstagram.collagemaker;

import android.graphics.Bitmap;

/**
 * Created by oleg on 27.05.16.
 */
public abstract class AbsCollage {
     public interface MakeCollageHandler{
        void onSuccess(Bitmap collage);
        void onFailure(Throwable e);
    }

}
