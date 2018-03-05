package sagar.khengat.gsmart.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;

public class Retailer implements Parcelable {
    @DatabaseField(canBeNull = true,generatedId = true)
    private int id;
    @DatabaseField(canBeNull = true,unique = true)
    private String name;
    @DatabaseField(canBeNull = true,unique = true)
    private String mobno;
    @DatabaseField(canBeNull = true,unique = true)
    private String storeName;
    @DatabaseField(canBeNull = true)
    private String password;
    @DatabaseField(foreign = true)
    private Area area;
    @DatabaseField(canBeNull = true)
    private String storeAddress;


    public String getMobno() {
        return mobno;
    }

    public void setMobno(String mobno) {
        this.mobno = mobno;
    }



    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(mobno);
        dest.writeString(password);
        dest.writeString(storeName);
        dest.writeString(storeAddress);
        dest.writeValue(area);


    }

    public static final Creator<Retailer> CREATOR = new Creator<Retailer>() {
        @Override
        public Retailer createFromParcel(Parcel source) {
            return new Retailer(source);
        }

        @Override
        public Retailer[] newArray(int size) {
            return new Retailer[size];
        }
    };
    public Retailer(Parcel in) {
        name = in.readString();
        mobno = in.readString();
        password = in.readString();
        storeName = in.readString();
        storeAddress = in.readString();
        id = in.readInt();
        area = (Area) in.readValue(Store.class.getClassLoader());

    }

    @Override
    public String toString() {
        return "Retailer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mobno='" + mobno + '\'' +
                ", area='" + area + '\'' +
                ", storeName='" + storeName + '\'' +
                ", storeAddress='" + storeAddress + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Retailer() {
    }
}
