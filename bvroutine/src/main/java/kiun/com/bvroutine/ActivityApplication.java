package kiun.com.bvroutine;

import android.app.Application;
import android.widget.ImageView;

import com.taobao.weex.InitConfig;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.adapter.DefaultWXHttpAdapter;
import com.taobao.weex.adapter.IWXImgLoaderAdapter;
import com.taobao.weex.common.WXImageStrategy;
import com.taobao.weex.dom.WXImageQuality;

public abstract class ActivityApplication extends Application {

    private static ActivityApplication application;

    public static ActivityApplication getApplication(){
        return application;
    }

    public static void registerCallbacks(ActivityLifecycleCallbacks callbacks){
        if (application != null){
            application.registerActivityLifecycleCallbacks(callbacks);
        }
    }

    protected abstract String debugProxy();

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        if (debugProxy() != null){
            WXEnvironment.sRemoteDebugMode = true;
            WXEnvironment.sRemoteDebugProxyUrl = "ws://" + debugProxy() + ":8088/debugProxy/native";
        }

        InitConfig config = new InitConfig.Builder().setImgAdapter(new IWXImgLoaderAdapter() {
            @Override
            public void setImage(String url, ImageView view, WXImageQuality quality, WXImageStrategy strategy) {
            }
        }).setHttpAdapter(new DefaultWXHttpAdapter()).build();
        WXSDKEngine.initialize(this, config);
    }
}
