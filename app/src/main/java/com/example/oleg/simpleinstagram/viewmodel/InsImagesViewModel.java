package com.example.oleg.simpleinstagram.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.graphics.Bitmap;
import android.net.Uri;
import com.example.oleg.simpleinstagram.InstagramClient;
import com.example.oleg.simpleinstagram.R;
import com.example.oleg.simpleinstagram.collagemaker.AbsCollage;
import com.example.oleg.simpleinstagram.collagemaker.PhotoBoothCollage;
import com.example.oleg.simpleinstagram.model.InsImage;
import com.example.oleg.simpleinstagram.model.User;
import com.example.oleg.simpleinstagram.view.ImagesListActivity;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by oleg on 20.05.16.
 */
public class InsImagesViewModel extends BaseObservable {
    ImagesListActivity mView;
    private static final int NUMBER_PHOTO_FOR_COLLAGE = 2;

    @Bindable
    public ObservableArrayList<InsImageViewModel> insImages;


    public InsImagesViewModel(ImagesListActivity view) {
        this.insImages = new ObservableArrayList<>();
        this.mView = view;
    }

    public void addImage (InsImage image) {
        this.insImages.add(new InsImageViewModel(image));
    }

    public void requestUserImages(User user) {
        final String url = String.format("users/%s/media/recent", user.getUserId());
        if (user == null ||
                user.getUserId().equalsIgnoreCase("") ||
                user.getUsername().equalsIgnoreCase("")) return;
        mView.showProgressDialog(mView.getString(R.string.load_images_begin_message));
        InstagramClient.getWithToken(url, null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray data = response.getJSONArray("data");
                    for (int i = 0; i<data.length(); i++){
                        InsImage image = new InsImage(data.getJSONObject(i));
                        addImage(image);
                        notifyChange();
                        mView.hideProgressDialog(R.string.message_end_request_photos);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                mView.hideProgressDialog(R.string.message_failure_request_photos);
            }
        });
    }

    public int getCount(){
        return insImages.size();
    }

    public int getCheckedCount(){
        int count = 0;
        for(InsImageViewModel image : insImages){
            if( image.getIsChecked()) count++;
        }
        return count;
    }

    public String getFormCaption(){
        return String.format("%d %s %d",
                getCheckedCount(),
                mView.getString(R.string.from),
                getCount());
    }

    public void makeCollage(){
        if (insImages.size() > NUMBER_PHOTO_FOR_COLLAGE) {
            final List<InsImage> checkedImages = getCheckedImages();
            mView.showProgressDialog(mView.getString(R.string.progress_dialog_message_create_collage));
            PhotoBoothCollage.makeSingleLineCollage(checkedImages,
                    mView.getApplicationContext(),
                    new AbsCollage.MakeCollageHandler() {
                        @Override
                        public void onSuccess(final Bitmap collage) {
                                mView.mUIThreadHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mView.hideProgressDialog();
                                    try {
                                        Uri collageUri = saveImage(collage);
                                        mView.startImagePreviewActivity(collageUri);
                                    } catch (IOException e) {
                                        mView.showToast(mView.getString(R.string.toast_message_failure_writing_to_file));

                                    }
                                }
                            });
                        }

                        @Override
                        public void onFailure(Throwable e) {
                            mView.hideProgressDialog(R.string.toast_message_failure_create_collage);
                        }
                    }
            );
        } else {
            mView.hideProgressDialog(R.string.toast_message_failure_small_amount_of_picture);
        }
    }

    public Uri saveImage(Bitmap image) throws IOException {
        String fileName = Long.toString(System.currentTimeMillis()) + ".jpg";
        File file = new File(mView.getApplicationContext().getFilesDir(), fileName);
        FileOutputStream out = new FileOutputStream(file);
        image.compress(Bitmap.CompressFormat.JPEG, 90, out);
        out.flush();
        out.close();

        return android.net.Uri.parse(file.toURI().toString());
    }

    public List<InsImage> getCheckedImages() {
        List checkedImages = new LinkedList();
            for (int i=0; i < insImages.size(); i++){
                InsImage currentImage = insImages.get(i).getImage();
                if (currentImage.isChecked()){
                    checkedImages.add(currentImage);
                }
            }
        return checkedImages;
    }
}
