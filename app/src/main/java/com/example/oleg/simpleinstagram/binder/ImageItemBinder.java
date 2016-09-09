package com.example.oleg.simpleinstagram.binder;

import com.example.oleg.simpleinstagram.viewmodel.InsImageViewModel;

import net.droidlabs.mvvm.recyclerview.adapter.binder.ConditionalDataBinder;

/**
 * Created by oleg on 22.05.16.
 */
public class ImageItemBinder extends ConditionalDataBinder<InsImageViewModel> {

    public ImageItemBinder(int bindingVariable, int layoutId) {
        super(bindingVariable, layoutId);
    }

    @Override
    public boolean canHandle(InsImageViewModel model) {
        return true;
    }

}
