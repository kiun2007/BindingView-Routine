package kiun.com.bindingdemo.fragment;

import android.os.Handler;
import android.os.Message;

import kiun.com.bindingdemo.BR;
import kiun.com.bindingdemo.R;
import kiun.com.bindingdemo.databinding.FragmentMainBinding;
import kiun.com.bvroutine.base.BVBaseFragment;
import kiun.com.bvroutine.base.NavigationBaseFragment;
import kiun.com.bvroutine.interfaces.PagerHandlerSeter;
import kiun.com.bvroutine.views.listeners.PagerHandler;

public class DemoFragment extends NavigationBaseFragment<FragmentMainBinding> implements PagerHandlerSeter {

    PagerHandler pagerHandler;

    @Override
    public int getViewId() {
        return R.layout.fragment_main;
    }

    @Override
    public void initView() {
        int a = 0;
//        pagerHandler.setTitle("1111", 0);

        getBarItem().setBarNoBack(false);
    }

    @Override
    protected int getNavigationBR() {
        return BR.naviHandler;
    }

    @Override
    protected String title() {
        return "导航测试";
    }

    @Override
    public Class<? extends BVBaseFragment>[] getNextFragments() {
        return new Class[]{FirstFragment.class};
    }

    @Override
    public void setPagerHandler(PagerHandler pagerHandler) {
        this.pagerHandler = pagerHandler;
    }
}
