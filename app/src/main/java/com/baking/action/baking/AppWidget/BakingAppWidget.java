package com.baking.action.baking.AppWidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.baking.action.baking.R;
import com.baking.action.baking.activity.RecipeActivity;
import com.baking.action.baking.activity.RecipeDetailActivity;
import com.baking.action.baking.service.BakingService;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, int currentIndex) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        views.setImageViewResource(R.id.appWidgetIv, R.mipmap.logo);

        if (currentIndex != -1) {
            Intent intent = new Intent(context, RecipeDetailActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            views.setOnClickPendingIntent(R.id.appWidgetIv, pendingIntent);
        } else {
            Intent intent = new Intent(context, BakingService.class);
            intent.setAction(BakingService.UPDATA_BAKING);
            PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.appWidgetIv, pendingIntent);
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Intent intent = new Intent(context, BakingService.class);
        intent.setAction(BakingService.UPDATA_BAKING);
        context.startService(intent);
    }

    public static void updateBakId(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, int currentIndex){
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, currentIndex);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

