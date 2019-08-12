package kiun.com.bvroutine.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;

import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.utils.WXJsonUtils;

import java.util.HashMap;
import java.util.Map;
import kiun.com.bvroutine.R;
import kiun.com.bvroutine.databinding.ActivityVueViewBinding;

public class VueViewActivity extends BVBaseActivity<ActivityVueViewBinding> implements IWXRenderListener {

    WXSDKInstance mWXSDKInstance;

    public static class URL implements Parcelable {
        private String url;
        private boolean isCache;

        public URL(String url, boolean isCache) {
            this.url = url;
            this.isCache = isCache;
        }

        protected URL(Parcel in) {
            url = in.readString();
            isCache = in.readByte() != 0;
        }

        public static final Creator<URL> CREATOR = new Creator<URL>() {
            @Override
            public URL createFromParcel(Parcel in) {
                return new URL(in);
            }

            @Override
            public URL[] newArray(int size) {
                return new URL[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(url);
            dest.writeByte((byte) (isCache ? 1 : 0));
        }
    }

    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        mViewBinding.fragmentContent.removeAllViews();
        mViewBinding.fragmentContent.addView(view);
    }

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
    }

    @Override
    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {
    }

    @Override
    public void onException(WXSDKInstance instance, String errCode, String msg) {
        int a = 0;
    }

    public static class Builder{
        Intent intent = new Intent();
        Bundle extra = new Bundle();
        Context context;

        public Builder(Context context){
            this.context = context;
            intent.setClass(context, VueViewActivity.class);
        }

        public Builder url(String url){
            intent.putExtra("PAGE_URL", new URL(url,true));
            return this;
        }

        public Builder extra(String key, double value){
            extra.putDouble(key, value);
            return this;
        }

        public Builder extra(String key, float value){
            extra.putFloat(key, value);
            return this;
        }

        public Builder extra(String key, int value){
            extra.putInt(key, value);
            return this;
        }

        public Builder extra(String key, long value){
            extra.putLong(key, value);
            return this;
        }

        public Builder extra(String key, boolean value){
            extra.putBoolean(key, value);
            return this;
        }

        public Builder extra(String key, String value){
            extra.putString(key, value);
            return this;
        }

        public Builder extra(String key, Parcelable value){
            extra.putParcelable(key, value);
            return this;
        }

        public void start(){
            intent.putExtra("extra", extra);
            context.startActivity(intent);
        }

        public void start(int resultCode){
            intent.putExtra("extra", extra);
            if (context instanceof Activity){
                ((Activity) context).startActivityForResult(intent, resultCode);
            }
        }
    }

    public static VueViewActivity.Builder context(Context context){
        return new VueViewActivity.Builder(context);
    }

    private String getJSONIntent(){

        Map<String, Object> allParams = new HashMap<>();
        Bundle bundle = getIntent().getBundleExtra("extra");
        if(bundle == null){
            return null;
        }

        for (String key : bundle.keySet()){
            Object data = bundle.get(key);
            if (data instanceof Parcelable){
                allParams.put(key, WXJsonUtils.fromObjectToJSONString(data));
            }else{
                allParams.put(key, data);
            }
        }

        String user = "{\"guid\":\"ee5c759f97a24cedb646a3c9d5e5eca9\",\"token\":\"11119143aea1dddd4691bab8a922e9752a79\"}";
//        allParams.put("user", user);
//        allParams.put("baseUrl", "http://sv.goldenwater.com.cn/");
        return WXJsonUtils.fromObjectToJSONString(allParams);
    }

    @Override
    public int getViewId() {
        return R.layout.activity_vue_view;
    }

    @Override
    public boolean isWithActionBar() {
        return false;
    }

    @Override
    public void initView() {

        mWXSDKInstance = new WXSDKInstance(this);
        mWXSDKInstance.registerRenderListener(this);

        String pageName = "VuePage";
        URL bundleUrl = getIntent().getParcelableExtra("PAGE_URL");
        String jsonData = getJSONIntent();

        mWXSDKInstance.renderByUrl(pageName, bundleUrl.url, null, jsonData,WXRenderStrategy.APPEND_ASYNC);
    }
}
