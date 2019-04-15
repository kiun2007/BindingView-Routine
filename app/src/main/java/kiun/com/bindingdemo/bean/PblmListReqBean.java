package kiun.com.bindingdemo.bean;

import kiun.com.bvroutine.data.PagerBean;

public class PblmListReqBean extends PagerBean {

    private String inspPblmName;
    private String villType;


    public String getInspPblmName() {
        return inspPblmName;
    }

    public void setInspPblmName(String inspPblmName) {
        this.inspPblmName = inspPblmName;
    }

    public String getVillType() {
        return villType;
    }

    public void setVillType(String villType) {
        this.villType = villType;
    }
}
