package kiun.com.bvroutine.interfaces.presenter;

import kiun.com.bvroutine.base.BaseHandler;
import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.interfaces.view.ListRequestView;

public interface ListViewPresenter<T>{

    PagerBean initRequest(PagerBean pagerBean, ListRequestView requestView);

    void start(BaseHandler<T> hanlder, int itemLayout, int dataBr, RequestBindingPresenter p);

    void loadMore();
}