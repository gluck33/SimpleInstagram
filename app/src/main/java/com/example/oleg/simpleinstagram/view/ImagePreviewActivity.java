package com.example.oleg.simpleinstagram.view;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.oleg.simpleinstagram.R;
import com.example.oleg.simpleinstagram.databinding.ActivityImagePreviewBinding;
import com.example.oleg.simpleinstagram.model.CollageImage;
import com.squareup.picasso.Picasso;

import java.io.File;

public class ImagePreviewActivity extends AppCompatActivity {
    private CollageImage mCollage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Uri collageUri = intent.getParcelableExtra("com.com.example.oleg.appkodetest.COLLAGE_URI");
        mCollage = new CollageImage(collageUri);
        ActivityImagePreviewBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_image_preview);
        binding.setCollageModel(mCollage);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        binding.toolbar.setTitleTextColor(getResources().getColor(R.color.colorTollbarText));
    }

    public void onShareButtonClick(View view) {
        PackageManager pm = getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo("com.vkontakte.android", 0);
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(ImagePreviewActivity.this, getString(R.string.package_vkontakte_not_found), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        if (pi != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("image/jpeg");
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(mCollage.getUri())));
            shareIntent.setPackage("com.vkontakte.android");
            startActivity(shareIntent);
        }
    }

    @BindingAdapter({"bind:localUrl", "bind:error"})
    public static void loadImage(ImageView view, String url, Drawable error){
        Picasso.with(view.getContext())
                .load(url)
                .error(error)
                .into(view);
    }

}
