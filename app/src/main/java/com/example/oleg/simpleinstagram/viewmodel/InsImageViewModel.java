package com.example.oleg.simpleinstagram.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.example.oleg.simpleinstagram.R;
import com.example.oleg.simpleinstagram.model.InsImage;
import com.squareup.picasso.Picasso;

/**
 * Created by oleg on 20.05.16.
 */
public class InsImageViewModel extends BaseObservable {

    private final InsImage image;

    public InsImageViewModel(InsImage image) {

        this.image = image;
    }

    @Bindable
    public InsImage getImage() {
        return image;
    }

    public String getCreatedTime() {
        return image.getmCreatedTime();
    }

    public String getCaption() {
        return image.getmCaption();
    }

    public String getLikesCount() {
        return String.valueOf(image.getLikesCount());
    }


    public boolean getIsChecked() {
        return image.isChecked();
    }


    public String getTumbnail() {
        return image.getTumbnailUrl();
    }


    public void changeCheck() {
        image.setChecked(!image.isChecked());
        notifyChange();
    }

    @BindingAdapter({"bind:imageUrl", "bind:error"})
    public static void loadImage(ImageView view, String url, Drawable error) {
        Picasso.with(view.getContext())
                .load(url)
                .error(error)
                .into(view);

    }

    @BindingAdapter("imageResource")
    public static void setImageResource(ImageView view, boolean isChecked) {
        if (isChecked) view.setImageResource(R.mipmap.check_1_icon);
        else view.setImageResource(R.mipmap.check_0_icon);
    }
}
