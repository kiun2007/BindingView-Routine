package kiun.com.bvroutine.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import kiun.com.bvroutine.interfaces.presenter.NavigationPresenter;
import kiun.com.bvroutine.interfaces.view.NavigationView;
import kiun.com.bvroutine.presenters.NormalNavigationPresenter;

public abstract class NavigationBaseActivity<T extends ViewDataBinding> extends RequestBVActivity<T> implements NavigationView {

    protected NavigationPresenter navigationPresenter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getNavigation().initNavigation(this, getFragmentManager());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        getNavigation().initNavigation(this, getFragmentManager());
    }

    protected NavigationPresenter getNavigation(){
        if (navigationPresenter == null){
            navigationPresenter = new NormalNavigationPresenter();
        }
        return navigationPresenter;
    }
}