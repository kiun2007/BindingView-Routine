package kiun.com.bvroutine.presenters;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.data.KeyValue;
import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.interfaces.callers.GetTNoParamCall;
import kiun.com.bvroutine.interfaces.callers.ServiceCaller;
import kiun.com.bvroutine.interfaces.callers.SetCaller;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine.interfaces.view.SeviceRequestView;
import kiun.com.bvroutine.interfaces.warp.DataWarp;
import kiun.com.bvroutine.interfaces.warp.ListWarp;
import kiun.com.bvroutine.presenters.loader.GetAsyncLoader;
import retrofit2.Call;
import retrofit2.Response;

public class RetrofitRequestPresenter implements RequestBindingPresenter, LoaderManager.LoaderCallbacks {

    SeviceRequestView viewInterface;
    LoaderManager loaderManager;
    Map<Integer, KeyValue<GetTNoParamCall, SetCaller>> requestMaps = new HashMap<>();

    public RetrofitRequestPresenter(SeviceRequestView viewInterface){
        this.viewInterface = viewInterface;
        loaderManager = viewInterface.getSupportLoaderManager();
    }

    @Override
    public <T> void addRequest(GetTNoParamCall<T> serviceCaller, SetCaller<T> setCaller) {
        int taskId = (int) (Math.random() * 1000 * 1000 * 1000);
        requestMaps.put(taskId, new KeyValue<>(serviceCaller, setCaller));
        loaderManager.restartLoader(taskId, new Bundle(), this);
    }

    @Override
    public boolean isConcurrent() {
        return true;
    }

    @Override
    public <T, E> E callServiceData(Class<T> serviceClass, ServiceCaller<T> callback) {
        T services = viewInterface.createService(serviceClass);

        Call<E> call = callback.requestCall(services);
        try {
            Response<E> response = call.execute();
            E warp = response.body();
            if(warp instanceof DataWarp){
                return (E) ((DataWarp) warp).getData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T, E> List<E> callServiceList(Class<T> serviceClass, ServiceCaller<T> callback, PagerBean pagerBean) {
        T services = viewInterface.createService(serviceClass);

        Call call = callback.requestCall(services);
        try {
            Response response = call.execute();
            Object warp = response.body();
            if(warp instanceof ListWarp){
                if(pagerBean != null){
                    pagerBean.setPages(((ListWarp) warp).getPages());
                }
                return ((ListWarp) warp).getList();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @NonNull
    @Override
    public Loader onCreateLoader(int id, @Nullable Bundle args) {

        if(requestMaps.containsKey(id)){
            KeyValue<GetTNoParamCall, SetCaller> request = requestMaps.remove(id);
            return new GetAsyncLoader(viewInterface.getContext(), request.key(), request.value());
        }
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Object data) {
        if (loader instanceof GetAsyncLoader){
            ((GetAsyncLoader) loader).getCallback().call(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {
    }
}
