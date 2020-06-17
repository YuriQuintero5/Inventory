package com.example.inventory;

import android.view.View;

public abstract class ViewSimpleCallback implements SimplaCallback {
    public View view ;
    public  ViewSimpleCallback(View view){
        this.view = view;
    }
}
