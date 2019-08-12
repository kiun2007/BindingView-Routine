package kiun.com.bindingdemo;

import kiun.com.bvroutine.ActivityApplication;
import kiun.com.bvroutine.data.QueryBean;

public class MainApplication extends ActivityApplication {

    public static MainApplication app;

    @Override
    protected String debugProxy() {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        QueryBean.setBaseGuid("82C66BD251437F29E0530DC3010ACF31");
    }
}
