package kiun.com.bindingdemo;

import android.app.Application;

public class MainApplication extends Application {

    public static MainApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
}
