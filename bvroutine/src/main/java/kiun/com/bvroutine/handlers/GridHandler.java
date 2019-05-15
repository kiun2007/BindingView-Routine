package kiun.com.bvroutine.handlers;

import android.content.Context;

import kiun.com.bvroutine.base.BaseHandler;
import kiun.com.bvroutine.interfaces.callers.SetCaller;
import kiun.com.bvroutine.interfaces.presenter.GridPresenter;

public class GridHandler<T> extends BaseHandler<T> {

    GridPresenter presenter;
    SetCaller<T> caller;

    public GridHandler(int hanlderBr, SetCaller<T> caller) {
        super(hanlderBr);
        this.caller = caller;
    }

    public void remove(T item){
        if (presenter != null){
            presenter.remove(item);
        }
    }

    @Override
    public void onClick(Context context, int tag, T data) {
        if (caller != null){
            caller.call(data);
        }
    }

    public void setPresenter(GridPresenter presenter) {
        this.presenter = presenter;
    }
}
