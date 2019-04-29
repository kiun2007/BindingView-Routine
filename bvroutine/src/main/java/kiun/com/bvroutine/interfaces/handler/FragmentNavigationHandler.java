package kiun.com.bvroutine.interfaces.handler;

import kiun.com.bvroutine.base.BVBaseFragment;
import kiun.com.bvroutine.base.BaseHandler;
import kiun.com.bvroutine.interfaces.presenter.NavigationPresenter;

public class FragmentNavigationHandler extends BaseHandler {

    private NavigationPresenter naviPresenter;
    BVBaseFragment current = null;

    public FragmentNavigationHandler(NavigationPresenter presenter){
        naviPresenter = presenter;
    }

    public void setCurrent(BVBaseFragment current) {
        this.current = current;
    }

    public void navigationTo(int toIndex){

        Class<? extends BVBaseFragment>[] fragmentClzs;
        if (current != null && (fragmentClzs = current.getNextFragments()) != null){
            if (toIndex < 0 || toIndex >= fragmentClzs.length){
                return;
            }
            naviPresenter.gotoNavi(fragmentClzs[toIndex], null);
        }
    }
}
