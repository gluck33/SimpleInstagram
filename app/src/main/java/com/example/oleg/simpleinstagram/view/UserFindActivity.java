package com.example.oleg.simpleinstagram.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.example.oleg.simpleinstagram.InstagramClient;
import com.example.oleg.simpleinstagram.R;
import com.example.oleg.simpleinstagram.databinding.ActivityUserFindBinding;
import com.example.oleg.simpleinstagram.model.User;
import com.example.oleg.simpleinstagram.viewmodel.UserViewModel;

public class UserFindActivity extends AppCompatActivity {
    public static final String USER_ID_EXTRA_PARAM = "com.example.oleg.appkodetest.USER_ID";
    public static final String USER_NAME_EXTRA_PARAM = "com.example.oleg.appkodetest.USER_NAME";
    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewModel = new UserViewModel();
        ActivityUserFindBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_user_find);
        binding.setUserViewModel(userViewModel);
        setSupportActionBar(binding.toolbar);
//        binding.toolbar.setTitleTextColor(getResources().getColor(R.color.colorTollbarText));
        getUser("any user");
    }


    public void onFindButtonClick(View view){
        Intent intent = new Intent(this, ImagesListActivity.class);
        intent.putExtra(USER_ID_EXTRA_PARAM, userViewModel.getUserId());
        intent.putExtra(USER_NAME_EXTRA_PARAM, userViewModel.getUserName());
        startActivity(intent);
    }

    private void getUser(String username){
        User.searchUser(username, new InstagramClient.InstagramResponseHandler() {
            @Override
            public void onSuccess(Object model) {
                User user = (User) model;
                userViewModel.user = user;
                userViewModel.notifyChange();
            }

            @Override
            public void onFailure() {
                userViewModel.user = new User("Failed name","");
                userViewModel.notifyChange();
            }
        });
    }

}
