package com.baking.action.baking.uitls;

import android.app.Activity;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by hanyuezi on 18/1/25.
 */

public class DataUtils {
    public static String getData(Activity activity){
        InputStream is = null;
        try {
            is = activity.getResources().getAssets().open("data.txt");

            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[8192]; // buff用于存放循环读取的临时数据
            int rc;

            while ((rc = is.read(buff, 0, 1024)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            byte[] buffer = swapStream.toByteArray(); // in_b为转换之后的结果? 　
            return new String(buffer);
        } catch (Exception e) {
            return "";
        }
    }
}
