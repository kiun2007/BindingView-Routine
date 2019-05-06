package kiun.com.bvroutine.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import kiun.com.bvroutine.R;
import kiun.com.bvroutine.handlers.ActionBarHandler;
import kiun.com.bvroutine.interfaces.handler.FragmentNavigationHandler;
import kiun.com.bvroutine.interfaces.view.BindingView;
import kiun.com.bvroutine.views.NavigatorBar;
import kiun.com.bvroutine.views.viewmodel.ActionBarItem;

public abstract class BVBaseFragment<T extends ViewDataBinding> extends Fragment implements BindingView {

    protected T mViewBinding = null;

    /**
     * 导航条功能模型BR.
     * @return 重写此方法实现导航条模块使用.
     */
    protected int getNavigationBR(){
        return -1;
    }

    public boolean isWithActionBar(){
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mViewBinding = DataBindingUtil.inflate(inflater, getViewId(), container, false);
        return mViewBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }
}