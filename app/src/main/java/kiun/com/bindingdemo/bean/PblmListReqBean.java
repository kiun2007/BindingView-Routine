package kiun.com.bindingdemo.bean;

import android.databinding.ObservableField;

import kiun.com.bvroutine.data.PagerBean;


public class PblmListReqBean extends PagerBean {

    public ObservableField<String> inspPblmName = new ObservableField<>();
    public ObservableField<String> villType = new ObservableField<>();

//
//    public String getInspPblmName() {
//        return inspPblmName.get();
//    }
//
//    public String getVillType() {
//        return villType.get();
//    }
}
