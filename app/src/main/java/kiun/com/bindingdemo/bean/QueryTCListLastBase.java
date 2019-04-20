package kiun.com.bindingdemo.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by sky on 2019/3/21.
 */

public class QueryTCListLastBase implements Parcelable {
    private String townCode;
    private String townName;
    private String townFullName;
    private String status;
    private String guid;
    private ArrayList<QueryTCListBase> list;

    protected QueryTCListLastBase(Parcel in) {
        townCode = in.readString();
        townName = in.readString();
        townFullName = in.readString();
        status = in.readString();
        guid = in.readString();

    }

    public static final Creator<QueryTCListLastBase> CREATOR = new Creator<QueryTCListLastBase>() {
        @Override
        public QueryTCListLastBase createFromParcel(Parcel in) {
            return new QueryTCListLastBase(in);
        }

        @Override
        public QueryTCListLastBase[] newArray(int size) {
            return new QueryTCListLastBase[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(townCode);
        dest.writeString(townName);
        dest.writeString(townFullName);
        dest.writeString(status);
        dest.writeString(guid);

    }

    public String getTownCode() {
        return townCode;
    }

    public void setTownCode(String townCode) {
        this.townCode = townCode;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public String getTownFullName() {
        return townFullName;
    }

    public void setTownFullName(String townFullName) {
        this.townFullName = townFullName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public ArrayList<QueryTCListBase> getList() {
        return list;
    }

    public void setList(ArrayList<QueryTCListBase> list) {
        this.list = list;
    }
}
