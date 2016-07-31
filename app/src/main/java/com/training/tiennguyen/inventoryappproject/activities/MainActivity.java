/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by TienNguyen <tien.workinfo@gmail.com - tien.workinfo@icloud.com>, October 2015
 */

package com.training.tiennguyen.inventoryappproject.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.training.tiennguyen.inventoryappproject.R;
import com.training.tiennguyen.inventoryappproject.adapters.ProductAdapter;
import com.training.tiennguyen.inventoryappproject.constants.VariableConstant;
import com.training.tiennguyen.inventoryappproject.database.ProductDBHelper;
import com.training.tiennguyen.inventoryappproject.models.ProductModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * MainActivity
 *
 * @author TienNguyen
 */
public class MainActivity extends AppCompatActivity {
    /**
     * mainActivityLvProducts
     */
    @BindView(R.id.mainActivityLvProducts)
    protected ListView listView;

    /**
     * mainActivityTxtStatus
     */
    @BindView(R.id.mainActivityTxtStatus)
    protected TextView textView;

    /**
     * mainActivityFab
     */
    @BindView(R.id.mainActivityFab)
    protected FloatingActionButton fab;

    /**
     * mainActivityToolbar
     */
    @BindView(R.id.mainActivityToolbar)
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initViews
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViews();
    }

    /**
     * initViews
     */
    private void initViews() {
        ButterKnife.bind(this);

        // Set default view
        setTitle(getString(R.string.activity_main));
        setSupportActionBar(toolbar);

        if (verifyInternetConnection()) {
            // Populate Data
            populateData();
        } else {
            // Message to user to connect the internet or close the app
            requestConnectionDialog();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.main_action_settings) {
            openSetting();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Populate Data
     */
    private void populateData() {
        // Get data
        final ProductDBHelper dbHelper = new ProductDBHelper(this);
        final List<ProductModel> modelList = dbHelper.selectAllHabits();

        // Populate data into view
        final ProductAdapter productAdapter = new ProductAdapter(this, R.layout.list_products_item, modelList);
        listView.setAdapter(productAdapter);
        if (modelList.size() > 0) {
            textView.setText(VariableConstant.STRING_EMPTY);
        } else {
            textView.setText(getString(R.string.text_view_no_data));
        }

        // Open AddActivity
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Request Connection Dialog
     */
    private void requestConnectionDialog() {
        textView.setText(getString(R.string.text_view_no_connection));

        new AlertDialog.Builder(this)
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
}
