package kiun.com.bvroutine.presenters;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;
import kiun.com.bvroutine.base.BaseHandler;
import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.interfaces.presenter.ListViewPresenter;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine.interfaces.view.ListRequestView;
import kiun.com.bvroutine.interfaces.view.LoadAdapter;
import kiun.com.bvroutine.views.adapter.RecyclerSimpleAdapter;

public class RecyclerListPresenter<T> extends RecyclerView.OnScrollListener implements ListViewPresenter<T>, SwipeRefreshLayout.OnRefreshListener {

    RecyclerView mRecyclerView;
    SwipeRefreshLayout mRefreshLayout;
    ListRequestView mRequestView;
    PagerBean mPagerBean;
    RequestBindingPresenter presenter;
    LoadAdapter loadAdapter;

    public RecyclerListPresenter(RecyclerView recyclerView, SwipeRefreshLayout refreshLayout){
        mRecyclerView = recyclerView;
        mRefreshLayout = refreshLayout;
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public PagerBean initRequest(PagerBean pagerBean, ListRequestView requestView) {
        mRequestView = requestView;
        mPagerBean = pagerBean;

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requestView.getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        return pagerBean;
    }

    @Override
    public void start(BaseHandler<T> hanlder, int itemLayout, int dataBr, RequestBindingPresenter p) {
        presenter = p;
        RecyclerSimpleAdapter adapter = new RecyclerSimpleAdapter(this, itemLayout, dataBr, hanlder);
        loadAdapter = adapter;

        mRecyclerView.setAdapter(adapter);
        presenter.addRequest(()->mRequestView.requestPager(mPagerBean), this::onDataComplete);
    }

    @Override
    public void loadMore() {
        if (mPagerBean.getPageNum() < mPagerBean.getPages()){
            mPagerBean.setPageNum(mPagerBean.getPageNum() + 1);
        }
        presenter.addRequest(()->mRequestView.requestPager(mPagerBean), this::onDataComplete);
    }

    @Override
    public void reload() {
        loadAdapter.clear();
        mPagerBean.setPageNum(1);
        presenter.addRequest(()->mRequestView.requestPager(mPagerBean), this::onDataComplete);
    }

    private void onDataComplete(List<T> v){
        loadAdapter.add(v);
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        reload();
    }
}