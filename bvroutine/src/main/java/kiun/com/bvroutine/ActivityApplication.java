package kiun.com.bvroutine;

import android.app.Application;

public class ActivityApplication extends Application {

    private static ActivityApplication application;

    public static ActivityApplication getApplication(){
        return application;
    }

    public static void registerCallbacks(ActivityLifecycleCallbacks callbacks){
        if (application != null){
            application.registerActivityLifecycleCallbacks(callbacks);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }
}
