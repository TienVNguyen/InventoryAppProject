/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by TienNguyen <tien.workinfo@gmail.com - tien.workinfo@icloud.com>, October 2015
 */

package com.training.tiennguyen.inventoryappproject.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.training.tiennguyen.inventoryappproject.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * DetailsActivity
 *
 * @author TienNguyen
 */
public class DetailsActivity extends AppCompatActivity {
    /**
     * detailActivityPbImgProduct
     */
    @BindView(R.id.detailActivityPbImgProduct)
    protected ProgressBar progressBar;

    /**
     * detailActivityImgProduct
     */
    @BindView(R.id.detailActivityImgProduct)
    protected ImageView imageView;

    /**
     * detailActivityEdtName
     */
    @BindView(R.id.detailActivityEdtName)
    protected EditText edtName;

    /**
     * detailActivityEdtQuantity
     */
    @BindView(R.id.detailActivityEdtQuantity)
    protected EditText edtQuantity;

    /**
     * detailActivityEdtPrice
     */
    @BindView(R.id.detailActivityEdtPrice)
    protected EditText edtPrice;

    /**
     * detailActivityEdtSupplierEmail
     */
    @BindView(R.id.detailActivityEdtSupplierEmail)
    protected EditText edtSupplierEmail;

    /**
     * detailActivityEdtSupplierPhone
     */
    @BindView(R.id.detailActivityEdtSupplierPhone)
    protected EditText edtSupplierPhone;

    /**
     * detailActivityEdtDescription
     */
    @BindView(R.id.detailActivityEdtDescription)
    protected EditText edtDescription;

    /**
     * detailActivityBtnQuantity
     */
    @BindView(R.id.detailActivityBtnQuantity)
    protected Button btnQuantity;

    /**
     * detailActivityBtnOrder
     */
    @BindView(R.id.detailActivityBtnOrder)
    protected Button btnOrder;

    /**
     * detailActivityBtnDelete
     */
    @BindView(R.id.detailActivityBtnDelete)
    protected Button btnDelete;

    /**
     * detailActivityBtnCancel
     */
    @BindView(R.id.detailActivityBtnCancel)
    protected Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // initViews
        initViews();
    }

    /**
     * initViews
     */
    private void initViews() {
        ButterKnife.bind(this);

        setTitle(getString(R.string.activity_details));

        final ProductModel productModel = getIntent().getParcelableExtra(VariableConstant.PRODUCT_DETAILS_INTENT);
        if (productModel != null) {
            edtName.setText(productModel.getmName());
            edtQuantity.setText(String.valueOf(productModel.getmQuality()));
            edtPrice.setText(String.valueOf(productModel.getmPrice()));
            edtSupplierEmail.setText(productModel.getmSupplierEmail());
            edtSupplierPhone.setText(productModel.getmSupplierPhone());
            edtDescription.setText(productModel.getmDescription());

            if (!StringUtil.isEmpty(productModel.getmImageBitmap())) {
                imageView.setImageBitmap(StringToBitMap(productModel.getmImageBitmap()));
                progressBar.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.VISIBLE);
            }

            // Activate quantityFunction
            btnQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    quantityFunction(productModel);
                }
            });

            //  Activate orderFunction
            btnOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    orderFunction();
                }
            });

            //  Activate deleteFunction
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteFunction(productModel);
                }
            });

            // Return back to MainActivity
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        } else {
            // Show errors
            Log.e("ERROR_TITLE", "ERROR_VALUE");
        }
    }

    /**
     * Quantity Function
     *
     * @param productModel ProductModel
     */
    private void quantityFunction(final ProductModel productModel) {
        // Get prompts.xml view
        final LayoutInflater layoutInflater = LayoutInflater.from(DetailsActivity.this);
        final View promptView = layoutInflater.inflate(R.layout.quantity_dialog, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
        builder.setView(promptView);

        // Original quantity
        final EditText edtQuantityDialog = (EditText) promptView.findViewById(R.id.quantityDialog_edtQuantity);
        final String quantity = String.valueOf(productModel.getmQuality());
        edtQuantityDialog.setText(quantity);

        // Quantity - 1 (only available if it's over 0)
        final Button btnSub = (Button) promptView.findViewById(R.id.quantityDialog_btnSub);
        if (quantity.equalsIgnoreCase(VariableConstant.STRING_ZERO)) {
            btnSub.setEnabled(false);
        } else {
            btnSub.setEnabled(true);
        }
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String quantity = edtQuantityDialog.getText().toString();
                if (quantity.equalsIgnoreCase(VariableConstant.STRING_ZERO)) {
                    btnSub.setEnabled(false);
                } else if (quantity.equalsIgnoreCase(VariableConstant.STRING_ONE)) {
                    edtQuantityDialog.setText(String.valueOf(Integer.parseInt(quantity) - 1));
                    btnSub.setEnabled(false);
                } else {
                    edtQuantityDialog.setText(String.valueOf(Integer.parseInt(quantity) - 1));
                    btnSub.setEnabled(true);
                }
            }
        });

        // Quantity + 1
        final Button btnAdd = (Button) promptView.findViewById(R.id.quantityDialog_btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String quantity = edtQuantityDialog.getText().toString();
                edtQuantityDialog.setText(String.valueOf(Integer.parseInt(quantity) + 1));
                btnSub.setEnabled(true);
            }
        });

        // Setup a dialog window
        builder.setCancelable(false)
                .setPositiveButton(this.getString(R.string.btn_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Final update
                        final ProductDBHelper productDBHelper = new ProductDBHelper(getApplicationContext());
                        final int oldQuantity = productModel.getmQuality();
                        productModel.setmQuality(Integer.parseInt(edtQuantityDialog.getText().toString()));

                        // Update Product
                        long result = productDBHelper.updateProduct(productModel);
                        if (result > 0) {
                            edtQuantity.setText(String.valueOf(productModel.getmQuality()));
                            Toast.makeText(getApplicationContext(), getString(R.string.update_successfully) + edtName.getText().toString(), Toast.LENGTH_SHORT).show();
                        } else {
                            productModel.setmQuality(oldQuantity);
                            Toast.makeText(getApplicationContext(), getString(R.string.update_error), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(this.getString(R.string.btn_cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Close dialog
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        final AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Order Function
     * <ul>
     * <li>Send email to Supplier's email</li>
     * <li>Make a call to Supplier's phone</li>
     * <li>Message to notify if there are no supported</li>
     * </ul>
     */
    private void orderFunction() {
        if (!StringUtil.isEmpty(edtSupplierEmail.getText().toString())) {
            // Check email's valid
            if (StringUtil.isEmail(edtSupplierEmail.getText().toString().trim())) {
                // Check connection
                if (verifyInternetConnection()) {
                    // Send email
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL, new String[]{edtSupplierEmail.getText().toString().trim()});
                    i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
                    i.putExtra(Intent.EXTRA_TEXT, "body of email");
                    try {
                        startActivity(Intent.createChooser(i, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(this, getString(R.string.dialog_no_email_client), Toast.LENGTH_SHORT).show();
                        edtSupplierEmail.requestFocus();
                    }
                } else {
                    requestConnectionDialog();
                }
            } else {
                Toast.makeText(this, getString(R.string.edt_supplier_email_error_invalid), Toast.LENGTH_SHORT).show();
                edtSupplierEmail.requestFocus();
            }
        } else if (!StringUtil.isEmpty(edtSupplierPhone.getText().toString())) {
            // Check phone's valid
            if (StringUtil.isPhone(edtSupplierPhone.getText().toString())) {
                // Check permission
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    // Make a call
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + edtSupplierPhone.getText().toString()));
                    startActivity(intent);
                } else {
                    Toast.makeText(this, getString(R.string.dialog_no_phone_client), Toast.LENGTH_SHORT).show();
                    edtSupplierPhone.requestFocus();
                }
            } else {
                Toast.makeText(this, getString(R.string.edt_supplier_phone_error_invalid), Toast.LENGTH_SHORT).show();
                edtSupplierPhone.requestFocus();
            }
        } else {
            // No support email or phone
            Toast.makeText(this, getString(R.string.dialog_no_email_phone_client), Toast.LENGTH_SHORT).show();
            edtSupplierEmail.requestFocus();
        }
    }

    /**
     * Delete Function
     *
     * @param productModel ProductModel
     */
    private void deleteFunction(ProductModel productModel) {
        ProductDBHelper productDBHelper = new ProductDBHelper(getApplicationContext());
        int result = productDBHelper.deleteProduct(productModel);
        if (result > 0) {
            Toast.makeText(DetailsActivity.this, getString(R.string.delete_successfully) + productModel.getmName(), Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(DetailsActivity.this, getString(R.string.delete_error), Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Request Connection Dialog
     */
    private void requestConnectionDialog() {
        new android.support.v7.app.AlertDialog.Builder(this)
                .setTitle(R.string.dialog_no_connection_title)
                .setMessage(R.string.dialog_no_connection_message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Connection internet setting
                        openSetting();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Exit app
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * Connection internet setting
     */
    private void openSetting() {
        Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
        startActivityForResult(intent, 0);
    }

    /**
     * Verify Internet Connection
     *
     * @return boolean
     */
    private boolean verifyInternetConnection() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
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
}
