package kiun.com.bvroutine.views.viewmodel;

import android.databinding.ObservableField;

import kiun.com.bvroutine.handlers.ActionBarHandler;

public class ActionBarItem{

    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<Boolean> barNoBack = new ObservableField<>();
    public ObservableField<String> backTitle = new ObservableField<>();
    private ActionBarHandler handler = null;

    public void setBarNoBack(boolean barNoBack) {
        this.barNoBack.set(barNoBack);
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public void setBackTitle(String title){
        backTitle.set(title);
    }

    public ActionBarHandler getHandler() {
        if(handler == null){
            handler = new ActionBarHandler();
        }
        return handler;
    }

    public void setHandler(ActionBarHandler handler) {
        this.handler = handler;
    }
}