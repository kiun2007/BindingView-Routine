package kiun.com.bindingdemo.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sky on 2019/3/22.
 * 饮水工程返回数据对象
 */

public class QueryListBase implements Parcelable {
    //    {
//        "addvcd":"110109000000",
//            "guid":"35f2b88d85424420a1f25a8ac680c7be",
//            "cwsCode":"110109000398",
//            "cwsName":"20190316",
//            "cwsLong":116.351968,
//            "cwsLat":39.887326,
//            "cwsLoc":"测试所在地",
//            "engType":null,
//            "wasuType":null,
//            "wasuRang":null,
//            "desWasuScal":23577,
//            "desWasuPop":85.36,
//            "engStat":null,
//            "startDate":null,
//            "compDate":"2012-03-16 00:00:00.0",
//            "note":null,
//            "effDate":"2019-03-16 15:21:07.0",
//            "exprDate":null,
//            "status":0
//    }
    private String addvcd;
    private String guid;
    private String cwsCode;
    private String cwsName;
    private String cwsLong;
    private String cwsLat;
    private String cwsLoc;
    private String engType;
    private String wasuType;
    private String wasuRang;
    private String desWasuScal;
    private String desWasuPop;
    private String engStat;
    private String startDate;
    private String compDate;
    private String note;
    private String effDate;
    private String exprDate;
    private String status;
    private String engId;

    @Override
    public String toString() {
        return cwsName;
    }

    protected QueryListBase(Parcel in) {
        addvcd = in.readString();
        guid = in.readString();
        cwsCode = in.readString();
        cwsName = in.readString();
        cwsLong = in.readString();
        cwsLat = in.readString();
        cwsLoc = in.readString();
        engType = in.readString();
        wasuType = in.readString();
        wasuRang = in.readString();
        desWasuScal = in.readString();
        desWasuPop = in.readString();
        engStat = in.readString();
        startDate = in.readString();
        compDate = in.readString();
        note = in.readString();
        effDate = in.readString();
        exprDate = in.readString();
        status = in.readString();
        engId = in.readString();
    }

    public static final Creator<QueryListBase> CREATOR = new Creator<QueryListBase>() {
        @Override
        public QueryListBase createFromParcel(Parcel in) {
            return new QueryListBase(in);
        }

        @Override
        public QueryListBase[] newArray(int size) {
            return new QueryListBase[size];
        }
    };

    public String getAddvcd() {
        return addvcd;
    }

    public void setAddvcd(String addvcd) {
        this.addvcd = addvcd;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getCwsCode() {
        return cwsCode;
    }

    public void setCwsCode(String cwsCode) {
        this.cwsCode = cwsCode;
    }

    public String getCwsName() {
        return cwsName;
    }

    public void setCwsName(String cwsName) {
        this.cwsName = cwsName;
    }

    public String getCwsLong() {
        return cwsLong;
    }

    public void setCwsLong(String cwsLong) {
        this.cwsLong = cwsLong;
    }

    public String getCwsLat() {
        return cwsLat;
    }

    public void setCwsLat(String cwsLat) {
        this.cwsLat = cwsLat;
    }

    public String getCwsLoc() {
        return cwsLoc;
    }

    public void setCwsLoc(String cwsLoc) {
        this.cwsLoc = cwsLoc;
    }

    public String getEngType() {
        return engType;
    }

    public void setEngType(String engType) {
        this.engType = engType;
    }

    public String getWasuType() {
        return wasuType;
    }

    public void setWasuType(String wasuType) {
        this.wasuType = wasuType;
    }

    public String getWasuRang() {
        return wasuRang;
    }

    public void setWasuRang(String wasuRang) {
        this.wasuRang = wasuRang;
    }

    public String getDesWasuScal() {
        return desWasuScal;
    }

    public void setDesWasuScal(String desWasuScal) {
        this.desWasuScal = desWasuScal;
    }

    public String getDesWasuPop() {
        return desWasuPop;
    }

    public void setDesWasuPop(String desWasuPop) {
        this.desWasuPop = desWasuPop;
    }

    public String getEngStat() {
        return engStat;
    }

    public void setEngStat(String engStat) {
        this.engStat = engStat;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getCompDate() {
        return compDate;
    }

    public void setCompDate(String compDate) {
        this.compDate = compDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getEffDate() {
        return effDate;
    }

    public void setEffDate(String effDate) {
        this.effDate = effDate;
    }

    public String getExprDate() {
        return exprDate;
    }

    public void setExprDate(String exprDate) {
        this.exprDate = exprDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEngId() {
        return engId;
    }

    public void setEngId(String engId) {
        this.engId = engId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(addvcd);
        dest.writeString(guid);
        dest.writeString(cwsCode);
        dest.writeString(cwsName);
        dest.writeString(cwsLong);
        dest.writeString(cwsLat);
        dest.writeString(cwsLoc);
        dest.writeString(engType);
        dest.writeString(wasuType);
        dest.writeString(wasuRang);
        dest.writeString(desWasuScal);
        dest.writeString(desWasuPop);
        dest.writeString(engStat);
        dest.writeString(startDate);
        dest.writeString(compDate);
        dest.writeString(note);
        dest.writeString(effDate);
        dest.writeString(exprDate);
        dest.writeString(status);
        dest.writeString(engId);
    }
}
