package kiun.com.bvroutine.base;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import java.util.HashMap;
import java.util.Map;
import kiun.com.bvroutine.R;
import kiun.com.bvroutine.data.BaseBean;
import kiun.com.bvroutine.interfaces.callers.SetCaller;
import kiun.com.bvroutine.interfaces.view.BindingView;
import kiun.com.bvroutine.utils.StatusBarUtils;
import kiun.com.bvroutine.views.NavigatorBar;
import kiun.com.bvroutine.views.viewmodel.ActionBarItem;

public abstract class BVBaseActivity<T extends ViewDataBinding> extends AppCompatActivity implements BindingView {

    protected T mViewBinding = null;
    private ActionBarItem barItem = null;
    private Map<Integer, SetCaller<Intent>> resultCallers = new HashMap<>();
    NavigatorBar barView;
    int requestIdBase = 50;

    public boolean isWithActionBar(){
        return true;
    }

    private void create(){

        if(isWithActionBar()){
            setContentView(R.layout.activiy_base);
            mViewBinding = DataBindingUtil.inflate(LayoutInflater.from(this), getViewId(), findViewById(R.id.view_content), true);
            barView = findViewById(R.id.view_actionbar);
        }else{
            mViewBinding = DataBindingUtil.setContentView(this, getViewId());
            barView = mViewBinding.getRoot().findViewWithTag(NavigatorBar.TAG);
        }

        if(barView != null){
            barItem = barView.getBarItem();
        }
        StatusBarUtils.with(this).setColor(-1).init();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        initView();
    }

    public NavigatorBar getBarView() {
        return barView;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        create();
    }

    protected <B extends BaseBean> B getExtra(){
        return getIntent().getParcelableExtra("EXTRA");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        create();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SetCaller caller = resultCallers.remove(requestCode);
        if (caller != null && resultCode == Activity.RESULT_OK){
            caller.call(data);
        }
    }

    public ActionBarItem getBarItem() {
        return barItem;
    }

    public void setBarItem(ActionBarItem barItem){
        if (barView != null){
            barView.setBarItem(barItem);
        }
    }

    public void startForResult(Class clz, String key, Parcelable value, SetCaller<Intent> caller){
        Intent intent = new Intent(this, clz);
        if (value != null){
            intent.putExtra(key, value);
        }
        startForResult(intent, caller);
    }

    public void startForResult(Intent intent, SetCaller<Intent> caller){
        if (requestIdBase > 5000){
            requestIdBase = 50;
        }
        int requestId = requestIdBase++;

        resultCallers.put(requestId, caller);
        startActivityForResult(intent, requestId);
    }

    public void startForResult(Class clz, SetCaller<Intent> caller){
        startForResult(clz, null,null,caller);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
