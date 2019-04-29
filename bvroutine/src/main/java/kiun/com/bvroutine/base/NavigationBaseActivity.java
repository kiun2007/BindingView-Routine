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
        getNavigation();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        getNavigation();
        super.onCreate(savedInstanceState, persistentState);
    }

    protected NavigationPresenter getNavigation(){
        if (navigationPresenter == null){
            navigationPresenter = new NormalNavigationPresenter();
            navigationPresenter.initNavigation(this, getSupportFragmentManager());
        }
        return navigationPresenter;
    }

    public void configAnimation(int enter, int exit, int popEnter, int popExit){
        getNavigation().configAnimation(enter, exit, popEnter, popExit);
    }
}