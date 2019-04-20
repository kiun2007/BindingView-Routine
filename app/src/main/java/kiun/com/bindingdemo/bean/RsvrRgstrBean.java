package kiun.com.bindingdemo.bean;

import android.os.Parcel;
import android.os.Parcelable;

import kiun.com.bindingdemo.warp.NetListBeanWrapper;

/**
 * Created by sky on 2019/3/21.
 * 请求督查分类数据结果返回类
 */

public class RsvrRgstrBean implements Parcelable {
//    {
//        "code":"130627000000",
//            "id":"002004001006",
//            "nm":"唐县",
//            "ptype":"2",
//            "lgtd":114.982972,
//            "lttd":38.748204,
//            "wtdstState":"0",
//            "dataStat":null,
//            "state":null,
//            "engId":null,
//            "objId":"10955daa162c47f380e48421978d1860",
//            "regsNm":null,
//            "groupLeader":null,
//            "groupLeaderTel":null,
//            "recPersId":null,
//            "recPers":null,
//            "recPersTel":null,
//            "intm":null,
//            "uptm":null,
//            "note":null,
//            "inspGroupId":null,
//            "villNum":0,
//            "cwsNum":0,
//            "surNum":0
//    }
    private String code;
    private String id;
    private String nm;
    private String ptype;
    private String lgtd;
    private String lttd;
    private String wtdstState;
    private String dataStat;
    private String state;
    private String engId;
    private String objId;
    private String regsNm;
    private String groupLeader;
    private String groupLeaderTel;
    private String recPersId;
    private String recPers;
    private String recPersTel;
    private String intm;
    private String uptm;
    private String note;
    private String inspGroupId;
    private String villNum;
    private String cwsNum;
    private String surNum;
    private String wtdstId;
    private NetListBeanWrapper.DataListWrapper<QueryTCListLastBase> queryTCListBase;
    private NetListBeanWrapper.DataListWrapper<QueryListBase> queryListBase;
    private NetListBeanWrapper.DataListWrapper<ProSourceProtectBase> proSourceProtectBase;


    protected RsvrRgstrBean(Parcel in) {
        this.code = in.readString();
        this.id = in.readString();
        this.nm = in.readString();
        this.ptype = in.readString();
        this.lgtd = in.readString();
        this.lttd = in.readString();
        this.wtdstState = in.readString();
        this.dataStat = in.readString();
        this.state = in.readString();
        this.engId = in.readString();
        this.objId = in.readString();
        this.regsNm = in.readString();
        this.groupLeader = in.readString();
        this.groupLeaderTel = in.readString();
        this.recPersId = in.readString();
        this.recPers = in.readString();
        this.recPersTel = in.readString();
        this.intm = in.readString();
        this.uptm = in.readString();
        this.note = in.readString();
        this.inspGroupId = in.readString();
        this.villNum = in.readString();
        this.cwsNum = in.readString();
        this.surNum = in.readString();
        this.wtdstId = in.readString();

    }

    public static final Creator<RsvrRgstrBean> CREATOR = new Creator<RsvrRgstrBean>() {
        @Override
        public RsvrRgstrBean createFromParcel(Parcel in) {
            return new RsvrRgstrBean(in);
        }

        @Override
        public RsvrRgstrBean[] newArray(int size) {
            return new RsvrRgstrBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(id);
        dest.writeString(nm);
        dest.writeString(ptype);
        dest.writeString(lgtd);
        dest.writeString(lttd);
        dest.writeString(wtdstState);
        dest.writeString(dataStat);
        dest.writeString(state);
        dest.writeString(engId);
        dest.writeString(objId);
        dest.writeString(regsNm);
        dest.writeString(groupLeader);
        dest.writeString(groupLeaderTel);
        dest.writeString(recPersId);
        dest.writeString(recPers);
        dest.writeString(recPersTel);
        dest.writeString(intm);
        dest.writeString(uptm);
        dest.writeString(note);
        dest.writeString(inspGroupId);
        dest.writeString(villNum);
        dest.writeString(cwsNum);
        dest.writeString(villNum);
        dest.writeString(surNum);
        dest.writeString(wtdstId);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNm() {
        return nm;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    public String getLgtd() {
        return lgtd;
    }

    public void setLgtd(String lgtd) {
        this.lgtd = lgtd;
    }

    public String getLttd() {
        return lttd;
    }

    public void setLttd(String lttd) {
        this.lttd = lttd;
    }

    public String getWtdstState() {
        return wtdstState;
    }

    public void setWtdstState(String wtdstState) {
        this.wtdstState = wtdstState;
    }

    public String getDataStat() {
        return dataStat;
    }

    public void setDataStat(String dataStat) {
        this.dataStat = dataStat;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getRegsNm() {
        return regsNm;
    }

    public void setRegsNm(String regsNm) {
        this.regsNm = regsNm;
    }

    public String getGroupLeader() {
        return groupLeader;
    }

    public void setGroupLeader(String groupLeader) {
        this.groupLeader = groupLeader;
    }

    public String getGroupLeaderTel() {
        return groupLeaderTel;
    }

    public void setGroupLeaderTel(String groupLeaderTel) {
        this.groupLeaderTel = groupLeaderTel;
    }

    public String getRecPersId() {
        return recPersId;
    }

    public void setRecPersId(String recPersId) {
        this.recPersId = recPersId;
    }

    public String getRecPers() {
        return recPers;
    }

    public void setRecPers(String recPers) {
        this.recPers = recPers;
    }

    public String getRecPersTel() {
        return recPersTel;
    }

    public void setRecPersTel(String recPersTel) {
        this.recPersTel = recPersTel;
    }

    public String getIntm() {
        return intm;
    }

    public void setIntm(String intm) {
        this.intm = intm;
    }

    public String getUptm() {
        return uptm;
    }

    public void setUptm(String uptm) {
        this.uptm = uptm;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getInspGroupId() {
        return inspGroupId;
    }

    public void setInspGroupId(String inspGroupId) {
        this.inspGroupId = inspGroupId;
    }

    public String getVillNum() {
        return villNum;
    }

    public void setVillNum(String villNum) {
        this.villNum = villNum;
    }

    public String getCwsNum() {
        return cwsNum;
    }

    public void setCwsNum(String cwsNum) {
        this.cwsNum = cwsNum;
    }

    public String getSurNum() {
        return surNum;
    }

    public void setSurNum(String surNum) {
        this.surNum = surNum;
    }

    public String getWtdstId() {
        return wtdstId;
    }

    public void setWtdstId(String wtdstId) {
        this.wtdstId = wtdstId;
    }

    public NetListBeanWrapper.DataListWrapper<QueryTCListLastBase> getQueryTCListBase() {
        return queryTCListBase;
    }

    public void setQueryTCListBase(NetListBeanWrapper.DataListWrapper<QueryTCListLastBase> queryTCListBase) {
        this.queryTCListBase = queryTCListBase;
    }

    public NetListBeanWrapper.DataListWrapper<QueryListBase> getQueryListBase() {
        return queryListBase;
    }

    public void setQueryListBase(NetListBeanWrapper.DataListWrapper<QueryListBase> queryListBase) {
        this.queryListBase = queryListBase;
    }

    public NetListBeanWrapper.DataListWrapper<ProSourceProtectBase> getProSourceProtectBase() {
        return proSourceProtectBase;
    }

    public void setProSourceProtectBase(NetListBeanWrapper.DataListWrapper<ProSourceProtectBase> proSourceProtectBase) {
        this.proSourceProtectBase = proSourceProtectBase;
    }

    @Override
    public String toString() {
        return nm;
    }
}
