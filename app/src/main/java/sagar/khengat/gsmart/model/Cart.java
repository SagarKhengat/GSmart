package sagar.khengat.gsmart.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;


public class Cart implements Parcelable {
    @DatabaseField(canBeNull = true)

    private String productName;
    @DatabaseField(foreign=true, foreignAutoRefresh=true, canBeNull=true,
            maxForeignAutoRefreshLevel=3)

    private Category productCategory;
    @DatabaseField(foreign=true, foreignAutoRefresh=true, canBeNull=true,
            maxForeignAutoRefreshLevel=3)
    private Store store;

    @DatabaseField(foreign=true, foreignAutoRefresh=true, canBeNull=true,
            maxForeignAutoRefreshLevel=3)

    private SubCategory productSubCategory;

    @DatabaseField(foreign=true, foreignAutoRefresh=true, canBeNull=true,
            maxForeignAutoRefreshLevel=3)

    private Area productArea;

    @DatabaseField(canBeNull = true)

    private String productUnit;


    @DatabaseField(canBeNull = true,generatedId = true)

    private int productId;


    @DatabaseField(canBeNull = true)

    private String productSize;


    @DatabaseField(canBeNull = true)


    private double productOriginalPrice;
    @DatabaseField(canBeNull = true)


    private double productGstPrice;
    @DatabaseField(canBeNull = true)

    private int productQuantity;


    @DatabaseField(canBeNull = true)


    private double productTotalPrice;

    @DatabaseField(canBeNull = true)

    private String productCartId;

    public String getProductCartId() {
        return productCartId;
    }

    public void setProductCartId(String productCartId) {
        this.productCartId = productCartId;
    }

    public Area getProductArea() {
        return productArea;
    }

    public void setProductArea(Area productArea) {
        this.productArea = productArea;
    }

    public double getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(double productToatalPrice) {
        this.productTotalPrice = productToatalPrice;
    }




    public double getProductGstPrice() {
        return productGstPrice;
    }

    public void setProductGstPrice(double productGstPrice) {
        this.productGstPrice = productGstPrice;
    }

    public double getProductOriginalPrice() {
        return productOriginalPrice;
    }

    public void setProductOriginalPrice(double productOriginalPrice) {
        this.productOriginalPrice = productOriginalPrice;
    }


    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }


    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }


    public Category getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(Category productCategory) {
        this.productCategory = productCategory;
    }

    public SubCategory getProductSubCategory() {
        return productSubCategory;
    }

    public void setProductSubCategory(SubCategory productSubCategory) {
        this.productSubCategory = productSubCategory;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }


    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(productId);
        dest.writeInt(productQuantity);
        dest.writeString(productSize);
        dest.writeString(productCartId);
        dest.writeDouble(productOriginalPrice);
        dest.writeDouble(productGstPrice);
        dest.writeDouble(productTotalPrice);
        dest.writeString(productName);

        dest.writeValue(productCategory);
        dest.writeValue(productArea);
        dest.writeString(productUnit);
        dest.writeValue(productSubCategory);
        dest.writeValue(store);
    }
    public static final Creator<Cart> CREATOR = new Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel source) {
            return new Cart(source);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };
    public Cart(Parcel in) {
        productId = in.readInt();
        productName = in.readString();
        productCartId = in.readString();
        store = (Store) in.readValue(Product.class.getClassLoader());

        productCategory = (Category) in.readValue(Product.class.getClassLoader());
        productSubCategory = (SubCategory) in.readValue(Product.class.getClassLoader());
        productArea = (Area) in.readValue(Product.class.getClassLoader());
        productQuantity = in.readInt();
        productSize = in.readString();
        productUnit = in.readString();
        productOriginalPrice = in.readDouble();
        productTotalPrice = in.readDouble();
        productGstPrice = in.readDouble();
    }

    public Cart() {
    }

    @Override
    public String toString() {
        return "Cart{" +
                "productName='" + productName + '\'' +
                ", productSubCategory='" + productSubCategory + '\'' +
                ", store=" + store +
                ", productCategory='" + productCategory + '\'' +
                ", productUnit='" + productUnit + '\'' +
                ", productCartId='" + productCartId + '\'' +
                ", productId=" + productId +
                ", productArea=" + productArea +
                ", productSize='" + productSize + '\'' +
                ", productOriginalPrice=" + productOriginalPrice +
                ", productGstPrice=" + productGstPrice +
                ", productQuantity=" + productQuantity +
                ", productTotalPrice=" + productTotalPrice +
                '}';
    }
}
