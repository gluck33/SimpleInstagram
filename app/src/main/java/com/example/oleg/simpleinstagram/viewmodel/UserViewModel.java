package com.example.oleg.simpleinstagram.viewmodel;

import android.databinding.BaseObservable;

import com.example.oleg.simpleinstagram.model.User;


/**
 * Created by oleg on 20.05.16.
 */
public class UserViewModel extends BaseObservable {
    public User user;
    public UserViewModel(User user) {
        this.user = user;
    }

    public UserViewModel(){
        this.user = new User("","");
    }

    public UserViewModel(String name, String id){
        if (this.user == null){
            this.user = new User("","");
        }
        this.user.setUsername(name);
        this.user.setUserId(id);
    }



    public String getUserName(){
        return user.getUsername();
    }

    public String getUserId(){
        return user.getUserId();
    }

    public void setUserId(String id){
        user.setUserId(id);
        this.notifyChange();
    }
    private void showUserImages(){
/*
*/
    }


}
