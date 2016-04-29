package com.appman.appmanager.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.appman.appmanager.AppManagerApplication;
import com.appman.appmanager.R;
import com.appman.appmanager.adapter.SlidingTabAdapter;
import com.appman.appmanager.slidingtablayout.SlidingTabLayout;
import com.appman.appmanager.utils.AppPreferences;
import com.appman.appmanager.utils.FileUtils;
import com.appman.appmanager.utils.UtilsUI;
import com.appman.appmanager.views.TextView_Regular;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by rudhraksh.pahade on 29-04-2016.
 */
public class ActivityFileManager extends AppCompatActivity{

    private AppPreferences appPreferences;
    private SlidingTabAdapter slidingTabAdapter;

    private Toolbar toolbar;
    private TextView_Regular textViewFileManagerStorage;
    private SlidingTabLayout tabs;
    private ViewPager pager;
    CharSequence titles[] = {"Internal Storage", "External Storage"};;
    int numbOfTabs = 2;
    JSONArray array;
    List<String> listItems ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_file_manager);
        this.appPreferences = AppManagerApplication.getAppPreferences();
        initToolBar();
        initViews();
        initSlidingTabs();
        initRecyclerView();
    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById (R.id.toolbarFileManager);
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(UtilsUI.darker(getResources().getColor(R.color.colorPrimaryDark0), 0.8));
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark0));
            if (!appPreferences.getNavigationBlackPref()) {
                getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimaryDark0));
            }
        }
    }
    private void initViews(){
        textViewFileManagerStorage = (TextView_Regular) findViewById (R.id.textViewFileManagerStorage);
        textViewFileManagerStorage.setText(FileUtils.getStorageUsage(ActivityFileManager.this));
        textViewFileManagerStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_INTERNAL_STORAGE_SETTINGS));
            }
        });
    }
    private void initSlidingTabs(){

        slidingTabAdapter = new SlidingTabAdapter(getSupportFragmentManager(), titles, numbOfTabs);
        pager = (ViewPager) findViewById (R.id.pager);
        pager.setAdapter(slidingTabAdapter);
        tabs = (SlidingTabLayout) findViewById (R.id.slidingTabsLayout);


        tabs.setDistributeEvenly(true);
        tabs.setViewPager(pager);

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorPrimary);
            }

        });

    }
    private void initRecyclerView(){

    }
}
