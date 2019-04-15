package kiun.com.bvroutine.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine.interfaces.view.SeviceRequestView;
import kiun.com.bvroutine.presenters.RetrofitRequestPresenter;

public abstract class RequestBVActivity<T extends ViewDataBinding> extends BVBaseActivity<T> implements SeviceRequestView {

    protected RequestBindingPresenter requestBindingPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public RequestBindingPresenter getRequestPresenter() {
        if(requestBindingPresenter == null){
            requestBindingPresenter = new RetrofitRequestPresenter(this);
        }
        return requestBindingPresenter;
    }
}