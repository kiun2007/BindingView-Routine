package kiun.com.bindingdemo.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sky on 2019/3/21.
 */

public class EngIdBase implements Parcelable {
    private String engId;

    public EngIdBase(String engId) {
        this.engId = engId;
    }

    public String getEngId() {
        return engId;
    }

    public void setEngId(String engId) {
        this.engId = engId;
    }

    protected EngIdBase(Parcel in) {
        engId = in.readString();
    }

    public static final Creator<EngIdBase> CREATOR = new Creator<EngIdBase>() {
        @Override
        public EngIdBase createFromParcel(Parcel in) {
            return new EngIdBase(in);
        }

        @Override
        public EngIdBase[] newArray(int size) {
            return new EngIdBase[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(engId);
    }
}
