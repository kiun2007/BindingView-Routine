package kiun.com.bindingdemo.bean;


import kiun.com.bvroutine.interfaces.QueryParam;

/**
 * Created by sky on 2019/3/21.
 * 批次分类请求参数类
 */

public class RsvrRgstrReqBean{

    private String plnaId;
    private String presId;
    private String pType;
    private String state;
    private String prsnTitle;

    @QueryParam("type")
    private String prsnType;

    public RsvrRgstrReqBean(){}

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

    @QueryParam("pType")
    public String getPType() {
        if(prsnType == null){
            return "0";
        }
        return String.format("%d", "rsvrvillrect".indexOf(prsnType)/4 + 1);
    }

    @Override
    public String toString() {
        return prsnTitle;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPrsnTitle() {
        return prsnTitle;
    }

    public void setPrsnTitle(String prsnTitle) {
        this.prsnTitle = prsnTitle;
    }

    public String getPrsnType() {
        return prsnType;
    }

    public void setPrsnType(String prsnType) {
        this.prsnType = prsnType;
    }
}
