package com.baking.action.baking.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.baking.action.baking.R;
import com.baking.action.baking.model.HttpObject;
import com.baking.action.baking.model.IngredientModel;
import com.baking.action.baking.model.StepModel;
import com.baking.action.baking.uitls.DataUtils;
import com.baking.action.baking.uitls.SharePreferenceUtils;
import com.bluelinelabs.logansquare.LoganSquare;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hanyuezi on 18/1/24.
 */
public class RecipeDetailActivity extends BaseActivity {

    @BindView(R.id.recipeIngredientsLv)
    ListView mIngredientsLv;

    private List<IngredientModel> ingredientLists;
    private List<StepModel> stepModels;

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_detail);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Ingredients");
        }

        ButterKnife.bind(this);

        ingredientLists = new ArrayList<>();
        stepModels = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            ingredientLists = bundle.getParcelableArrayList("ingredients");
            stepModels = bundle.getParcelableArrayList("videos");
        }

        if (null == ingredientLists || ingredientLists.isEmpty()) {
            int bakId = SharePreferenceUtils.getIntSharePreference(this);

            String data = DataUtils.getData(this);
            if (TextUtils.isEmpty(data)) {
                return;
            }
            HttpObject httpObject = null;
            try {
                httpObject = LoganSquare.parse(DataUtils.getData(this), HttpObject.class);
                if (bakId == -1) {
                    return;
                }
                ingredientLists = httpObject.getList().get(bakId).getIngredients();
                stepModels = httpObject.getList().get(bakId).getSteps();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        mIngredientsLv.setAdapter(new RecipeDetailAdapter());

        findViewById(R.id.recipeDetailStepBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipeDetailActivity.this, RecipeDetailStepActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("videos", (ArrayList<? extends Parcelable>) stepModels);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_star, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
            case R.id.menu_star:
                SharePreferenceUtils.saveIntSharePreference(this, (Integer) getIntent().getExtras().get("position"));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // 原料
    class RecipeDetailAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return ingredientLists.size();
        }

        @Override
        public Object getItem(int i) {
            return ingredientLists.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            View contentView = getLayoutInflater().inflate(R.layout.activity_recipe_detail_item, null);
            ViewHolder viewHolder = null;
            if (null == viewHolder) {
                viewHolder = new ViewHolder(contentView);
                contentView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) contentView.getTag();
            }

            viewHolder.mIngredient1Tv.setText(ingredientLists.get(position).getIngredient());
            viewHolder.mIngredient2Tv.setText(ingredientLists.get(position).getQuantity() + " " + ingredientLists.get(position).getMeasure());

            return contentView;
        }

        class ViewHolder {
            @BindView(R.id.recipeDetailItem1Tv)
            TextView mIngredient1Tv;
            @BindView(R.id.recipeDetailItem2Tv)
            TextView mIngredient2Tv;

            public ViewHolder(View itemView) {
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
