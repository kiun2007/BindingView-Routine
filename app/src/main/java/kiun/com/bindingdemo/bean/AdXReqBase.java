package kiun.com.bindingdemo.bean;

/**
 * Created by sky on 2019/3/22.
 * 人饮下请求参数
 */

public class AdXReqBase{
    String adCode;
    String engId;
    String objId;

    public AdXReqBase() {
    }

    public String getAdCode() {
        return adCode;
    }

    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }

    public String getEngId() {
        return engId;
    }

    public void setEngId(String engId) {
        this.engId = engId;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }
}
