package kiun.com.bvroutine.interfaces.presenter;

import java.util.List;

import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.interfaces.callers.GetTNoParamCall;
import kiun.com.bvroutine.interfaces.callers.ServiceCaller;
import kiun.com.bvroutine.interfaces.callers.SetCaller;
/**
 * 请求与绑定关系事务
 */
public interface RequestBindingPresenter {

    <T>void addRequest(GetTNoParamCall<T> serviceCaller, SetCaller<T> setCaller);

    boolean isConcurrent();

    <T,E>E callServiceData(Class<T> serviceClass, ServiceCaller<T> callback);

    <T,E>List<E> callServiceList(Class<T> serviceClass, ServiceCaller<T> callback, PagerBean pager);
}
