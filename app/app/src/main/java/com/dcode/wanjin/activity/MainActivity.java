package com.dcode.wanjin.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dcode.corelibrary.util.AppUtils;
import com.dcode.wanjin.R;
import com.dcode.wanjin.WanjinApplication;
import com.dcode.wanjin.activity.bet.BetChoseFragment;
import com.dcode.wanjin.activity.bet.BetDetailFragment;
import com.dcode.wanjin.activity.bet.BetQuickFragment;
import com.dcode.wanjin.activity.bet.PeriodCountFragment;
import com.dcode.wanjin.activity.bet.PeriodStopFragment;
import com.dcode.wanjin.activity.period.PeriodActivity;
import com.dcode.wanjin.activity.period.PeriodOddsActivity;
import com.dcode.wanjin.activity.period.PeriodStopActivity;
import com.dcode.wanjin.activity.report.UserBillActivity;
import com.dcode.wanjin.activity.user.MyUserActivity;
import com.dcode.wanjin.activity.user.UserOddsKfActivity;
import com.dcode.wanjin.event.EventBetQuick;
import com.dcode.wanjin.network.HttpApi;
import com.dcode.wanjin.permission.MPermission;
import com.dcode.wanjin.permission.annotation.OnMPermissionDenied;
import com.dcode.wanjin.permission.annotation.OnMPermissionGranted;
import com.dcode.wanjin.permission.annotation.OnMPermissionNeverAskAgain;
import com.dcode.wanjin.util.QXConstant;
import com.dcode.wanjin.util.UpdateAppHttpUtil;
import com.vector.update_app.UpdateAppManager;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

public class MainActivity extends WanJinBaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int BASIC_PERMISSION_REQUEST_CODE = 1002;

    @BindView(R.id.container)
    ViewPager mViewPager;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private List<Fragment> mFragments;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        requestBasicPermission();
//        setTitle("万金七星");
        WanjinApplication app = (WanjinApplication)getApplication();
        if(app.getCurrPeriod() != null) {
            setTitle( app.getCurrUser().getUserTitle() + ":"+ app.getCurrUser().getName() + "  "+app.getCurrPeriod().getpId() + "期(" + app.getCurrPeriod().getStrStatus() + ")");
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        TextView tv = navigationView.getHeaderView(0).findViewById(R.id.tv_version);
        tv.setText("版本号：v " + AppUtils.getVersionName(this));

        updateApp();

        if (savedInstanceState != null) {
            //清除saveInastanceState中的fragment， 避免getactivity空指针异常。
            String FRAGMENTS_TAG = "Android:support:fragments";
            savedInstanceState.remove(FRAGMENTS_TAG);
            if (mFragments == null || mFragments.size() == 0) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                mFragments = new ArrayList<>();

                BetDetailFragment betDetailFragment = (BetDetailFragment) fragmentManager.findFragmentByTag(BetDetailFragment.class.getSimpleName());
                if (betDetailFragment == null) {
                    betDetailFragment = new BetDetailFragment();
                }
                BetQuickFragment betQuickFragment = (BetQuickFragment) fragmentManager.findFragmentByTag(BetQuickFragment.class.getSimpleName());
                if (betQuickFragment == null) {
                    betQuickFragment = new BetQuickFragment();
                }
                BetChoseFragment betChoseFragment = (BetChoseFragment) fragmentManager.findFragmentByTag(BetChoseFragment.class.getSimpleName());
                if (betChoseFragment == null) {
                    betChoseFragment = new BetChoseFragment();
                }

                PeriodStopFragment periodStopFragment = (PeriodStopFragment) fragmentManager.findFragmentByTag(PeriodStopFragment.class.getSimpleName());
                if (periodStopFragment == null) {
                    periodStopFragment = new PeriodStopFragment();
                }

                PeriodCountFragment periodCountFragment = (PeriodCountFragment) fragmentManager.findFragmentByTag(PeriodCountFragment.class.getSimpleName());
                if (periodCountFragment == null) {
                    periodCountFragment = new PeriodCountFragment();
                }

                mFragments.add(betDetailFragment);
                mFragments.add(betQuickFragment);
                mFragments.add(betChoseFragment);
                mFragments.add(periodStopFragment);
                mFragments.add(periodCountFragment);
            }
        } else {
            mFragments = new ArrayList<>();
            mFragments.add(new BetDetailFragment());
            mFragments.add(new BetQuickFragment());
            mFragments.add(new BetChoseFragment());
            mFragments.add(new PeriodStopFragment());
            mFragments.add(new PeriodCountFragment());
        }

        mViewPager.setOffscreenPageLimit(mFragments.size());
        mTabLayout.setupWithViewPager(mViewPager);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);

        setUserDisplay(navigationView);
    }

    @Override
    protected int layoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected String topTitle() {
        return null;
    }

    @Override
    protected boolean isRegisterEventbus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceive(EventBetQuick event) {
        if(event.getKeyEvent().equals(EventBetQuick.KEY_ADD)) {
            mViewPager.setCurrentItem(1);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my_users) {                    //下级管理
            MyUserActivity.startActivity(MainActivity.this);
        } else if (id == R.id.nav_my_odds) {              //赔率设置    //最低级用户，会员可在此编辑顾客的赔率数据。
            WanjinApplication app = (WanjinApplication) getApplication();
            if (app.getCurrUser().getLevel() == QXConstant.LEVEL_HUIYUAN) {
                UserOddsKfActivity.startActivity(MainActivity.this, app.getCurrUser());
            }
        } else if (id == R.id.nav_period) {               //开奖号码
            PeriodActivity.startActivity(MainActivity.this);
        } else if (id == R.id.nav_period_odds) {          //赔率调整
            PeriodOddsActivity.startActivity(MainActivity.this);
        } else if (id == R.id.nav_period_stop) {          //停押设置
            PeriodStopActivity.startActivity(MainActivity.this);
        } else if (id == R.id.nav_report) {          //报表
            UserBillActivity.startActivity(MainActivity.this);
        } else if (id == R.id.nav_exit) {
            MainActivity.this.finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void updateApp() {
//        long lastUpdateTime = SharePrefUtil.getLong("UPDATE_TIME");
//        if(System.currentTimeMillis() < lastUpdateTime + 24*3600*1000) {
//            return;
//        }
//        SharePrefUtil.putLong("UPDATE_TIME", System.currentTimeMillis());
//        SharePrefUtil.commit();

        new UpdateAppManager
                .Builder()
                //当前Activity
                .setActivity(this)
                .setThemeColor(getResources().getColor(R.color.colorPrimary))
                .setTopPic(R.mipmap.top_8)
                //更新地址
                .setUpdateUrl(HttpApi.getUrlWithHost(HttpApi.getVersion))
                //实现httpManager接口的对象
                .setHttpManager(new UpdateAppHttpUtil())
                .build()
                .update();

    }

    private void setUserDisplay(NavigationView navigationView) {
        int userLevel = 99;
        try {
            WanjinApplication app = (WanjinApplication) getApplication();
            userLevel = app.getCurrUser().getLevel();
        }catch (Exception e) {
        }

        navigationView.getMenu().findItem(R.id.nav_my_users).setVisible(userLevel < QXConstant.LEVEL_HUIYUAN); //下级管理, 代理级别以上才能设置
        navigationView.getMenu().findItem(R.id.nav_my_odds).setVisible(userLevel == QXConstant.LEVEL_HUIYUAN); //赔率设置
        navigationView.getMenu().findItem(R.id.nav_period_stop).setVisible(userLevel < QXConstant.LEVEL_ZHONGDAI); //停押
    }



    /**
     * 基本权限管理
     */
    private final String[] BASIC_PERMISSIONS = new String[]{
            Manifest.permission.INTERNET
//            Manifest.permission.BLUETOOTH_ADMIN,
//            Manifest.permission.BLUETOOTH,
//            Manifest.permission.CALL_PHONE,
//            Manifest.permission.ACCESS_FINE_LOCATION,
//            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private void requestBasicPermission() {
        MPermission.printMPermissionResult(true, this, BASIC_PERMISSIONS);
        MPermission.with(this)
                .setRequestCode(BASIC_PERMISSION_REQUEST_CODE)
                .permissions(BASIC_PERMISSIONS)
                .request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @OnMPermissionGranted(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionSuccess() {
        showToast("授权成功");
        MPermission.printMPermissionResult(false, this, BASIC_PERMISSIONS);
    }

    @OnMPermissionDenied(BASIC_PERMISSION_REQUEST_CODE)
    @OnMPermissionNeverAskAgain(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionFailed() {
//        Toast.makeText(this, "未全部授权，部分功能可能无法正常运行！", Toast.LENGTH_SHORT).show();
        MPermission.printMPermissionResult(false, this, BASIC_PERMISSIONS);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return mFragments.get(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return ((WanjinBaseFragment)mFragments.get(position)).getTitle();
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
        }
    }

}
