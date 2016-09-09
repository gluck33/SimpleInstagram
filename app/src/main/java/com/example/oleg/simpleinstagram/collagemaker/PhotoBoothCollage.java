package com.example.oleg.simpleinstagram.collagemaker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.oleg.simpleinstagram.model.InsImage;
import java.util.List;

/**
 * Created by oleg on 27.05.16.
 */
public class PhotoBoothCollage extends AbsCollage {

    private static final int SPACING_BETWEEN_PHOTOS = 40;//px
    private static final int BACKGROUND_COLOR = Color.WHITE;
    public static void makeSingleLineCollage(final List<InsImage> images, final Context context, final MakeCollageHandler completionHandler){

        if (images.size() == 0) {
            throw new IllegalArgumentException("Must be at least one image");
        }
        InsImage firstImage = images.get(0);
        int imagesWidth = firstImage.getStandardWidth();
        for (InsImage image : images) {
            if (image.getStandardWidth() != imagesWidth) {
                throw new IllegalArgumentException("Pictures must have the same width");
            }
        }

        int collageWith = imagesWidth + 2 * SPACING_BETWEEN_PHOTOS;
        int collageHeight = SPACING_BETWEEN_PHOTOS;
        for (InsImage image : images) {
            collageHeight += image.getStandardHeight() + SPACING_BETWEEN_PHOTOS;
        }

        class IntegerContainer {
            public int integer;

        }
        final IntegerContainer currentImageVerticalCoord = new IntegerContainer();
        currentImageVerticalCoord.integer = SPACING_BETWEEN_PHOTOS;

        final Bitmap collageBitmap = Bitmap.createBitmap(collageWith, collageHeight, Bitmap.Config.ARGB_8888);
        collageBitmap.eraseColor(BACKGROUND_COLOR);
        final Canvas collageCanvas = new Canvas(collageBitmap);
        final int[] i = {0};
           for(InsImage image:images)
            {
                String imageUrl = image.getStandardUrl();
                Glide.with(context)
                        .load(imageUrl)
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                collageCanvas.drawBitmap(resource, SPACING_BETWEEN_PHOTOS, currentImageVerticalCoord.integer, null);
                                currentImageVerticalCoord.integer += resource.getHeight() + SPACING_BETWEEN_PHOTOS;
                                i[0]++;
                                Log.i("PhotoBoothCollage","Load: " + i[0] + "image.");
                                if (i[0] == images.size())
                                    completionHandler.onSuccess(collageBitmap);
                            }
                        });
            }
    }

}
