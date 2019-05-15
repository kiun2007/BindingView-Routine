package kiun.com.bindingdemo;

import android.view.View;

import java.util.LinkedList;
import java.util.List;
import kiun.com.bindingdemo.bean.request.PblmListReqBean;
import kiun.com.bindingdemo.databinding.ActivityMainBinding;
import kiun.com.bindingdemo.services.SupervisionListServices;
import kiun.com.bindingdemo.warp.ServiceGenerator;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.data.SpinnerValue;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.interfaces.presenter.ListViewPresenter;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine.interfaces.view.ListRequestView;
import kiun.com.bvroutine.interfaces.view.SpinnerView;
import kiun.com.bvroutine.presenters.RecyclerListPresenter;
import kiun.com.bvroutine.presenters.SpinnerPresenter;
import kiun.com.bvroutine_apt.ActivityOpen;

public class MainActivity extends RequestBVActivity<ActivityMainBinding> implements ListRequestView<PagerBean>, SpinnerView {

    ListViewPresenter listViewPresenter = null;
    PblmListReqBean pblmListReqBean = new PblmListReqBean();
    SpinnerPresenter spinnerPresenter = null;

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
        ListHandler listHandler = new ListHandler(BR.handler, R.layout.item_listview_error, null,"没数据","加载中");
        listViewPresenter.start(listHandler, R.layout.item_pblm, BR.item, getRequestPresenter());
        mViewBinding.setListViewPresenter(listViewPresenter);
        mViewBinding.setReqBean(pblmListReqBean);
        spinnerPresenter = new SpinnerPresenter(R.layout.item_spinselect_blue_mid, this, mViewBinding.firstSpinner,mViewBinding.secondSpinner);
        spinnerPresenter.setGetter(0, this::firstSpinner).setGetter(1, this::secondSpinner).start();
    }

    public void onClick(View v){
        ItemActivityHandler.openItem(this,"1231");
    }

    @Override
    public <T> T createService(Class<T> serviceClass) {

        return ServiceGenerator.createService(serviceClass);
    }

    @Override
    public List requestPager(RequestBindingPresenter p, PagerBean bean) throws Exception {
        Thread.sleep(5000);
        return p.callServiceList(SupervisionListServices.class, s->s.pblmPageList(pblmListReqBean), bean);
    }

    public SpinnerValue<String> firstSpinner(Object item) throws Exception{
        List<String> list = new LinkedList<>();
        list.add("1-1");
        list.add("1-2");
        return new SpinnerValue<String>(0,list);
    }

    public SpinnerValue<String> secondSpinner(String item){
        List<String> list = new LinkedList<>();
        list.add("2-1");
        list.add("2-2");
        list.add("2-3");
        list.add("2-4");
        return new SpinnerValue<String>(0,list);
    }

    @ActivityOpen
    public void openVoid(){

    }

    @Override
    public void loadComplete(ListViewPresenter<PagerBean, ?, ?> p) {}
}