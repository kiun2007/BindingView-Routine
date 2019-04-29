package kiun.com.bindingdemo;

import android.content.Context;
import kiun.com.bindingdemo.databinding.ActivityDemoBinding;
import kiun.com.bindingdemo.fragment.DemoFragment;
import kiun.com.bvroutine.base.NavigationBaseActivity;

public class NavigationActivity extends NavigationBaseActivity<ActivityDemoBinding> {

    @Override
    public boolean isWithActionBar() {
        return false;
    }

    @Override
    public int getViewId() {
        return R.layout.activity_demo;
    }

    @Override
    public void initView() {
        configAnimation(R.anim.enter_translate, R.anim.exit_translate, R.anim.pop_enter_translate, R.anim.pop_exit_translate);
        getNavigation().setRoot(DemoFragment.class, null);
    }

    @Override
    public int getContentViewId() {
        return R.id.fragment_content;
    }

    @Override
    public <T> T createService(Class<T> serviceClass) {
        return null;
    }

    @Override
    public Context getContext() {
        return this;
    }
}
