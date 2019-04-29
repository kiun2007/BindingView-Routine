package kiun.com.bindingdemo.fragment;

import kiun.com.bindingdemo.BR;
import kiun.com.bindingdemo.R;
import kiun.com.bindingdemo.databinding.FragmentMainBinding;
import kiun.com.bvroutine.base.BVBaseFragment;

public class DemoFragment extends BVBaseFragment<FragmentMainBinding> {

    @Override
    public int getViewId() {
        return R.layout.fragment_main;
    }

    @Override
    public void initView() {
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
}
