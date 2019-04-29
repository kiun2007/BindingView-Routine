package kiun.com.bvroutine.presenters;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import kiun.com.bvroutine.base.BVBaseFragment;
import kiun.com.bvroutine.data.BaseBean;
import kiun.com.bvroutine.handlers.ActionBarHandler;
import kiun.com.bvroutine.interfaces.handler.FragmentNavigationHandler;
import kiun.com.bvroutine.interfaces.presenter.NavigationPresenter;
import kiun.com.bvroutine.interfaces.view.NavigationView;

public class NormalNavigationPresenter implements NavigationPresenter {

    public static final String ARGS_VALUE = "VALUE";

    int enterAnimation = -1, exitAnimation = -1, popEnterAnimation = -1, popExitAnimation = -1;
    int contentViewId;
    FragmentManager mFragmentManager;
    Class<? extends BVBaseFragment> rootFragment = null;
    FragmentNavigationHandler handler = new FragmentNavigationHandler(this);
    NavigationActivityHandler barHandler = new NavigationActivityHandler();

    FragmentManager.FragmentLifecycleCallbacks lifecycleCallbacks = new FragmentManager.FragmentLifecycleCallbacks() {
        @Override
        public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
            if (f instanceof BVBaseFragment){
                BVBaseFragment fragment = (BVBaseFragment) f;
                fragment.setNavigationHandler(handler);
                fragment.setBarHandler(barHandler);
                handler.setCurrent(fragment);
            }

            System.out.println(f.toString());
        }
    };

    public class NavigationActivityHandler extends ActionBarHandler {

        @Override
        public void onClick(Context context, int tag, Object data) {
            if (tag == ActionBarHandler.TAG_BACK_BUTTON_ITEM){
                backNavi();
            }
        }
    }

    @Override
    public void initNavigation(@NonNull NavigationView view, FragmentManager fragmentManager) {
        contentViewId = view.getContentViewId();
        mFragmentManager = fragmentManager;
        mFragmentManager.registerFragmentLifecycleCallbacks(lifecycleCallbacks, false);
    }

    @Override
    public void setRoot(Class<? extends BVBaseFragment> clz, BaseBean value) {
        commitFragment(clz, value, true);
    }

    @Override
    public void backNavi() {
        mFragmentManager.popBackStack();
    }

    @Override
    public void backNavi(Class<? extends BVBaseFragment> clz) {
        mFragmentManager.popBackStack(clz.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void gotoRoot() {
        if (rootFragment != null){
            mFragmentManager.popBackStack(rootFragment.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    private void commitFragment(Class<? extends BVBaseFragment> clz, BaseBean value, boolean isRoot){

        if (isRoot && mFragmentManager.getFragments().size() > 0) return;

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        try {
            BVBaseFragment fragment = clz.newInstance();

            Bundle bundle = new Bundle();
            if(value != null){
                bundle.putParcelable(ARGS_VALUE, value);
            }

            if (isRoot && rootFragment == null){
                rootFragment = clz;
                bundle.putBoolean(BVBaseFragment.ROOT_TAG, true);
                transaction.add(contentViewId, fragment);
            }else{
                if (exitAnimation > 0 && enterAnimation > 0){
                    transaction.setCustomAnimations(enterAnimation, exitAnimation, popEnterAnimation, popExitAnimation);
                }else{
                    transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                            android.R.anim.slide_out_right, android.R.anim.slide_in_left);
                }
                transaction.replace(contentViewId, fragment);
            }
            fragment.setArguments(bundle);

            transaction.addToBackStack(clz.getName()).commit();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("ResourceType")
    @Override
    public void gotoNavi(Class<? extends BVBaseFragment> clz, BaseBean value) {
        commitFragment(clz, value, false);
    }

    @Override
    public void next(BaseBean value) {

    }

    @Override
    public void configAnimation(int enter, int exit, int popEnter, int popExit) {
        enterAnimation = enter; exitAnimation = exit; popEnterAnimation = popEnter; popExitAnimation = popExit;
    }
}
