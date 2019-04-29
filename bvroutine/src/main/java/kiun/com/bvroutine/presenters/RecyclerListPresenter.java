package kiun.com.bvroutine.presenters;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;
import kiun.com.bvroutine.base.BaseHandler;
import kiun.com.bvroutine.base.BaseRecyclerAdapter;
import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.data.QueryBean;
import kiun.com.bvroutine.data.viewmodel.TreeNode;
import kiun.com.bvroutine.interfaces.callers.CompareCaller;
import kiun.com.bvroutine.interfaces.presenter.ListViewPresenter;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine.interfaces.view.ListRequestView;
import kiun.com.bvroutine.interfaces.view.LoadAdapter;
import kiun.com.bvroutine.views.adapter.RecyclerSimpleAdapter;

public class RecyclerListPresenter<T,Q extends QueryBean,Req extends ListRequestView,ADA extends LoadAdapter>
        extends RecyclerView.OnScrollListener implements ListViewPresenter<T,Q,Req>, SwipeRefreshLayout.OnRefreshListener {

    protected RecyclerView mRecyclerView;
    protected SwipeRefreshLayout mRefreshLayout;
    protected Req mRequestView;
    protected Q rootRequest;
    protected RequestBindingPresenter presenter;
    protected ADA loadAdapter;

    public RecyclerListPresenter(RecyclerView recyclerView, SwipeRefreshLayout refreshLayout){
        mRecyclerView = recyclerView;
        mRefreshLayout = refreshLayout;
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public Q initRequest(Q request, Req requestView) {
        mRequestView = requestView;
        rootRequest = request;

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requestView.getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        return request;
    }

    protected BaseRecyclerAdapter getRecyclerAdapter(int itemLayout, int dataBr, BaseHandler<T> hanlder){
        return new RecyclerSimpleAdapter(this, itemLayout, dataBr, hanlder);
    }

    @Override
    public void start(BaseHandler<T> hanlder, int itemLayout, int dataBr, RequestBindingPresenter p) {
        presenter = p;
        mRecyclerView.setAdapter((BaseRecyclerAdapter) (loadAdapter = (ADA) getRecyclerAdapter(itemLayout, dataBr, hanlder)));
        reload();
    }

    @Override
    public void loadMore() {
        if(rootRequest instanceof PagerBean){
            if (!((PagerBean) rootRequest).isPageOver()){
                ((PagerBean) rootRequest).addPage();
            }else{
                return;
            }
        }
        presenter.addRequest(()->mRequestView.requestPager(presenter, rootRequest), this::onDataComplete);
    }

    @Override
    public void reload() {
        loadAdapter.clear();
        if(rootRequest instanceof PagerBean) {
            ((PagerBean) rootRequest).setPageNum(1);
        }
        presenter.addRequest(()->mRequestView.requestPager(presenter, rootRequest), this::onDataComplete);
    }

    @Override
    public void notifySet() {
        loadAdapter.notifySet();
    }

    @Override
    public int[] filterCount(CompareCaller<T>... callers) {
        return filterCount(false, callers);
    }

    @Override
    public int[] filterCount(boolean repeat, CompareCaller<T>... callers) {

        List<T> items = loadAdapter.getAll();
        int[] rets = new int[callers.length];
        for (int i = 0; i < rets.length; i++) rets[i] = 0;

        for (T item : items) {
            for (int i = 0; i < callers.length; i++) {
                if (callers[i].call(item)){
                    rets[i] ++;
                    if (!repeat){
                        break;
                    }
                }
            }
        }
        return rets;
    }

    @Override
    public List<T> filter(CompareCaller<T> caller) {
        return filters(caller)[0];
    }

    @Override
    public List<T>[] filters(CompareCaller<T>... callers) {
        return filters(false, callers);
    }

    @Override
    public List<T>[] filters(boolean repeat, CompareCaller<T>... callers) {
        List<T> allItems = loadAdapter.getAll();
        List<T>[] selectedItems = new List[callers.length];
        for (int i = 0; i < selectedItems.length; i++) {
            selectedItems[i] = new LinkedList<>();
        }

        for (T item : allItems) {
            for (int i = 0; i < callers.length; i++) {
                if (callers[i].call(item)){
                    selectedItems[i].add(item);
                    if (!repeat){
                        break;
                    }
                }
            }
        }
        return selectedItems;
    }

    @Override
    public List<T> list() {
        return loadAdapter.getAll();
    }

    protected void onDataComplete(List<T> v){
        loadAdapter.add(v);
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        reload();
    }
}