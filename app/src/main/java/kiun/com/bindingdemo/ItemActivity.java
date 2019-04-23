package kiun.com.bindingdemo;

import android.content.Context;

import java.util.List;

import kiun.com.bindingdemo.bean.AdXReqBase;
import kiun.com.bindingdemo.bean.QueryListBase;
import kiun.com.bindingdemo.bean.QueryTCListLastBase;
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
        if (extra instanceof RsvrRgstrReqBean || extra instanceof RsvrRgstrBean || extra instanceof QueryTCListLastBase){
            boolean children = false;
            if (extra instanceof RsvrRgstrReqBean){
                if(((RsvrRgstrReqBean) extra).getPType().equals("2")){
                    children = true;
                }
            }
            if (extra instanceof RsvrRgstrBean){
                children = true;
            }
            return new TreeViewNode(R.layout.item_row, children);
        }
        return null;
    }

    @Override
    public List requestPager(RequestBindingPresenter p, TreeNode bean) {
        if (bean == null){ //root.
            List<RsvrRgstrReqBean> planBases = p.callServiceList(OffOnlieServices.class, s -> s.inspPlanList("ee5c759f97a24cedb646a3c9d5e5eca9"), null);
            return planBases;
        }else if (bean.getExtra() instanceof RsvrRgstrReqBean){

            PagerBean pagerBean = bean.getPager();
            List<RsvrRgstrBean> list = p.callServiceList(OffOnlieServices.class, s -> s.rsvrRgstrList(pagerBean), pagerBean);

            return list;
        }else if (bean.getExtra() instanceof RsvrRgstrBean){

            boolean isFirst = !bean.withPager();
            PagerBean pagerBean = bean.getPager();
            List<QueryTCListLastBase> list = p.callServiceList(OffOnlieServices.class, s -> s.querTCList(pagerBean), pagerBean);

            if(isFirst){
                QueryTCListLastBase wsoure = new QueryTCListLastBase("水源地", "WW00001");
                wsoure.setGuid(((RsvrRgstrBean) bean.getExtra()).getEngId());
                wsoure.setStatus(((RsvrRgstrBean) bean.getExtra()).getObjId());
                wsoure.setTownFullName(((RsvrRgstrBean) bean.getExtra()).getCode());

                QueryTCListLastBase wpro = new QueryTCListLastBase("饮水工程", "WW00002");
                wpro.setGuid(((RsvrRgstrBean) bean.getExtra()).getEngId());
                wpro.setStatus(((RsvrRgstrBean) bean.getExtra()).getObjId());
                wpro.setTownFullName(((RsvrRgstrBean) bean.getExtra()).getCode());

                list.add(0, wsoure);
                list.add(0, wpro);
            }
            return list;
        }else if (bean.getExtra() instanceof QueryTCListLastBase){

            if (((QueryTCListLastBase) bean.getExtra()).getTownCode().equals("WW00001")){
                if (!bean.withPager()){
                    AdXReqBase adXReqBase = new AdXReqBase();
                    adXReqBase.setEngId(((QueryTCListLastBase) bean.getExtra()).getGuid());
                    adXReqBase.setObjId(((QueryTCListLastBase) bean.getExtra()).getTownFullName());
                    bean.setPager(new PagerBean<AdXReqBase>(adXReqBase));
                }
                PagerBean pagerBean = bean.getPager();
                List<QueryListBase> list = p.callServiceList(OffOnlieServices.class, s -> s.queryListByObjId(pagerBean), pagerBean);
                return list;
            }
            bean.getPager();
            return ((QueryTCListLastBase) bean.getExtra()).getList();
        }
        return null;
    }
}
