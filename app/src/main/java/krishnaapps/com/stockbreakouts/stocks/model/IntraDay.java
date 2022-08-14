package krishnaapps.com.stockbreakouts.stocks.model;

import android.os.Parcel;
import android.os.Parcelable;

public class IntraDay implements Parcelable {
    private String mName;
    private String mImageUrl;
    private int mKey;
    private String mDesc;
    private String mDate;

    public IntraDay() {
        //empty constructor needed
    }

    public IntraDay(String name, String imageUrl, int key, String desc, String date) {
        if (name.trim().equals("")) {
            name = "No Name";
        }

        mName = name;
        mImageUrl = imageUrl;
        mKey = key;
        mDesc = desc;
        mDate = date;
    }

    protected IntraDay(Parcel in) {
        mName = in.readString();
        mImageUrl = in.readString();
        mKey = in.readInt();
        mDesc = in.readString();
        mDate = in.readString();
    }

    public static final Creator<IntraDay> CREATOR = new Creator<IntraDay>() {
        @Override
        public IntraDay createFromParcel(Parcel in) {
            return new IntraDay(in);
        }

        @Override
        public IntraDay[] newArray(int size) {
            return new IntraDay[size];
        }
    };

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public int getKey() {
        return mKey;
    }

    public void setKey(int key) {
        mKey = key;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public String getDesc() {
        return mDesc;
    }

    public void setDesc(String mDesc) {
        this.mDesc = mDesc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeString(mImageUrl);
        parcel.writeInt(mKey);
        parcel.writeString(mDesc);
        parcel.writeString(mDate);
    }
}
