/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by TienNguyen <tien.workinfo@gmail.com - tien.workinfo@icloud.com>, October 2015
 */

package com.training.tiennguyen.inventoryappproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.training.tiennguyen.inventoryappproject.constants.DatabaseConstant;
import com.training.tiennguyen.inventoryappproject.models.ProductModel;
import com.training.tiennguyen.inventoryappproject.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * ProductDBHelper
 *
 * @author TienNguyen
 */
public final class ProductDBHelper extends SQLiteOpenHelper {
    /**
     * CREATE TABLE PRODUCT ( NAME PRIMARY KEY NOT NULL ,QUANTITY INTEGER KEY NOT NULL ,PRICE DOUBLE KEY NOT NULL ,SUPPLIER_EMAIL KEY , SUPPLIER_PHONE KEY ,DESCRIPTION KEY, IMAGE KEY ) ;
     */
    private static final String SQL_CREATE_ENTRIES = DatabaseConstant.CREATE_TABLE + ProductDBContract.ProductContain.TABLE_NAME
            + DatabaseConstant.OPEN_BRACKETS
            + ProductDBContract.ProductContain.COLUMN_NAME + DatabaseConstant.PRIMARY_KEY + DatabaseConstant.NOT_NULL + DatabaseConstant.COMMA_SEP
            + ProductDBContract.ProductContain.COLUMN_NAME_QUANTITY + DatabaseConstant.INTEGER_KEY + DatabaseConstant.NOT_NULL + DatabaseConstant.COMMA_SEP
            + ProductDBContract.ProductContain.COLUMN_NAME_SALE_NUMBER + DatabaseConstant.INTEGER_KEY + DatabaseConstant.NOT_NULL + DatabaseConstant.COMMA_SEP
            + ProductDBContract.ProductContain.COLUMN_NAME_PRICE + DatabaseConstant.DOUBLE_KEY + DatabaseConstant.NOT_NULL + DatabaseConstant.COMMA_SEP
            + ProductDBContract.ProductContain.COLUMN_NAME_SUPPLIER_EMAIL + DatabaseConstant.STRING_KEY + DatabaseConstant.COMMA_SEP
            + ProductDBContract.ProductContain.COLUMN_NAME_SUPPLIER_PHONE + DatabaseConstant.STRING_KEY + DatabaseConstant.COMMA_SEP
            + ProductDBContract.ProductContain.COLUMN_NAME_DESCRIPTION + DatabaseConstant.STRING_KEY + DatabaseConstant.COMMA_SEP
            + ProductDBContract.ProductContain.COLUMN_NAME_IMAGE_BITMAP + DatabaseConstant.STRING_KEY
            + DatabaseConstant.CLOSE_BRACKETS + DatabaseConstant.SEMICOLON;

    /**
     * DROP TABLE PRODUCT IF EXISTS;
     */
    private static final String SQL_DELETE_ENTRIES =
            DatabaseConstant.DROP_TABLE_EXISTED + ProductDBContract.ProductContain.TABLE_NAME + DatabaseConstant.SEMICOLON;

    /**
     * Constructor
     *
     * @param context Context
     */
    public ProductDBHelper(Context context) {
        super(context, ProductDBContract.ProductContain.DATABASE_NAME_VALUE, null, ProductDBContract.ProductContain.DATABASE_VERSION_VALUE);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        deleteDatabase(sqLiteDatabase);
        onCreate(sqLiteDatabase);
    }

    /**
     * deleteDatabase
     *
     * @param sqLiteDatabase SQLiteDatabase
     */
    private void deleteDatabase(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
    }


    /**
     * This function will select all record(s) of table inside of database
     *
     * @return List<ProductModel>
     */
    public List<ProductModel> selectAllHabits() {
        // Gets the data repository in read mode
        final SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        // Select table columns
        final String[] columns = new String[]{
                ProductDBContract.ProductContain.COLUMN_NAME, ProductDBContract.ProductContain.COLUMN_NAME_QUANTITY,
                ProductDBContract.ProductContain.COLUMN_NAME_SALE_NUMBER, ProductDBContract.ProductContain.COLUMN_NAME_PRICE,
                ProductDBContract.ProductContain.COLUMN_NAME_SUPPLIER_EMAIL, ProductDBContract.ProductContain.COLUMN_NAME_SUPPLIER_PHONE,
                ProductDBContract.ProductContain.COLUMN_NAME_DESCRIPTION, ProductDBContract.ProductContain.COLUMN_NAME_IMAGE_BITMAP
        };

        // Get the result
        final Cursor cursor = sqLiteDatabase.query(ProductDBContract.ProductContain.TABLE_NAME, columns, null, null, null, null, null);
        final List<ProductModel> resultSelect = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                ProductModel habitModel = new ProductModel();
                habitModel.setmName(cursor.getString(0));
                habitModel.setmQuality(cursor.getInt(1));
                habitModel.setmSaleNumber(cursor.getInt(2));
                habitModel.setmPrice(cursor.getDouble(3));
                habitModel.setmSupplierEmail(cursor.getString(4));
                habitModel.setmSupplierPhone(cursor.getString(5));
                habitModel.setmDescription(cursor.getString(6));
                habitModel.setmImageBitmap(cursor.getString(7));
                resultSelect.add(habitModel);
            } while (cursor.moveToNext());
        }

        // Close connection
        if (cursor != null) cursor.close();
        sqLiteDatabase.close();

        return resultSelect;
    }

    /**
     * This function will add a record of table inside of database
     *
     * @param productModel ProductModel
     * @return long
     */
    public long addProduct(final ProductModel productModel) {
        // Gets the data repository in write mode
        final SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        final ContentValues contentValues = new ContentValues();
        contentValues.put(ProductDBContract.ProductContain.COLUMN_NAME, productModel.getmName());
        contentValues.put(ProductDBContract.ProductContain.COLUMN_NAME_QUANTITY, productModel.getmQuality());
        contentValues.put(ProductDBContract.ProductContain.COLUMN_NAME_SALE_NUMBER, 0);
        contentValues.put(ProductDBContract.ProductContain.COLUMN_NAME_PRICE, productModel.getmPrice());
        if (!StringUtil.isEmpty(productModel.getmSupplierEmail())) {
            contentValues.put(ProductDBContract.ProductContain.COLUMN_NAME_SUPPLIER_EMAIL, productModel.getmSupplierEmail());
        }
        if (!StringUtil.isEmpty(productModel.getmSupplierPhone())) {
            contentValues.put(ProductDBContract.ProductContain.COLUMN_NAME_SUPPLIER_PHONE, productModel.getmSupplierPhone());
        }
        if (!StringUtil.isEmpty(productModel.getmDescription())) {
            contentValues.put(ProductDBContract.ProductContain.COLUMN_NAME_DESCRIPTION, productModel.getmDescription());
        }
        if (!StringUtil.isEmpty(productModel.getmImageBitmap())) {
            contentValues.put(ProductDBContract.ProductContain.COLUMN_NAME_IMAGE_BITMAP, productModel.getmImageBitmap());
        }

        // Insert
        final long newRowId = sqLiteDatabase.insert(
                ProductDBContract.ProductContain.TABLE_NAME,
                ProductDBContract.ProductContain.COLUMN_NAME_NULLABLE,
                contentValues);

        // Close connection
        sqLiteDatabase.close();

        return newRowId;
    }

    /**
     * This function will update a record of table inside of database
     *
     * @param productModel ProductModel
     * @return int
     */
    public int updateProduct(final ProductModel productModel) {
        // Get the lock
        final SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        // New value for one column
        final ContentValues contentValues = new ContentValues();
        contentValues.put(ProductDBContract.ProductContain.COLUMN_NAME, productModel.getmName());
        contentValues.put(ProductDBContract.ProductContain.COLUMN_NAME_QUANTITY, productModel.getmQuality());
        contentValues.put(ProductDBContract.ProductContain.COLUMN_NAME_SALE_NUMBER, productModel.getmSaleNumber());
        contentValues.put(ProductDBContract.ProductContain.COLUMN_NAME_PRICE, productModel.getmPrice());
        if (!StringUtil.isEmpty(productModel.getmSupplierEmail())) {
            contentValues.put(ProductDBContract.ProductContain.COLUMN_NAME_SUPPLIER_EMAIL, productModel.getmSupplierEmail());
        }
        if (!StringUtil.isEmpty(productModel.getmSupplierPhone())) {
            contentValues.put(ProductDBContract.ProductContain.COLUMN_NAME_SUPPLIER_PHONE, productModel.getmSupplierPhone());
        }
        if (!StringUtil.isEmpty(productModel.getmDescription())) {
            contentValues.put(ProductDBContract.ProductContain.COLUMN_NAME_DESCRIPTION, productModel.getmDescription());
        }
        if (!StringUtil.isEmpty(productModel.getmImageBitmap())) {
            contentValues.put(ProductDBContract.ProductContain.COLUMN_NAME_IMAGE_BITMAP, productModel.getmImageBitmap());
        }

        // Which row to update, based on the ID
        final String selection = ProductDBContract.ProductContain.COLUMN_NAME + DatabaseConstant.SELECTION_IS;

        // Specify arguments in placeholder order
        final String[] selectionArgs = {String.valueOf(productModel.getmName())};

        // Update
        final int count = sqLiteDatabase.update(
                ProductDBContract.ProductContain.TABLE_NAME,
                contentValues,
                selection,
                selectionArgs);

        // Close connection
        sqLiteDatabase.close();

        return count;
    }

    /**
     * This function will delete a record of table inside of database
     *
     * @param productModel ProductModel
     * @return int
     */
    public int deleteProduct(final ProductModel productModel) {
        // Get the lock
        final SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        // Which row to update, based on the ID
        final String selection = ProductDBContract.ProductContain.COLUMN_NAME + DatabaseConstant.SELECTION_IS;

        // Specify arguments in placeholder order
        final String[] selectionArgs = {String.valueOf(productModel.getmName())};

        // Delete
        int count = sqLiteDatabase.delete(
                ProductDBContract.ProductContain.TABLE_NAME,
                selection,
                selectionArgs);

        // Close connection
        sqLiteDatabase.close();

        return count;
    }
}
