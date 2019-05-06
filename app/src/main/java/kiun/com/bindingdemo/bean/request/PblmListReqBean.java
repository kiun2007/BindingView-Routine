package kiun.com.bindingdemo.bean.request;

import android.databinding.ObservableField;

import kiun.com.bvroutine.data.PagerBean;


public class PblmListReqBean extends PagerBean {

    private String inspPblmName;
    public ObservableField<String> villType = new ObservableField<>();

    public static int a = 0;

    public String getInspPblmName() {
        return inspPblmName;
    }

    public void setInspPblmName(String inspPblmName) {
        this.inspPblmName = inspPblmName;
    }
}