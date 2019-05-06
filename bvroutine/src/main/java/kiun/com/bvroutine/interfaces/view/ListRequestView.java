package kiun.com.bvroutine.interfaces.view;

import java.io.IOException;
import java.util.List;
import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.interfaces.presenter.ListViewPresenter;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;

public interface ListRequestView<Q> extends BaseView{

    List requestPager(RequestBindingPresenter p, Q bean) throws Exception;

    void loadComplete(ListViewPresenter<Q,?,?> p);
}