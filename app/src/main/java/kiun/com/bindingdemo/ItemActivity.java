package kiun.com.bindingdemo;

import android.content.Context;

import kiun.com.bindingdemo.databinding.ActivityItemBinding;
import kiun.com.bindingdemo.warp.ServiceGenerator;
import kiun.com.bvroutine.base.RequestBVActivity;

public class ItemActivity extends RequestBVActivity<ActivityItemBinding> {

    @Override
    public int getViewId() {
        return R.layout.activity_item;
    }

    @Override
    public void initView() {
        mViewBinding.setItem(getExtra());
    }

    @Override
    public <T> T createService(Class<T> serviceClass) {
        return ServiceGenerator.createService(serviceClass);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
