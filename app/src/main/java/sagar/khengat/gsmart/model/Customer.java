package sagar.khengat.gsmart.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Sagar Khengat on 02/03/2018.
 */

public class Customer implements Parcelable {
    @DatabaseField(canBeNull = true,generatedId = true)
    private int id;
    @DatabaseField(canBeNull = true)
    private String name;
    @DatabaseField(canBeNull = true,unique = true)
    private String contactNo;
    @DatabaseField(canBeNull = true)
    private String password;

    @DatabaseField(canBeNull = true)
    private String address;
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

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        dest.writeString(contactNo);
        dest.writeString(password);
        dest.writeString(address);

    }

    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel source) {
            return new Customer(source);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };
    public Customer(Parcel in) {
        name = in.readString();
        contactNo = in.readString();
        password = in.readString();
        address = in.readString();
        id = in.readInt();
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contactNo='" + contactNo + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public Customer() {
    }
}
