package kiun.com.bindingdemo.bean;

import android.os.Parcel;

import kiun.com.bvroutine.data.BaseBean;


public class PblmBean extends BaseBean {

    /**
     * cwsCode : null
     * villageCode : null
     * pblmsTypeId : 10000000000000000000000000000232
     * regid : 8183425013f14390a9b9246a673e4428
     * commonFileIds : -1
     * fileNo : null
     * fileNoNumber : null
     * pblmsId : 00000000000000000000000000000000
     * pblmId : 5e493fcc5879450c8f19dd10220c1569
     * objId : 63f547230c334dcb892e4b4cf135b0a0
     * objType : 1
     * inspGroupId : null
     * pguid : null
     * inspPblmType : 13
     * inspPblmCode : 81
     * inspPblmName : 工程实体
     * inspPblmDesc : 泄洪道进口拦鱼装置未拆除。
     * inspAddDesc : null
     * pblmLong : 100.241788
     * pblmLat : 25.587799
     * ifCasePblm : 0
     * inspPblmOrgName : null
     * pblmPersName : null
     * inspPblmCate : 1
     * pblmStat : 0
     * reviOpin : null
     * reviConc : null
     * reviOrgGuid : null
     * dataStat : 1
     * collTime : 1555212495308
     * recPers : 251
     * note : null
     * villType : 0
     * persName : 高磊
     * inspPblmsName : 工程实体
     * checkPoint : 泄洪建筑物
     * nm : 米总水库
     * objNm : null
     * cwsName : null
     * pblmDesc : null
     * srcDesc : 米总水库
     * startTime : null
     * endTime : null
     * state : null
     * code : 532901000016
     * adFullName : null
     * orgNm : null
     * orgId : null
     * adCode : null
     * rsName : null
     * province : null
     * city : null
     * country : null
     * gwComFiles : null
     */

    private String pblmsTypeId;
    private String regid;
    private String commonFileIds;
    private String pblmsId;
    private String pblmId;
    private String objId;
    private String objType;
    private String inspPblmType;
    private String inspPblmCode;
    private String inspPblmName;
    private String inspPblmDesc;
    private double pblmLong;
    private double pblmLat;
    private String ifCasePblm;
    private String inspPblmCate;
    private String pblmStat;
    private String dataStat;
    private long collTime;
    private String recPers;
    private String villType;
    private String persName;
    private String inspPblmsName;
    private String checkPoint;
    private String nm;
    private String srcDesc;
    private String code;

    protected PblmBean(Parcel in) {
        pblmsTypeId = in.readString();
        regid = in.readString();
        commonFileIds = in.readString();
        pblmsId = in.readString();
        pblmId = in.readString();
        objId = in.readString();
        objType = in.readString();
        inspPblmType = in.readString();
        inspPblmCode = in.readString();
        inspPblmName = in.readString();
        inspPblmDesc = in.readString();
        pblmLong = in.readDouble();
        pblmLat = in.readDouble();
        ifCasePblm = in.readString();
        inspPblmCate = in.readString();
        pblmStat = in.readString();
        dataStat = in.readString();
        collTime = in.readLong();
        recPers = in.readString();
        villType = in.readString();
        persName = in.readString();
        inspPblmsName = in.readString();
        checkPoint = in.readString();
        nm = in.readString();
        srcDesc = in.readString();
        code = in.readString();
    }

    public static final Creator<PblmBean> CREATOR = new Creator<PblmBean>() {
        @Override
        public PblmBean createFromParcel(Parcel in) {
            return new PblmBean(in);
        }

        @Override
        public PblmBean[] newArray(int size) {
            return new PblmBean[size];
        }
    };

    public String getPblmsTypeId() {
        return pblmsTypeId;
    }

    public void setPblmsTypeId(String pblmsTypeId) {
        this.pblmsTypeId = pblmsTypeId;
    }

    public String getRegid() {
        return regid;
    }

    public void setRegid(String regid) {
        this.regid = regid;
    }

    public String getCommonFileIds() {
        return commonFileIds;
    }

    public void setCommonFileIds(String commonFileIds) {
        this.commonFileIds = commonFileIds;
    }

    public String getPblmsId() {
        return pblmsId;
    }

    public void setPblmsId(String pblmsId) {
        this.pblmsId = pblmsId;
    }

    public String getPblmId() {
        return pblmId;
    }

    public void setPblmId(String pblmId) {
        this.pblmId = pblmId;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public String getObjType() {
        return objType;
    }

    public void setObjType(String objType) {
        this.objType = objType;
    }

    public String getInspPblmType() {
        return inspPblmType;
    }

    public void setInspPblmType(String inspPblmType) {
        this.inspPblmType = inspPblmType;
    }

    public String getInspPblmCode() {
        return inspPblmCode;
    }

    public void setInspPblmCode(String inspPblmCode) {
        this.inspPblmCode = inspPblmCode;
    }

    public String getInspPblmName() {
        return inspPblmName;
    }

    public void setInspPblmName(String inspPblmName) {
        this.inspPblmName = inspPblmName;
    }

    public String getInspPblmDesc() {
        return inspPblmDesc;
    }

    public void setInspPblmDesc(String inspPblmDesc) {
        this.inspPblmDesc = inspPblmDesc;
    }

    public double getPblmLong() {
        return pblmLong;
    }

    public void setPblmLong(double pblmLong) {
        this.pblmLong = pblmLong;
    }

    public double getPblmLat() {
        return pblmLat;
    }

    public void setPblmLat(double pblmLat) {
        this.pblmLat = pblmLat;
    }

    public String getIfCasePblm() {
        return ifCasePblm;
    }

    public void setIfCasePblm(String ifCasePblm) {
        this.ifCasePblm = ifCasePblm;
    }

    public String getInspPblmCate() {
        return inspPblmCate;
    }

    public void setInspPblmCate(String inspPblmCate) {
        this.inspPblmCate = inspPblmCate;
    }

    public String getPblmStat() {
        return pblmStat;
    }

    public void setPblmStat(String pblmStat) {
        this.pblmStat = pblmStat;
    }

    public String getDataStat() {
        return dataStat;
    }

    public void setDataStat(String dataStat) {
        this.dataStat = dataStat;
    }

    public long getCollTime() {
        return collTime;
    }

    public void setCollTime(long collTime) {
        this.collTime = collTime;
    }

    public String getRecPers() {
        return recPers;
    }

    public void setRecPers(String recPers) {
        this.recPers = recPers;
    }

    public String getVillType() {
        return villType;
    }

    public void setVillType(String villType) {
        this.villType = villType;
    }

    public String getPersName() {
        return persName;
    }

    public void setPersName(String persName) {
        this.persName = persName;
    }

    public String getInspPblmsName() {
        return inspPblmsName;
    }

    public void setInspPblmsName(String inspPblmsName) {
        this.inspPblmsName = inspPblmsName;
    }

    public String getCheckPoint() {
        return checkPoint;
    }

    public void setCheckPoint(String checkPoint) {
        this.checkPoint = checkPoint;
    }

    public String getNm() {
        return nm;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }

    public String getSrcDesc() {
        return srcDesc;
    }

    public void setSrcDesc(String srcDesc) {
        this.srcDesc = srcDesc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pblmsTypeId);
        dest.writeString(regid);
        dest.writeString(commonFileIds);
        dest.writeString(pblmsId);
        dest.writeString(pblmId);
        dest.writeString(objId);
        dest.writeString(objType);
        dest.writeString(inspPblmType);
        dest.writeString(inspPblmCode);
        dest.writeString(inspPblmName);
        dest.writeString(inspPblmDesc);
        dest.writeDouble(pblmLong);
        dest.writeDouble(pblmLat);
        dest.writeString(ifCasePblm);
        dest.writeString(inspPblmCate);
        dest.writeString(pblmStat);
        dest.writeString(dataStat);
        dest.writeLong(collTime);
        dest.writeString(recPers);
        dest.writeString(villType);
        dest.writeString(persName);
        dest.writeString(inspPblmsName);
        dest.writeString(checkPoint);
        dest.writeString(nm);
        dest.writeString(srcDesc);
        dest.writeString(code);
    }
}
