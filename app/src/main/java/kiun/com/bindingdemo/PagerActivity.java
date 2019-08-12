package kiun.com.bindingdemo;

import android.content.Context;
import kiun.com.bindingdemo.databinding.ActivityFragmentPagerBinding;
import kiun.com.bindingdemo.fragment.DemoFragment;
import kiun.com.bindingdemo.fragment.FirstFragment;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.views.adapter.PagerFragmentAdapter;
import kiun.com.bvroutine.views.listeners.PagerHandler;

public class PagerActivity extends RequestBVActivity<ActivityFragmentPagerBinding> {

    PagerHandler pagerHandler;
    PagerFragmentAdapter adapter;

    @Override
    public <T>T createService(Class<T> serviceClass) {
        return null;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public int getViewId() {
        return R.layout.activity_fragment_pager;
    }

    @Override
    public void onCreateBefore() {
        adapter = new PagerFragmentAdapter(getSupportFragmentManager(), DemoFragment.class, FirstFragment.class,FirstFragment.class);
        adapter.addCreateListener(DemoFragment.class, this::onDemoCreate);
    }

    @Override
    public void initView() {
        mViewBinding.setAdapter(adapter);
        mViewBinding.setHandler(pagerHandler = new PagerHandler(null, "未督查","督查中"));
        mViewBinding.pagerView.setPagerCaller(this::onPagerSelected);
    }

    private void onDemoCreate(DemoFragment fragment){
        int a = 0;
    }

    private void onPagerSelected(int index){

        if (index == 0){
            DemoFragment demoFragment = adapter.getFragmentItem(index);
            int a = 0;
        }

    }
}
