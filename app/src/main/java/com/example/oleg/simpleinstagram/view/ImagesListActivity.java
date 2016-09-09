package com.example.oleg.simpleinstagram.view;
import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;


import com.example.oleg.simpleinstagram.BR;
import com.example.oleg.simpleinstagram.R;
import com.example.oleg.simpleinstagram.binder.ImageItemBinder;
import com.example.oleg.simpleinstagram.databinding.ActivityImagesListBinding;
import com.example.oleg.simpleinstagram.model.CollageImage;
import com.example.oleg.simpleinstagram.model.User;
import com.example.oleg.simpleinstagram.viewmodel.InsImageViewModel;
import com.example.oleg.simpleinstagram.viewmodel.InsImagesViewModel;

import net.droidlabs.mvvm.recyclerview.adapter.ClickHandler;
import net.droidlabs.mvvm.recyclerview.adapter.binder.CompositeItemBinder;
import net.droidlabs.mvvm.recyclerview.adapter.binder.ItemBinder;


public class ImagesListActivity extends AppCompatActivity {

    private InsImagesViewModel insImagesViewModel;
    private ActivityImagesListBinding binding;
    private User user;
    public Handler mUIThreadHandler;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUIThreadHandler = new Handler();
        insImagesViewModel = new InsImagesViewModel(this);
        Intent intent = getIntent();
        String user_id = intent.getStringExtra(UserFindActivity.USER_ID_EXTRA_PARAM);
        String username = intent.getStringExtra(UserFindActivity.USER_NAME_EXTRA_PARAM);
        user=new User(username,user_id);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_images_list);
        binding.setImagesView(this);
        binding.setInsImagesViewModel(insImagesViewModel);
        setSupportActionBar(binding.toolbar);
//        binding.toolbar.setTitleTextColor(getResources().getColor(R.color.colorTollbarText));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.imagesListRecycler.setLayoutManager(new LinearLayoutManager(this));
        insImagesViewModel.requestUserImages(user);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public ClickHandler<InsImageViewModel> clickHandler()
    {
        return new ClickHandler<InsImageViewModel>()
        {
            @Override
            public void onClick(InsImageViewModel viewModel) {
                viewModel.changeCheck();
                insImagesViewModel.notifyChange();
            }
        };
    }


    public ItemBinder<InsImageViewModel> itemViewBinder()
    {
        return new CompositeItemBinder<InsImageViewModel>(
                new ImageItemBinder(BR.InsImageViewModel, R.layout.imagelist_item)
        );
    }

    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT);
    }

    public void showToast(int messageId) {
        Toast.makeText(this, getText(messageId), Toast.LENGTH_SHORT);
    }

    public void showProgressDialog(int stringResourceId){
        showProgressDialog(getString(stringResourceId));
    }

    public void showProgressDialog(final String message){
//        mUIThreadHandler = new Handler();
        mUIThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                if ( mProgressDialog == null ){
                    mProgressDialog = new ProgressDialog(ImagesListActivity.this);
                    mProgressDialog.setCancelable(false);
                }
                mProgressDialog.setMessage(message);
                if ( !mProgressDialog.isShowing() ){
                    mProgressDialog.show();
                }
            }
        });
    }

    public void hideProgressDialog() {
        hideProgressDialog(null);
    }

    public void hideProgressDialog(int stringResourceId) {
        hideProgressDialog(getString(stringResourceId));
    }

    protected void hideProgressDialog(final String message){
        mUIThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mProgressDialog != null){
                    mProgressDialog.dismiss();
                }
                if (message!=null) {
                    Toast.makeText(ImagesListActivity.this,
                            message,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void onMakeCollageButtonClick(View view){
        insImagesViewModel.makeCollage();
    }

    public void startImagePreviewActivity(Uri collageUri) {
        Intent intent = new Intent(this, ImagePreviewActivity.class);
        intent.putExtra(CollageImage.COLLAGE_URI_KEY, collageUri);
        startActivity(intent);
    }

}
