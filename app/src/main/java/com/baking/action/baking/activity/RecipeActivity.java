package com.baking.action.baking.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.baking.action.baking.R;

/**
 * Created by hanyuezi on 17/12/7.
 */

public class RecipeActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle("Baking");
        }

    }
}
