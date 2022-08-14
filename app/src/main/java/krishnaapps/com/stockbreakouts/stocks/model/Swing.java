package krishnaapps.com.stockbreakouts.stocks.model;


import android.os.Parcel;
import android.os.Parcelable;

public class Swing implements Parcelable {
    private String swingName;
    private String swingImageUrl;
    private int swingKey;
    private String swingDesc;
    private String swingDate;

    public Swing() {
        //empty constructor needed
    }

    public Swing(String name, String imageUrl, int key, String desc, String date) {
        if (name.trim().equals("")) {
            name = "No Name";
        }

        swingName = name;
        swingImageUrl = imageUrl;
        swingKey = key;
        swingDesc = desc;
        swingDate = date;
    }

    protected Swing(Parcel in) {
        swingName = in.readString();
        swingImageUrl = in.readString();
        swingKey = in.readInt();
        swingDesc = in.readString();
        swingDate = in.readString();
    }

    public static final Creator<Swing> CREATOR = new Creator<Swing>() {
        @Override
        public Swing createFromParcel(Parcel in) {
            return new Swing(in);
        }

        @Override
        public Swing[] newArray(int size) {
            return new Swing[size];
        }
    };

    public String getSwingName() {
        return swingName;
    }

    public void setSwingName(String name) {
        swingName = name;
    }

    public String getSwingImageUrl() {
        return swingImageUrl;
    }

    public void setSwingImageUrl(String imageUrl) {
        swingImageUrl = imageUrl;
    }

    public int getSwingKey() {
        return swingKey;
    }

    public void setSwingKey(int key) {
        swingKey = key;
    }

    public String getSwingDate() {
        return swingDate;
    }

    public void setSwingDate(String mDate) {
        this.swingDate = mDate;
    }

    public String getSwingDesc() {
        return swingDesc;
    }

    public void setSwingDesc(String mDesc) {
        this.swingDesc = mDesc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(swingName);
        parcel.writeString(swingImageUrl);
        parcel.writeInt(swingKey);
        parcel.writeString(swingDesc);
        parcel.writeString(swingDate);
    }
}


