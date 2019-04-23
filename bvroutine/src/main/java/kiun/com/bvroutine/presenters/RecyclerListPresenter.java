package kiun.com.bvroutine.presenters;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.List;
import kiun.com.bvroutine.base.BaseHandler;
import kiun.com.bvroutine.base.BaseRecyclerAdapter;
import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.data.QueryBean;
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
            if (((PagerBean) rootRequest).getPageNum() < ((PagerBean) rootRequest).getPages()){
                ((PagerBean) rootRequest).setPageNum(((PagerBean) rootRequest).getPageNum() + 1);
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

    protected void onDataComplete(List<T> v){
        loadAdapter.add(v);
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        reload();
    }
}