package utils;

import android.util.Log;

import configs.Config;

public class LogUtil {

    private static String TAG = "LogUtil";

    public static void d(Object msg) {
        if (Config.isDebugable)
            Log.d(TAG, msg + "");
    }

    public static void w(Object msg) {
        if (Config.isDebugable)
            Log.w(TAG, msg + "");
    }

    public static void e(Object msg) {
        if (Config.isDebugable)
            Log.e(TAG, msg + "");
    }


}
