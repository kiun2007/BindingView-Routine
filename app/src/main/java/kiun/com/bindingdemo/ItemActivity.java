package kiun.com.bindingdemo;

import android.content.Context;

import java.util.List;

import kiun.com.bindingdemo.bean.RsvrRgstrBean;
import kiun.com.bindingdemo.bean.RsvrRgstrReqBean;
import kiun.com.bindingdemo.databinding.ActivityItemBinding;
import kiun.com.bindingdemo.services.OffOnlieServices;
import kiun.com.bindingdemo.warp.ServiceGenerator;
import kiun.com.bvroutine.base.BaseHandler;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.data.viewmodel.TreeNode;
import kiun.com.bvroutine.data.viewmodel.TreeViewNode;
import kiun.com.bvroutine.interfaces.presenter.ListViewPresenter;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine.interfaces.view.TreeStepView;
import kiun.com.bvroutine.presenters.StepTreePresenter;

public class ItemActivity extends RequestBVActivity<ActivityItemBinding> implements TreeStepView {

    ListViewPresenter listViewPresenter = null;
    @Override
    public int getViewId() {
        return R.layout.activity_item;
    }

    @Override
    public void initView() {

        listViewPresenter = new StepTreePresenter(mViewBinding.mainRecyclerView, mViewBinding.mainRefresh, BR.treeHandler);
        listViewPresenter.initRequest(null, this);
        listViewPresenter.start(new BaseHandler(BR.handler), R.layout.item_plan, BR.item, getRequestPresenter());
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
    public TreeViewNode children(Object extra) {
        if (extra instanceof RsvrRgstrReqBean){
            return new TreeViewNode(R.layout.item_row, true);
        }
        return null;
    }

    @Override
    public List requestPager(RequestBindingPresenter p, TreeNode bean) {
        if (bean == null){ //root.
            List<RsvrRgstrReqBean> planBases = p.callServiceList(OffOnlieServices.class, s -> s.inspPlanList("82C66BD251437F29E0530DC3010ACF31"), null);
            return planBases;
        }else if (bean.getExtra() instanceof RsvrRgstrReqBean){
            PagerBean pagerBean = bean.getPager();
            List<RsvrRgstrBean> list = p.callServiceList(OffOnlieServices.class, s -> s.rsvrRgstrList(pagerBean), pagerBean);
            return list;
        }
        return null;
    }
}
