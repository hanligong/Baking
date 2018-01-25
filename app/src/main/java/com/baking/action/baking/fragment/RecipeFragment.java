package com.baking.action.baking.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baking.action.baking.activity.BaseActivity;
import com.baking.action.baking.R;
import com.baking.action.baking.activity.RecipeDetailActivity;
import com.baking.action.baking.model.HttpObject;
import com.baking.action.baking.model.RecipeModel;
import com.baking.action.baking.uitls.DataUtils;
import com.bluelinelabs.logansquare.LoganSquare;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hanyuezi on 17/12/7.
 */
public class RecipeFragment extends Fragment{

    private BaseActivity activity;

    @BindView(R.id.recipeRv)
    RecyclerView recyclerView;
    @BindView(R.id.recipeTv)
    TextView mNoContentTv;
    private RecipeAdapter adapter;
    private List<RecipeModel> list;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (BaseActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe, null);
        ButterKnife.bind(this, view);

        list = new ArrayList<>();
        adapter = new RecipeAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        initData();
        return view;
    }

    private void initData() {
        String data = DataUtils.getData(activity);
        if (TextUtils.isEmpty(data)) {
            return;
        }
        HttpObject httpObject = null;
        try {
            httpObject = LoganSquare.parse(DataUtils.getData(activity), HttpObject.class);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        list = httpObject.getList();
        adapter.notifyDataSetChanged();
    }

    class RecipeAdapter extends RecyclerView.Adapter<ViewHolder>{

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = activity.getLayoutInflater().inflate(R.layout.fragment_recipe_item, null);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.mNameTv.setText(list.get(position).getName());
            holder.mServingTv.setText("Serving " + list.get(position).getServings());
            if (position == 0) {
                holder.mIconIv.setImageResource(R.mipmap.nutella_pie);
            } else if (position == 1) {
                holder.mIconIv.setImageResource(R.mipmap.timg);
            } else if (position == 2) {
                holder.mIconIv.setImageResource(R.mipmap.yellow_cake);
            }else {
                holder.mIconIv.setImageResource(R.mipmap.cheese_cake);
            }

            holder.mItemLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, RecipeDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("position", position);
                    bundle.putParcelableArrayList("ingredients", (ArrayList<? extends Parcelable>) list.get(position).getIngredients());
                    bundle.putParcelableArrayList("videos", (ArrayList<? extends Parcelable>) list.get(position).getSteps());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.recipeItemLL)
        LinearLayout mItemLl;
        @BindView(R.id.recipeItemIv)
        ImageView mIconIv;
        @BindView(R.id.recipeItemNameTv)
        TextView mNameTv;
        @BindView(R.id.recipeItemServingTv)
        TextView mServingTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface RecipeResponseListener{
        void showData(String response);
    }
}
