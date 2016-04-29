package com.appman.appmanager.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.appman.appmanager.AppManagerApplication;
import com.appman.appmanager.R;
import com.appman.appmanager.recycler.RecyclerAdapter;
import com.appman.appmanager.utils.AppPreferences;
import com.appman.appmanager.utils.FileUtils;
import com.appman.appmanager.utils.UtilsUI;

import java.io.File;

/**
 * Created by rudhraksh.pahade on 28-04-2016.
 */
public class ActivityFileManager extends AppCompatActivity{

    private AppPreferences appPreferences;

    Toolbar toolbar;
    TextView textViewStorageUsage;

    private File currentDirectory;

    private RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_file_manager);
        this.appPreferences = AppManagerApplication.getAppPreferences();

        initToolBar();
        initViews();
        initData();
    }

    private void initData() {
        textViewStorageUsage.setText(FileUtils.getStorageUsage(this));
    }

    private void initViews() {
        textViewStorageUsage = (TextView) findViewById(R.id.textViewStorageUsage);
    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarFileManager);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this,R.drawable.ic_more));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("File Explorer");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark0));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }

        // Setting navigation bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(UtilsUI.darker(getResources().getColor(R.color.colorPrimaryDark0), 0.8));


            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark0));
            if (!appPreferences.getNavigationBlackPref()) {
                getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimaryDark0));
            }
        }
    }
}
