package com.baking.action.baking.service;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.baking.action.baking.AppWidget.BakingAppWidget;
import com.baking.action.baking.uitls.SharePreferenceUtils;

/**
 * Created by hanyuezi on 18/1/25.
 */

public class BakingService extends IntentService{

    public static final String UPDATA_BAKING = "com.baking.action.baking.update";

    public BakingService() {
        super("BakingService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (null == intent) {
            return;
        }
        if (intent.getAction().equals(UPDATA_BAKING)) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingAppWidget.class));
            BakingAppWidget.updateBakId(this, appWidgetManager, appWidgetIds, SharePreferenceUtils.getIntSharePreference(this));
        }
    }
}
