package kiun.com.bindingdemo.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sky on 2019/3/21.
 */

public class QueryTCListBase implements Parcelable {
//    {
//        "villageName":"剧场东街社区居委会",
//            "villageCode":"110109001012",
//            "villageFullName":null,
//            "status":1,
//            "lgtd":null,
//            "lttd":null,
//            "lgtdpc":-0.015498,
//            "lttdpc":-0.002728,
//            "isPoverty":"2",
//            "guid":"8330E74158F41759E0530DC3010A9154"
//    }
    private String villageName;
    private String villageCode;
    private String villageFullName;
    private String status;
    private String lgtd;
    private String lttd;
    private String lgtdpc;
    private String lttdpc;
    private String guid;
    private String engId;

    @Override
    public String toString() {
        return villageName;
    }

    protected QueryTCListBase(Parcel in) {
        villageName = in.readString();
        villageCode = in.readString();
        villageFullName = in.readString();
        status = in.readString();
        lgtd = in.readString();
        lttd = in.readString();
        lgtdpc = in.readString();
        lttdpc = in.readString();
        guid = in.readString();
        engId = in.readString();
    }

    public static final Creator<QueryTCListBase> CREATOR = new Creator<QueryTCListBase>() {
        @Override
        public QueryTCListBase createFromParcel(Parcel in) {
            return new QueryTCListBase(in);
        }

        @Override
        public QueryTCListBase[] newArray(int size) {
            return new QueryTCListBase[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(villageName);
        dest.writeString(villageCode);
        dest.writeString(villageFullName);
        dest.writeString(status);
        dest.writeString(lgtd);
        dest.writeString(lttd);
        dest.writeString(lgtdpc);
        dest.writeString(lttdpc);
        dest.writeString(guid);
        dest.writeString(engId);
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public String getVillageCode() {
        return villageCode;
    }

    public void setVillageCode(String villageCode) {
        this.villageCode = villageCode;
    }

    public String getVillageFullName() {
        return villageFullName;
    }

    public void setVillageFullName(String villageFullName) {
        this.villageFullName = villageFullName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getLgtdpc() {
        return lgtdpc;
    }

    public void setLgtdpc(String lgtdpc) {
        this.lgtdpc = lgtdpc;
    }

    public String getLttdpc() {
        return lttdpc;
    }

    public void setLttdpc(String lttdpc) {
        this.lttdpc = lttdpc;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getEngId() {
        return engId;
    }

    public void setEngId(String engId) {
        this.engId = engId;
    }
}
