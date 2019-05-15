package kiun.com.bvroutine;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

import kiun.com.bvroutine.interfaces.callers.SetCaller;
import kiun.com.bvroutine.utils.MCString;

public class ActivityCallback {

    public static final String ACTIVITY_GUID = "ACTIVITY_GUID";

    static Callback callback;
    static Map<String,SetCaller<Activity>> activityCallerMap = new HashMap<>();
    static {
        if (callback == null){
            ActivityApplication.registerCallbacks(callback = new Callback());
        }
    }

    public static void addCallBack(String uuid, SetCaller<Activity> caller){
        activityCallerMap.put(uuid, caller);
    }

    public static String getGuid(){
        return MCString.randUUID();
    }

    public static class Callback implements Application.ActivityLifecycleCallbacks{

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            String guid = activity.getIntent().getStringExtra(ACTIVITY_GUID);

            if (guid != null){
                SetCaller<Activity> caller = activityCallerMap.get(guid);
                if (caller != null){
                    caller.call(activity);
                }
            }
        }

        @Override
        public void onActivityStarted(Activity activity) {}

        @Override
        public void onActivityResumed(Activity activity) {}

        @Override
        public void onActivityPaused(Activity activity) {}

        @Override
        public void onActivityStopped(Activity activity) {}

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}

        @Override
        public void onActivityDestroyed(Activity activity) {
            String guid = activity.getIntent().getStringExtra(ACTIVITY_GUID);
            activityCallerMap.remove(guid);
        }
    }
}
