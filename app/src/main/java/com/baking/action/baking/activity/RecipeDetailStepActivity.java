package com.baking.action.baking.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.baking.action.baking.R;
import com.baking.action.baking.model.HttpObject;
import com.baking.action.baking.model.StepModel;
import com.baking.action.baking.uitls.DataUtils;
import com.baking.action.baking.uitls.SharePreferenceUtils;
import com.bluelinelabs.logansquare.LoganSquare;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hanyuezi on 18/1/24.
 */
public class RecipeDetailStepActivity extends BaseActivity {

    @BindView(R.id.recipeVideosLv)
    ListView mVideosLv;

    private List<StepModel> stepModels;

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_detail_step);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Step");
        }

        ButterKnife.bind(this);

        stepModels = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            stepModels = bundle.getParcelableArrayList("videos");
        }

        if (stepModels == null || stepModels.isEmpty()) {
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
                stepModels = httpObject.getList().get(bakId).getSteps();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        mVideosLv.setAdapter(new RecipeDetailStepAdapter());
    }

    private SimpleExoPlayer initExoPlayer(SimpleExoPlayerView view) {
        // 1.创建一个默认TrackSelector
        // step1. 创建一个默认的TrackSelector
        Handler mainHandler = new Handler();
        // 创建带宽
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        // 创建轨道选择工厂
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        // 创建轨道选择器实例
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        //step2. 创建播放器
        SimpleExoPlayer simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(RecipeDetailStepActivity.this, trackSelector);
        view.setPlayer(simpleExoPlayer);
        return simpleExoPlayer;
    }

    private void createPlayer(ExoPlayer player, String videoUrl) {
        // 测量播放带宽，如果不需要可以传null
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

        // 创建加载数据的工厂
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(RecipeDetailStepActivity.this,
                Util.getUserAgent(RecipeDetailStepActivity.this, "yourApplicationName"), bandwidthMeter);

        // 创建解析数据的工厂
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        // 传入Uri、加载数据的工厂、解析数据的工厂，就能创建出MediaSource
        MediaSource videoSource = new ExtractorMediaSource(Uri.parse(videoUrl),
                dataSourceFactory, extractorsFactory, null, null);

        // Prepare
        player.prepare(videoSource);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //步骤
    class RecipeDetailStepAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return stepModels.size();
        }

        @Override
        public Object getItem(int i) {
            return stepModels.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, View view, ViewGroup viewGroup) {
            View contentView = getLayoutInflater().inflate(R.layout.activity_recipe_detail_video_item, null);
            ViewHolder viewHolder = null;
            if (null == viewHolder) {
                viewHolder = new ViewHolder(contentView);
                contentView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) contentView.getTag();
            }

            viewHolder.mShortDescTv.setText(stepModels.get(position).getShortDescription());
            viewHolder.mDescTv.setText(stepModels.get(position).getDescription());

            isVideoShow(viewHolder.mStepRl, stepModels.get(position).getVideoURL());

            final ViewHolder finalViewHolder = viewHolder;
            viewHolder.mStepRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ExoPlayer exoPlayer = initExoPlayer(finalViewHolder.mVideoPlayerView);
                    createPlayer(exoPlayer, stepModels.get(position).getVideoURL());
                    exoPlayer.setPlayWhenReady(true);
                }
            });

            return contentView;
        }

        class ViewHolder {
            @BindView(R.id.recipeDetailStepRl)
            RelativeLayout mStepRl;
            @BindView(R.id.recipeDetailStepShortDescTv)
            TextView mShortDescTv;
            @BindView(R.id.recipeDetailStepDescTv)
            TextView mDescTv;
            @BindView(R.id.recipeDetailVideoIv)
            SimpleExoPlayerView mVideoPlayerView;

            public ViewHolder(View itemView) {
                ButterKnife.bind(this, itemView);
            }
        }

        public void isVideoShow(RelativeLayout mStepRl, String videoUrl){
            if (TextUtils.isEmpty(videoUrl)) {
                mStepRl.setVisibility(View.GONE);
            } else {
                mStepRl.setVisibility(View.VISIBLE);

            }
        }
    }


}
