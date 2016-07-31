/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by TienNguyen <tien.workinfo@gmail.com - tien.workinfo@icloud.com>, October 2015
 */

package com.training.tiennguyen.inventoryappproject.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.training.tiennguyen.inventoryappproject.R;
import com.training.tiennguyen.inventoryappproject.constants.VariableConstant;
import com.training.tiennguyen.inventoryappproject.database.ProductDBHelper;
import com.training.tiennguyen.inventoryappproject.models.ProductModel;
import com.training.tiennguyen.inventoryappproject.utils.ImageUtil;
import com.training.tiennguyen.inventoryappproject.utils.StringUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * AddActivity
 *
 * @author TienNguyen
 */
public class AddActivity extends AppCompatActivity {
    /**
     * addActivityPbImgProduct
     */
    @BindView(R.id.addActivityPbImgProduct)
    protected ProgressBar progressBar;

    /**
     * addActivityImgProduct
     */
    @BindView(R.id.addActivityImgProduct)
    protected ImageView imageView;

    /**
     * addActivityEdtName
     */
    @BindView(R.id.addActivityEdtName)
    protected EditText edtName;

    /**
     * addActivityEdtQuantity
     */
    @BindView(R.id.addActivityEdtQuantity)
    protected EditText edtQuantity;

    /**
     * addActivityEdtPrice
     */
    @BindView(R.id.addActivityEdtPrice)
    protected EditText edtPrice;

    /**
     * addActivityEdtSupplierEmail
     */
    @BindView(R.id.addActivityEdtSupplierEmail)
    protected EditText edtSupplierEmail;

    /**
     * addActivityEdtSupplierPhone
     */
    @BindView(R.id.addActivityEdtSupplierPhone)
    protected EditText edtSupplierPhone;

    /**
     * addActivityEdtDescription
     */
    @BindView(R.id.addActivityEdtDescription)
    protected EditText edtDescription;

    /**
     * addActivityBrowse
     */
    @BindView(R.id.addActivityBrowse)
    protected Button btnBrowse;

    /**
     * addActivityBtnAdd
     */
    @BindView(R.id.addActivityBtnAdd)
    protected Button btnAdd;

    /**
     * addActivityBtnCancel
     */
    @BindView(R.id.addActivityBtnCancel)
    protected Button btnCancel;

    /**
     * userChosenTask
     */
    private String userChosenTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // initViews
        initViews();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case ImageUtil.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChosenTask.equalsIgnoreCase(getString(R.string.dialog_take_photo)))
                        cameraIntent();
                    else if (userChosenTask.equalsIgnoreCase(getString(R.string.dialog_choose_from_library)))
                        galleryIntent();
                } else {
                    //code for deny
                    Log.e("DENY_TITLE", "DENY_VALUE");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == ImageUtil.SELECT_FILE) {
                onSelectFromGalleryResult(data);
            } else if (requestCode == ImageUtil.REQUEST_CAMERA) {
                onCaptureImageResult(data);
            }
        }
    }

    /**
     * initViews
     */
    private void initViews() {
        ButterKnife.bind(this);

        setTitle(getString(R.string.activity_add));

        // Activate browseFunction
        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                browseFunction();
            }
        });

        // Activate addFunction
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput(view)) {
                    addFunction();
                }
            }
        });

        // Return back to MainActivity
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * Browse Function
     */
    private void browseFunction() {
        final CharSequence[] items = {getString(R.string.dialog_take_photo), getString(R.string.dialog_choose_from_library), getString(R.string.btn_cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
        builder.setTitle(getString(R.string.dialog_add_photo));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = ImageUtil.checkPermission(AddActivity.this);
                if (items[item].equals(getString(R.string.dialog_take_photo))) {
                    userChosenTask = getString(R.string.dialog_take_photo);
                    if (result)
                        cameraIntent();
                } else if (items[item].equals(getString(R.string.dialog_choose_from_library))) {
                    userChosenTask = getString(R.string.dialog_choose_from_library);
                    if (result)
                        galleryIntent();
                } else if (items[item].equals(getString(R.string.btn_cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    /**
     * onCaptureImageResult
     *
     * @param data Intent
     */
    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            assert thumbnail != null;
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
            FileOutputStream fo;

            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        progressBar.setVisibility(View.GONE);
        imageView.setImageBitmap(thumbnail);
    }

    /**
     * onSelectFromGalleryResult
     *
     * @param data Intent
     */
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        progressBar.setVisibility(View.GONE);
        imageView.setImageBitmap(bm);
    }

    /**
     * Gallery Intent
     */
    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.dialog_select_file)), ImageUtil.SELECT_FILE);
    }

    /**
     * Camera Intent
     */
    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, ImageUtil.REQUEST_CAMERA);
    }

    /**
     * Validate Input
     *
     * @param view View
     * @return boolean
     */
    private boolean validateInput(View view) {
        // Check empty
        if (StringUtil.isEmpty(edtName.getText().toString())) {
            edtName.requestFocus();
            Snackbar.make(view, this.getString(R.string.edt_name_error_empty), Snackbar.LENGTH_LONG).setAction(VariableConstant.ACTION, null).show();
            return false;
        }

        // Check empty & 0
        if (StringUtil.isEmpty(edtQuantity.getText().toString())) {
            edtQuantity.requestFocus();
            Snackbar.make(view, this.getString(R.string.edt_quantity_error_empty), Snackbar.LENGTH_LONG).setAction(VariableConstant.ACTION, null).show();
            return false;
        } else if (edtQuantity.getText().toString().equalsIgnoreCase(VariableConstant.STRING_ZERO)) {
            edtQuantity.requestFocus();
            Snackbar.make(view, this.getString(R.string.edt_quantity_error_zero), Snackbar.LENGTH_LONG).setAction(VariableConstant.ACTION, null).show();
            return false;
        }

        // Check empty & 0
        if (StringUtil.isEmpty(edtPrice.getText().toString())) {
            edtPrice.requestFocus();
            Snackbar.make(view, this.getString(R.string.edt_price_error_empty), Snackbar.LENGTH_LONG).setAction(VariableConstant.ACTION, null).show();
            return false;
        } else if (edtPrice.getText().toString().equalsIgnoreCase(VariableConstant.STRING_ZERO)) {
            edtPrice.requestFocus();
            Snackbar.make(view, this.getString(R.string.edt_price_error_zero), Snackbar.LENGTH_LONG).setAction(VariableConstant.ACTION, null).show();
            return false;
        }

        // Check invalid if not empty
        if (!StringUtil.isEmpty(edtSupplierEmail.getText().toString())) {
            if (!StringUtil.isEmail(edtSupplierEmail.getText().toString().trim())) {
                edtSupplierEmail.requestFocus();
                Snackbar.make(view, this.getString(R.string.edt_supplier_email_error_invalid), Snackbar.LENGTH_LONG).setAction(VariableConstant.ACTION, null).show();
                return false;
            }
        }

        // Check invalid if not empty
        if (!StringUtil.isEmpty(edtSupplierPhone.getText().toString())) {
            if (!StringUtil.isPhone(edtSupplierPhone.getText().toString().trim())) {
                edtSupplierPhone.requestFocus();
                Snackbar.make(view, this.getString(R.string.edt_supplier_phone_error_invalid), Snackbar.LENGTH_LONG).setAction(VariableConstant.ACTION, null).show();
                return false;
            }
        }

        return true;
    }

    /**
     * Add Function
     */
    private void addFunction() {
        // Object add
        final ProductModel productModel = new ProductModel();
        productModel.setmName(edtName.getText().toString().trim());
        productModel.setmQuality(Integer.parseInt(edtQuantity.getText().toString()));
        productModel.setmPrice(Double.parseDouble(edtPrice.getText().toString()));
        productModel.setmSupplierEmail(edtSupplierEmail.getText().toString().trim());
        productModel.setmSupplierPhone(edtSupplierPhone.getText().toString());
        productModel.setmDescription(edtDescription.getText().toString().trim());
        if (imageView.getDrawable() != null) {
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            productModel.setmImageBitmap(BitMapToString(bitmap));
        }

        // Add Product
        final ProductDBHelper productDBHelper = new ProductDBHelper(this);
        long result = productDBHelper.addProduct(productModel);
        if (result > 0) {
            Toast.makeText(AddActivity.this, getString(R.string.insert_successfully) + edtName.getText().toString(), Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(AddActivity.this, getString(R.string.insert_error), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * BitMap To String
     *
     * @param bitmap Bitmap
     * @return String
     */
    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] bit = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bit, Base64.DEFAULT);
    }
}
