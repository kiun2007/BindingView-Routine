package kiun.com.bvroutine.base;

import android.app.Fragment;
import android.app.FragmentManager;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.data.BaseBean;
import kiun.com.bvroutine.utils.StatusBarUtils;
import kiun.com.bvroutine.views.NavigatorBar;
import kiun.com.bvroutine.views.viewmodel.ActionBarItem;

public abstract class BVBaseActivity<T extends ViewDataBinding> extends AppCompatActivity {

    protected T mViewBinding = null;
    private ActionBarItem barItem = null;
    NavigatorBar barView;

    public boolean isWithActionBar(){
        return true;
    }

    public abstract int getViewId();

    public abstract void initView();

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

    public ActionBarItem getBarItem() {
        return barItem;
    }

    public void setBarItem(ActionBarItem barItem){
        if (barView != null){
            barView.setBarItem(barItem);
        }
    }
}
