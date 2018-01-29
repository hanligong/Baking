package com.baking.action.baking.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.baking.action.baking.R;
import com.baking.action.baking.activity.RecipeDetailActivity;
import com.baking.action.baking.uitls.SharePreferenceUtils;

import java.util.ArrayList;

public class RemoveViewService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteviewFactoryImp(this, intent);
    }

    private static ArrayList<String> data;


    class RemoteviewFactoryImp implements RemoteViewsFactory {

        private Intent requestIntent;
        private Context requestContext;


        public RemoteviewFactoryImp(Context context, Intent intent) {
            requestContext = context;
            requestIntent = intent;
        }

        @Override
        public void onCreate() {
            data = new ArrayList<>();
            data.add("Nutella Pie");
            data.add("Brownies");
            data.add("Yellow Cake");
            data.add("Cheesecake");
        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews remoteViews = new RemoteViews(requestContext.getPackageName(), R.layout.service_removeview_item);
            remoteViews.setTextViewText(R.id.removeViewItemTv, data.get(position));

            //listview的点击事件
            Intent intent = new Intent();
            intent.putExtra("position", position);
            remoteViews.setOnClickFillInIntent(R.id.removeViewItemTv, intent);

            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
