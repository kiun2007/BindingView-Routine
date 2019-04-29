package kiun.com.bvroutine.base;

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

    public static final String ROOT_TAG = "ROOT_TAG";

    protected T mViewBinding = null;
    NavigatorBar barView;
    ActionBarItem actionBarItem;
    boolean isRoot = false;
    private FragmentNavigationHandler navigationHandler;
    private ActionBarHandler actionBarHandler;

    protected abstract String title();

    public Class<? extends BVBaseFragment>[] getNextFragments(){
        return null;
    }

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        isRoot = getArguments().getBoolean(ROOT_TAG, false);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view;
        if (getActivity() instanceof BVBaseActivity){
            barView = ((BVBaseActivity) getActivity()).getBarView();
        }

        if(isWithActionBar() && barView == null){
            view = inflater.inflate(R.layout.activiy_base, container, false);
            mViewBinding = DataBindingUtil.inflate(inflater, getViewId(), view.findViewById(R.id.view_content), true);
            barView = view.findViewById(R.id.view_actionbar);
        }else{
            mViewBinding = DataBindingUtil.inflate(inflater, getViewId(), container, false);
            view = mViewBinding.getRoot();
        }

        if (barView == null){
            barView = view.findViewWithTag(NavigatorBar.TAG);
        }
        actionBarItem = barView.getBarItem();
        actionBarItem.setTitle(title());
        actionBarItem.setBarNoBack(isRoot);
        setNavigationHandler(navigationHandler);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    public void setBarHandler(ActionBarHandler actionBarHandler) {
        actionBarItem.setHandler(actionBarHandler);
    }

    public void setNavigationHandler(FragmentNavigationHandler navigationHandler){
        if (getNavigationBR() > 0 && mViewBinding != null){
            mViewBinding.setVariable(getNavigationBR(), navigationHandler);
        }
        this.navigationHandler = navigationHandler;
    }

    public ActionBarItem getBarItem() {
        return actionBarItem;
    }
}