package kiun.com.bvroutine.interfaces.view;

import java.util.List;
import kiun.com.bvroutine.data.PagerBean;

public interface ListRequestView<T> extends BaseView{
    List<T> requestPager(PagerBean bean);
}