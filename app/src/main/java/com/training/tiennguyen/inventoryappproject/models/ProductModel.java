/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by TienNguyen <tien.workinfo@gmail.com - tien.workinfo@icloud.com>, October 2015
 */

package com.training.tiennguyen.inventoryappproject.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ProductModel
 *
 * @author TienNguyen
 */
public class ProductModel implements Parcelable {
    /**
     * After implementing the `Parcelable` interface, we need to create the
     * `Parcelable.Creator<MyParcelable> CREATOR` constant for our class;
     * Notice how it has our class specified as its type.
     */
    public static final Creator<ProductModel> CREATOR = new Creator<ProductModel>() {
        @Override
        public ProductModel createFromParcel(Parcel in) {
            return new ProductModel(in);
        }

        @Override
        public ProductModel[] newArray(int size) {
            return new ProductModel[size];
        }
    };
    /**
     * mName
     */
    private String mName;
    /**
     * mQuality
     */
    private int mQuality;
    /**
     * mSaleNumber
     */
    private int mSaleNumber;
    /**
     * mPrice
     */
    private double mPrice;
    /**
     * mDescription
     */
    private String mDescription;
    /**
     * mSupplierEmail
     */
    private String mSupplierEmail;
    /**
     * mSupplierPhone
     */
    private String mSupplierPhone;
    /**
     * mImageBitmap
     */
    private String mImageBitmap;
    /**
     * mInfo
     */
    private Parcelable mInfo;

    /**
     * Constructor
     */
    public ProductModel() {
    }

    /**
     * Using the `in` variable, we can retrieve the values that
     * we originally wrote into the `Parcel`.  This constructor is usually
     * private so that only the `CREATOR` field can access.
     *
     * @param in Parcel
     */
    protected ProductModel(Parcel in) {
        mName = in.readString();
        mQuality = in.readInt();
        mSaleNumber = in.readInt();
        mPrice = in.readDouble();
        mDescription = in.readString();
        mSupplierEmail = in.readString();
        mSupplierPhone = in.readString();
        mImageBitmap = in.readString();
        mInfo = in.readParcelable(ProductModel.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeInt(mQuality);
        parcel.writeInt(mSaleNumber);
        parcel.writeDouble(mPrice);
        parcel.writeString(mDescription);
        parcel.writeString(mSupplierEmail);
        parcel.writeString(mSupplierPhone);
        parcel.writeString(mImageBitmap);
        parcel.writeParcelable(mInfo, i);
    }

    /**
     * gets method
     *
     * @return String mName
     */
    public String getmName() {
        return mName;
    }

    /**
     * sets method
     *
     * @param mName String
     */
    public void setmName(String mName) {
        this.mName = mName;
    }

    /**
     * gets method
     *
     * @return int mQuality
     */
    public int getmQuality() {
        return mQuality;
    }

    /**
     * sets method
     *
     * @param mQuality int
     */
    public void setmQuality(int mQuality) {
        this.mQuality = mQuality;
    }

    /**
     * gets method
     *
     * @return int mSaleNumber
     */
    public int getmSaleNumber() {
        return mSaleNumber;
    }

    /**
     * sets method
     *
     * @param mSaleNumber int
     */
    public void setmSaleNumber(int mSaleNumber) {
        this.mSaleNumber = mSaleNumber;
    }

    /**
     * gets method
     *
     * @return double mPrice
     */
    public double getmPrice() {
        return mPrice;
    }

    /**
     * sets method
     *
     * @param mPrice double
     */
    public void setmPrice(double mPrice) {
        this.mPrice = mPrice;
    }

    /**
     * gets method
     *
     * @return String mDescription
     */
    public String getmDescription() {
        return mDescription;
    }

    /**
     * sets method
     *
     * @param mDescription String
     */
    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    /**
     * gets method
     *
     * @return String mSupplierEmail
     */
    public String getmSupplierEmail() {
        return mSupplierEmail;
    }

    /**
     * sets method
     *
     * @param mSupplierEmail String
     */
    public void setmSupplierEmail(String mSupplierEmail) {
        this.mSupplierEmail = mSupplierEmail;
    }

    /**
     * gets method
     *
     * @return String mSupplierPhone
     */
    public String getmSupplierPhone() {
        return mSupplierPhone;
    }

    /**
     * sets method
     *
     * @param mSupplierPhone String
     */
    public void setmSupplierPhone(String mSupplierPhone) {
        this.mSupplierPhone = mSupplierPhone;
    }

    /**
     * gets method
     *
     * @return String mImageBitmap
     */
    public String getmImageBitmap() {
        return mImageBitmap;
    }

    /**
     * sets method
     *
     * @param mImageBitmap String
     */
    public void setmImageBitmap(String mImageBitmap) {
        this.mImageBitmap = mImageBitmap;
    }
}
