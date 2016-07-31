/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by TienNguyen <tien.workinfo@gmail.com - tien.workinfo@icloud.com>, October 2015
 */

package com.training.tiennguyen.inventoryappproject.database;

import android.provider.BaseColumns;

/**
 * ProductDBContract
 *
 * @author TienNguyen
 */
public class ProductDBContract {
    /**
     * DATABASE_VERSION
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * DATABASE_NAME
     */
    private static final String DATABASE_NAME = "PRODUCT.db";

    /**
     * TABLE_PRODUCT
     */
    private static final String TABLE_PRODUCT = "PRODUCT";

    /**
     * KEY_NAME
     */
    private static final String KEY_NAME = "NAME";

    /**
     * KEY_QUANTITY
     */
    private static final String KEY_QUANTITY = "QUANTITY";

    /**
     * KEY_SELL_NUMBER
     */
    private static final String KEY_SELL_NUMBER = "SALE_NUMBER";

    /**
     * KEY_PRICE
     */
    private static final String KEY_PRICE = "PRICE";

    /**
     * KEY_SUPPLIER_EMAIL
     */
    private static final String KEY_SUPPLIER_EMAIL = "SUPPLIER_EMAIL";

    /**
     * KEY_SUPPLIER_PHONE
     */
    private static final String KEY_SUPPLIER_PHONE = "SUPPLIER_PHONE";

    /**
     * KEY_DESCRIPTION
     */
    private static final String KEY_DESCRIPTION = "DESCRIPTION";

    /**
     * KEY_IMAGE_BITMAP
     */
    private static final String KEY_IMAGE_BITMAP = "IMAGE_BITMAP";

    /**
     * NULL
     */
    private static final String NULL = "NULL";

    /**
     * To prevent someone from accidentally instantiating the contract class, give it an empty constructor.
     */
    public ProductDBContract() {
    }

    /**
     * Inner class that defines the table contents
     */
    protected static abstract class ProductContain implements BaseColumns {
        protected static final int DATABASE_VERSION_VALUE = DATABASE_VERSION;
        protected static final String DATABASE_NAME_VALUE = DATABASE_NAME;
        protected static final String TABLE_NAME = TABLE_PRODUCT;
        protected static final String COLUMN_NAME = KEY_NAME;
        protected static final String COLUMN_NAME_QUANTITY = KEY_QUANTITY;
        protected static final String COLUMN_NAME_SALE_NUMBER = KEY_SELL_NUMBER;
        protected static final String COLUMN_NAME_PRICE = KEY_PRICE;
        protected static final String COLUMN_NAME_SUPPLIER_EMAIL = KEY_SUPPLIER_EMAIL;
        protected static final String COLUMN_NAME_SUPPLIER_PHONE = KEY_SUPPLIER_PHONE;
        protected static final String COLUMN_NAME_DESCRIPTION = KEY_DESCRIPTION;
        protected static final String COLUMN_NAME_IMAGE_BITMAP = KEY_IMAGE_BITMAP;
        protected static final String COLUMN_NAME_NULLABLE = NULL;
    }
}
