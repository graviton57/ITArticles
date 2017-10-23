package com.havryliuk.itarticles.ui.main;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.havryliuk.itarticles.BuildConfig;
import com.havryliuk.itarticles.R;
import com.havryliuk.itarticles.ui.base.BaseActivity;
import com.havryliuk.itarticles.ui.login.LoginActivity;
import com.havryliuk.itarticles.utils.AppUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Igor Havrylyuk on 20.10.2017.
 */

public class MainActivity extends BaseActivity
        implements MainMvpView, NavigationView.OnNavigationItemSelectedListener {

    @Inject
    MainMvpPresenter<MainMvpView> presenter;

    @Inject
    DouPagerAdapter pagerAdapter;

    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.nav_drawer_view)
    NavigationView navigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.view_pager_layout)
    ViewPager viewPager;
    @BindView(R.id.tabs_layout)
    TabLayout tabLayout;
    @BindView(R.id.tv_app_version)
    TextView appVersion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActivityFragmentComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        setSupportActionBar(toolbar);
        presenter.attachView(this);
        initUI();
    }

    @Override
    public boolean isNetworkConnected() {
        return AppUtils.isNetworkAvailable(this);
    }

    @Override
    protected void initUI() {
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.app_name));
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.app_name, R.string.app_name);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        pagerAdapter = new DouPagerAdapter(getSupportFragmentManager());
        pagerAdapter.setPages(getResources().getStringArray(R.array.articles_tab_titles));
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            public void onTabUnselected(TabLayout.Tab tab) {
            }

            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        presenter.onNavMenuCreated();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_share:
                presenter.onDrawerShareClick();
                break;
            case R.id.nav_logout:
                presenter.onDrawerOptionLogoutClick();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    @Override
    public void showAboutFragment() {
        Toast.makeText(this, getString(R.string.app_name),Toast.LENGTH_SHORT).show();
    }


    @Override
    public void updateAppVersion() {
        String version = getString(R.string.format_app_version, BuildConfig.VERSION_NAME);
        appVersion.setText(version);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void shareApp() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
            } else {
                shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            }
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_name) +
                    "-> https://play.google.com/store/apps/details?id=" + getPackageName());
            startActivity(shareIntent);
        } catch (ActivityNotFoundException ignored){

        }
    }

    @Override
    public void closeNavigationDrawer() {
        if (drawer != null) {
            drawer.closeDrawer(Gravity.START);
        }
    }

    @Override
    public void showLoginActivity() {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }
}
