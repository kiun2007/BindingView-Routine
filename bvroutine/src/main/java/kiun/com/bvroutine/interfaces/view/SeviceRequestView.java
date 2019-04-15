package kiun.com.bvroutine.interfaces.view;

import android.content.Context;
import android.support.v4.app.LoaderManager;

import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;

public interface SeviceRequestView extends BaseView{

    <T>T createService(Class<T> serviceClass);

    RequestBindingPresenter getRequestPresenter();

    LoaderManager getSupportLoaderManager();
}