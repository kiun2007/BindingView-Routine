package kiun.com.bindingdemo;

import android.view.View;

import java.util.LinkedList;
import java.util.List;

import kiun.com.bindingdemo.bean.PblmBean;
import kiun.com.bindingdemo.bean.RsvrRgstrBean;
import kiun.com.bindingdemo.bean.RsvrRgstrReqBean;
import kiun.com.bindingdemo.bean.request.PblmListReqBean;
import kiun.com.bindingdemo.databinding.ActivityMainBinding;
import kiun.com.bindingdemo.services.OffOnlieServices;
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
    RsvrRgstrReqBean reqBean = new RsvrRgstrReqBean();

    @Override
    public int getViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        getBarItem().setTitle("测试标题");
        getBarItem().setBarNoBack(true);
//        listViewPresenter = new RecyclerListPresenter(mViewBinding.mainRecyclerView, mViewBinding.mainRefresh);
//
//        reqBean.setPlnaId("001041001001");
//        reqBean.setPrsnType("rsvr");
//        listViewPresenter.initRequest(new PagerBean(reqBean), this);
//        ListHandler listHandler = new ListHandler(BR.handler, R.layout.item_listview_error, null,"没数据","加载中");
//        listViewPresenter.start(listHandler, R.layout.item_pblm, BR.item, getRequestPresenter());
//        mViewBinding.setListViewPresenter(listViewPresenter);
//        mViewBinding.setReqBean(pblmListReqBean);

//        mViewBinding.firstSpinner.setDropDownVerticalOffset();
        spinnerPresenter = new SpinnerPresenter(R.layout.item_spinselect_blue_mid, this, mViewBinding.firstSpinner,mViewBinding.secondSpinner);
        spinnerPresenter.setGetter(0, this::firstSpinner).setGetter(1, this::secondSpinner).start();

    }

    public void onClick(View v){
//        mViewBinding.animBing.setEndPoint(100, 100);
//        mViewBinding.animBing.start();
//        ItemActivityHandler.openItem(this,"1231");
    }

    @Override
    public <T> T createService(Class<T> serviceClass) {
        return ServiceGenerator.createService(serviceClass);
    }

    @Override
    public List requestPager(RequestBindingPresenter p, PagerBean bean) throws Exception {

        List<RsvrRgstrBean> list = p.callServiceList(OffOnlieServices.class, s -> s.rsvrRgstrList(bean), bean);
//        return p.callServiceList(SupervisionListServices.class, s->s.pblmPageList(pblmListReqBean), bean);
        return list;
    }

    public SpinnerValue<PblmBean> firstSpinner(Object item) throws Exception{
        List<PblmBean> list = new LinkedList<>();
        PblmBean pblmBean = new PblmBean();
        pblmBean.setCheckPoint("check point");
        list.add(pblmBean);
        return new SpinnerValue<PblmBean>(0,list,PblmBean::getCheckPoint);
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