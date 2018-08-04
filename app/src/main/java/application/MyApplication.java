package application;

import android.app.Application;

import configs.Config;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Config.init(getApplicationContext());
    }
}
