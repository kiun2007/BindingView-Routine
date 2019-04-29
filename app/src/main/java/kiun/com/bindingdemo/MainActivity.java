package kiun.com.bindingdemo;

import android.content.Context;
import android.content.Intent;

import java.util.List;
import kiun.com.bindingdemo.bean.PblmBean;
import kiun.com.bindingdemo.bean.request.PblmListReqBean;
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

public class MainActivity extends RequestBVActivity<ActivityMainBinding> implements ListRequestView<PagerBean> {

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
        listViewPresenter = new RecyclerListPresenter(mViewBinding.mainRecyclerView, mViewBinding.mainRefresh);

        listViewPresenter.initRequest(pblmListReqBean, this);
        listViewPresenter.start(new ActivityHandler(BR.handler, ItemActivity.class), R.layout.item_pblm, BR.item, getRequestPresenter());
        mViewBinding.setListViewPresenter(listViewPresenter);
        mViewBinding.setReqBean(pblmListReqBean);

        Intent intent = new Intent(this,ItemActivity.class);
        startActivity(intent);
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
    public List requestPager(RequestBindingPresenter p, PagerBean bean) {
        return p.callServiceList(SupervisionListServices.class, s->s.pblmPageList(pblmListReqBean), bean);
    }

    @Override
    public void loadComplete(ListViewPresenter<PagerBean, ?, ?> p) {

    }
}