package kiun.com.bindingdemo.bean.request;

import android.databinding.ObservableField;

import kiun.com.bvroutine.data.PagerBean;


public class PblmListReqBean extends PagerBean {

    public ObservableField<String> inspPblmName = new ObservableField<>();
    public ObservableField<String> villType = new ObservableField<>();
}