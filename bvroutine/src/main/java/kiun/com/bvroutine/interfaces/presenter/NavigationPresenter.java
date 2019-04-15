package kiun.com.bvroutine.interfaces.presenter;

import android.app.Fragment;
import android.app.FragmentManager;
import kiun.com.bvroutine.data.BaseBean;
import kiun.com.bvroutine.interfaces.view.NavigationView;

/**
 * 导航事务接口.
 */
public interface NavigationPresenter {

    /**
     * 初始化导航事务.
     * @param view
     * @param fragmentManager
     */
    void initNavigation(NavigationView view, FragmentManager fragmentManager);

    /**
     * 返回上个界面.
     */
    void backNavi();

    /**
     * 返回上个界面.
     */
    void backNavi(Class<Fragment> clz);

    /**
     * 返回第一个界面.
     */
    void gotoRoot();

    /**
     * 跳到指定的界面.
     * @param clz 页面类.
     */
    void gotoNavi(Class<Fragment> clz, BaseBean value);

    /**
     * 跳转到下一个页面.
     * @param value
     */
    void next(BaseBean value);
}
