/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by TienNguyen <tien.workinfo@gmail.com - tien.workinfo@icloud.com>, October 2015
 */

package com.training.tiennguyen.inventoryappproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.training.tiennguyen.inventoryappproject.R;
import com.training.tiennguyen.inventoryappproject.activities.DetailsActivity;
import com.training.tiennguyen.inventoryappproject.constants.VariableConstant;
import com.training.tiennguyen.inventoryappproject.database.ProductDBHelper;
import com.training.tiennguyen.inventoryappproject.models.ProductModel;
import com.training.tiennguyen.inventoryappproject.utils.StringUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ProductAdapter
 *
 * @author TienNguyen
 */
public class ProductAdapter extends ArrayAdapter<ProductModel> {
    /**
     * mResource
     */
    private int mResource;

    /**
     * Constructor
     *
     * @param context  Context
     * @param resource int
     * @param objects  List<ProductModel>
     */
    public ProductAdapter(Context context, int resource, List<ProductModel> objects) {
        super(context, 0, objects);
        this.mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Caching
        final ProductHolder holder;
        if (convertView != null) {
            holder = (ProductHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(mResource, parent, false);
            holder = new ProductHolder(convertView);
            convertView.setTag(holder);
        }

        // Populating
        final ProductModel productModel = getItem(position);
        if (productModel != null) {
            holder.mTxtName.setText(productModel.getmName());
            holder.mTxtQuantity.setText(String.valueOf(productModel.getmQuality()));
            holder.mTxtPrice.setText(String.valueOf(productModel.getmPrice()));
            holder.mTxtSaleNumber.setText(String.valueOf(productModel.getmSaleNumber()));

            if (!StringUtil.isEmpty(productModel.getmImageBitmap())) {
                holder.mImgProduct.setImageBitmap(StringToBitMap(productModel.getmImageBitmap()));
            }

            // If quality meets 0, no more sale action
            if (holder.mTxtQuantity.getText().toString().equalsIgnoreCase(VariableConstant.STRING_ZERO)) {
                holder.mBtnSale.setEnabled(false);
            } else {
                holder.mBtnSale.setEnabled(true);
                holder.mBtnSale.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        saleFunction(holder);
                    }
                });
            }
        }

        // Activate DetailActivity
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(getContext(), DetailsActivity.class);
                intent.putExtra(VariableConstant.PRODUCT_DETAILS_INTENT, productModel);
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }

    /**
     * Sale Function
     *
     * @param holder ProductHolder
     */
    private void saleFunction(ProductHolder holder) {
        // Validate
        if (holder.mTxtQuantity.getText().toString().equalsIgnoreCase(VariableConstant.STRING_ZERO)
                || holder.mTxtQuantity.getText().toString().trim().equalsIgnoreCase(VariableConstant.STRING_EMPTY)) {
            holder.mBtnSale.setEnabled(false);
            Toast.makeText(getContext(), getContext().getString(R.string.dialog_edt_quality_zero), Toast.LENGTH_SHORT).show();
            return;
        }

        // Detail
        final ProductModel productModel = new ProductModel();
        productModel.setmName(holder.mTxtName.getText().toString());
        productModel.setmQuality(Integer.parseInt(holder.mTxtQuantity.getText().toString()) - 1);
        productModel.setmPrice(Double.parseDouble(holder.mTxtPrice.getText().toString()));
        productModel.setmSaleNumber(Integer.parseInt(holder.mTxtSaleNumber.getText().toString()) + 1);

        // Update
        final ProductDBHelper productDBHelper = new ProductDBHelper(getContext());
        int result = productDBHelper.updateProduct(productModel);

        // Result
        if (result > 0) {
            holder.mTxtQuantity.setText(String.valueOf(Integer.parseInt(holder.mTxtQuantity.getText().toString()) - 1));
            holder.mTxtSaleNumber.setText(String.valueOf(Integer.parseInt(holder.mTxtSaleNumber.getText().toString()) + 1));
            Toast.makeText(getContext(), getContext().getString(R.string.update_successfully) + productModel.getmName(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), getContext().getString(R.string.update_error), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * String To BitMap
     *
     * @param encodedString String
     * @return Bitmap
     */
    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    /**
     * ProductHolder
     */
    protected static class ProductHolder {
        /**
         * lvProductsItemImgProduct
         */
        @BindView(R.id.lvProductsItemImgProduct)
        protected ImageView mImgProduct;

        /**
         * lvProductsItemTxtName
         */
        @BindView(R.id.lvProductsItemTxtName)
        protected TextView mTxtName;

        /**
         * lvProductsItemTxtQuantity
         */
        @BindView(R.id.lvProductsItemTxtQuantity)
        protected TextView mTxtQuantity;

        /**
         * lvProductsItemTxtPrice
         */
        @BindView(R.id.lvProductsItemTxtPrice)
        protected TextView mTxtPrice;

        /**
         * lvProductsItemTxtSaleNumber
         */
        @BindView(R.id.lvProductsItemTxtSaleNumber)
        protected TextView mTxtSaleNumber;

        /**
         * lvProductsItemBtnSale
         */
        @BindView(R.id.lvProductsItemBtnSale)
        protected Button mBtnSale;

        /**
         * Constructor
         *
         * @param view View
         */
        protected ProductHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
