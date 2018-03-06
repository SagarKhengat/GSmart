package sagar.khengat.gsmart.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;

public class Product implements Parcelable{
    @DatabaseField(canBeNull = true)

    private String productName;
    @DatabaseField(foreign = true)

    private Category productCategory;
    @DatabaseField(foreign = true)
    private Store store;

    @DatabaseField(foreign = true)

    private SubCategory productSubCategory;
    @DatabaseField(canBeNull = true)

    private String productUnit;


    @DatabaseField(canBeNull = true,id = true)

    private String productId;


    @DatabaseField(canBeNull = true)

    private String productSize;
    @DatabaseField(canBeNull = true)

    private String productBrand;

    @DatabaseField(canBeNull = true)


    private double productOriginalPrice;
    @DatabaseField(canBeNull = true)


    private double productGstPrice;
    @DatabaseField(canBeNull = true)

    private int productQuantity;


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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
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
        dest.writeString(productId);
        dest.writeInt(productQuantity);
        dest.writeString(productSize);
        dest.writeDouble(productOriginalPrice);
        dest.writeDouble(productGstPrice);
        dest.writeString(productName);
        dest.writeString(productBrand);
        dest.writeValue(productCategory);
        dest.writeString(productUnit);
        dest.writeValue(productSubCategory);
        dest.writeValue(store);
    }
    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
    public Product(Parcel in) {
        productId = in.readString();
        productName = in.readString();
        store = (Store) in.readValue(Product.class.getClassLoader());
        productBrand =in.readString();
        productCategory = (Category) in.readValue(Product.class.getClassLoader());
        productSubCategory = (SubCategory) in.readValue(Product.class.getClassLoader());
        productQuantity = in.readInt();
        productSize = in.readString();
        productUnit = in.readString();
        productOriginalPrice = in.readDouble();
        productGstPrice = in.readDouble();
    }

    public Product() {
    }

    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", productSubCategory='" + productSubCategory + '\'' +
                ", store=" + store +
                ", productCategory='" + productCategory + '\'' +
                ", productUnit='" + productUnit + '\'' +
                ", productId=" + productId +
                ", productSize='" + productSize + '\'' +
                ", productBrand='" + productBrand + '\'' +
                ", productOriginalPrice=" + productOriginalPrice +
                ", productGstPrice=" + productGstPrice +
                ", productQuantity=" + productQuantity +
                '}';
    }
}
