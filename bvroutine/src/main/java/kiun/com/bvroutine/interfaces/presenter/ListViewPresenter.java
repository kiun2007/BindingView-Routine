package kiun.com.bvroutine.interfaces.presenter;

import kiun.com.bvroutine.base.BaseHandler;
import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.data.QueryBean;
import kiun.com.bvroutine.interfaces.view.ListRequestView;

public interface ListViewPresenter<T,Q extends QueryBean, Req extends ListRequestView>{

    Q initRequest(Q rootRequest, Req requestView);

    void start(BaseHandler<T> hanlder, int itemLayout, int dataBr, RequestBindingPresenter p);

    void loadMore();

    void reload();
}