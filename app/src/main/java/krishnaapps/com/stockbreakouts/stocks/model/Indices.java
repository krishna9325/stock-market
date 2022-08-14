package krishnaapps.com.stockbreakouts.stocks.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Indices implements Parcelable {

    private String indicesName;
    private String indicesImageUrl;
    private int indicesKey;
    private String indicesDesc;
    private String indicesDate;

    public Indices() {
        //empty constructor needed
    }

    public Indices(String name, String imageUrl, int key, String desc, String date) {
        if (name.trim().equals("")) {
            name = "No Name";
        }

        indicesName = name;
        indicesImageUrl = imageUrl;
        indicesKey = key;
        indicesDesc = desc;
        indicesDate = date;
    }

    protected Indices(Parcel in) {
        indicesName = in.readString();
        indicesImageUrl = in.readString();
        indicesKey = in.readInt();
        indicesDesc = in.readString();
        indicesDate = in.readString();
    }

    public static final Creator<Indices> CREATOR = new Creator<Indices>() {
        @Override
        public Indices createFromParcel(Parcel in) {
            return new Indices(in);
        }

        @Override
        public Indices[] newArray(int size) {
            return new Indices[size];
        }
    };

    public String getIndicesName() {
        return indicesName;
    }

    public void setIndicesName(String name) {
        indicesName = name;
    }

    public String getIndicesImageUrl() {
        return indicesImageUrl;
    }

    public void setIndicesImageUrl(String imageUrl) {
        indicesImageUrl = imageUrl;
    }

    public int getIndicesKey() {
        return indicesKey;
    }

    public void setIndicesKey(int key) {
        indicesKey = key;
    }

    public String getIndicesDate() {
        return indicesDate;
    }

    public void setIndicesDate(String mDate) {
        this.indicesDate = mDate;
    }

    public String getIndicesDesc() {
        return indicesDesc;
    }

    public void setIndicesDesc(String mDesc) {
        this.indicesDesc = mDesc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(indicesName);
        parcel.writeString(indicesImageUrl);
        parcel.writeInt(indicesKey);
        parcel.writeString(indicesDesc);
        parcel.writeString(indicesDate);
    }
}
