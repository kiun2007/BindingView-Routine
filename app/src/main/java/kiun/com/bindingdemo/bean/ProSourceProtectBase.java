package kiun.com.bindingdemo.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sky on 2019/3/22.
 * 水源地基础
 */

public class ProSourceProtectBase implements Parcelable {
//    {
//        "lgtd":0,
//            "lttd":0,
//            "lgtdpc":null,
//            "lttdpc":null,
//            "wsCd":null,
//            "wsNm":"北京广安门测试",
//            "status":"1",
//            "type":"A",
//            "id":"a1007d3111e54ccab952e7ab8522a100"
//    }
    private String lgtd;
    private String lttd;
    private String lgtdpc;
    private String lttdpc;
    private String wsCd;
    private String wsNm;
    private String status;
    private String type;
    private String id;
    private String engSurId;
    private String objId;


    protected ProSourceProtectBase(Parcel in) {
        lgtd = in.readString();
        lttd = in.readString();
        lgtdpc = in.readString();
        lttdpc = in.readString();
        wsCd = in.readString();
        wsNm = in.readString();
        status = in.readString();
        type = in.readString();
        id = in.readString();
        engSurId = in.readString();
        objId = in.readString();
    }

    public static final Creator<ProSourceProtectBase> CREATOR = new Creator<ProSourceProtectBase>() {
        @Override
        public ProSourceProtectBase createFromParcel(Parcel in) {
            return new ProSourceProtectBase(in);
        }

        @Override
        public ProSourceProtectBase[] newArray(int size) {
            return new ProSourceProtectBase[size];
        }
    };

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

    public String getWsCd() {
        return wsCd;
    }

    public void setWsCd(String wsCd) {
        this.wsCd = wsCd;
    }

    public String getWsNm() {
        return wsNm;
    }

    public void setWsNm(String wsNm) {
        this.wsNm = wsNm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getEngSurId() {
        return engSurId;
    }

    public void setEngSurId(String engSurId) {
        this.engSurId = engSurId;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(lgtd);
        dest.writeString(lttd);
        dest.writeString(lgtdpc);
        dest.writeString(lttdpc);
        dest.writeString(wsCd);
        dest.writeString(wsNm);
        dest.writeString(status);
        dest.writeString(type);
        dest.writeString(id);
        dest.writeString(engSurId);
        dest.writeString(objId);
    }
}
