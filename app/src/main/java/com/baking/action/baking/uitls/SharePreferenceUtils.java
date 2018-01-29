package com.baking.action.baking.uitls;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hanyuezi on 17/11/24.
 */

public class SharePreferenceUtils {

    public static void saveIntSharePreference(Context activity, int position){
        SharedPreferences sp = activity.getSharedPreferences("bak", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("bakStar", position);
        editor.commit();
    }

    public static int getIntSharePreference(Context activity){
        SharedPreferences sp = activity.getSharedPreferences("bak", Context.MODE_PRIVATE);
        return sp.getInt("bakStar", 0);
    }

}
