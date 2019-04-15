package kiun.com.bindingdemo;

import android.content.Context;
import java.util.List;
import kiun.com.bindingdemo.bean.PblmBean;
import kiun.com.bindingdemo.bean.PblmListReqBean;
import kiun.com.bindingdemo.databinding.ActivityMainBinding;
import kiun.com.bindingdemo.services.SupervisionListServices;
import kiun.com.bindingdemo.warp.ServiceGenerator;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.handlers.ActivityHandler;
import kiun.com.bvroutine.interfaces.presenter.ListViewPresenter;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine.interfaces.view.ListRequestView;
import kiun.com.bvroutine.presenters.RecyclerListPresenter;

public class MainActivity extends RequestBVActivity<ActivityMainBinding> implements ListRequestView<PblmBean> {

    ListViewPresenter listViewPresenter = null;
    PblmListReqBean pblmListReqBean = new PblmListReqBean();

    @Override
    public int getViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        getBarItem().setTitle("测试标题1");
        getBarItem().setBarNoBack(true);
        RequestBindingPresenter p = getRequestPresenter();
        listViewPresenter = new RecyclerListPresenter(mViewBinding.mainRecyclerView, mViewBinding.mainRefresh);
        listViewPresenter.initRequest(pblmListReqBean, this);
        listViewPresenter.start(new ActivityHandler(BR.handler, ItemActivity.class), R.layout.item_pblm, BR.item, p);
    }

    @Override
    public <T> T createService(Class<T> serviceClass) {
        return ServiceGenerator.createService(serviceClass);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public List<PblmBean> requestPager(PagerBean bean) {
        return getRequestPresenter().callServiceList(SupervisionListServices.class, s->s.pblmPageList(new PblmListReqBean()), bean);
    }
}