package kiun.com.bvroutine.presenters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import kiun.com.bvroutine.data.BaseBean;
import kiun.com.bvroutine.interfaces.presenter.NavigationPresenter;
import kiun.com.bvroutine.interfaces.view.NavigationView;

public class NormalNavigationPresenter implements NavigationPresenter {

    public static final String ARGS_VALUE = "VALUE";

    int contentViewId;
    FragmentManager mFragmentManager;
    Class<Fragment> rootFragment = null;

    @Override
    public void initNavigation(@NonNull NavigationView view, FragmentManager fragmentManager) {
        contentViewId = view.getContentViewId();
        mFragmentManager = fragmentManager;
    }

    @Override
    public void backNavi() {
        mFragmentManager.popBackStack();
    }

    @Override
    public void backNavi(Class<Fragment> clz) {
        mFragmentManager.popBackStack(clz.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void gotoRoot() {
        if (rootFragment != null){
            mFragmentManager.popBackStack(rootFragment.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    @Override
    public void gotoNavi(Class<Fragment> clz, BaseBean value) {

        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        try {
            Fragment fragment = clz.newInstance();

            if(value != null){
                Bundle bundle = new Bundle();
                bundle.putParcelable(ARGS_VALUE, value);
                fragment.setArguments(bundle);
            }
            transaction.add(contentViewId, fragment);
            transaction.addToBackStack(clz.getName());
            transaction.commit();
            if (rootFragment != null){
                rootFragment = clz;
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void next(BaseBean value) {

    }
}
