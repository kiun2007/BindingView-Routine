package kiun.com.bindingdemo.bean.request;

import android.os.Parcel;
import android.os.Parcelable;

import kiun.com.bvroutine.data.PagerBean;

/**
 * Created by sky on 2019/3/21.
 * 批次分类请求参数类
 */

public class RsvrRgstrReqBean extends PagerBean {

    private String plnaId;
    private String presId;
    private String pType;
    private String type;
    private String state;

    public String getPlnaId() {
        return plnaId;
    }

    public void setPlnaId(String plnaId) {
        this.plnaId = plnaId;
    }

    public String getPresId() {
        return presId;
    }

    public void setPresId(String presId) {
        this.presId = presId;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
