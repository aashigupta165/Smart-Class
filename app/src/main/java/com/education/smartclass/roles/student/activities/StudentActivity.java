package com.education.smartclass.roles.student.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.education.smartclass.R;
import com.education.smartclass.roles.Organisation.activities.OrganisationActivity;
import com.education.smartclass.roles.Organisation.model.VersionCheckingViewModel;
import com.education.smartclass.roles.teacher.fragments.NotificationFragment;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.BadgeDrawable;
import com.education.smartclass.utils.Logout;
import com.education.smartclass.utils.SessionExpire;
import com.google.android.material.navigation.NavigationView;

public class StudentActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private VersionCheckingViewModel versionCheckingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        DataObserver();
        BuildVersion();

        Menu menu = navigationView.getMenu();

        MenuItem questionare = menu.findItem(R.id.questionaire);
        MenuItem addquestion = menu.findItem(R.id.new_question);
        MenuItem assignment = menu.findItem(R.id.assignment);

        questionare.setVisible(true);
        addquestion.setVisible(true);
        assignment.setVisible(true);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorPrimaryDark));
        toggle.syncState();

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.home, R.id.profile, R.id.schedule, R.id.new_question, R.id.questionaire, R.id.assignment)
                .setDrawerLayout(drawerLayout)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void BuildVersion() {
        try {
            PackageInfo info = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = info.versionName;
            versionCheckingViewModel.version(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateChecking() {
        final String appPackageName = "com.education.smartclass";
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        BuildVersion();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_toolbar_menu, menu);

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MenuItem notification = menu.findItem(R.id.notification);
                                LayerDrawable icon = (LayerDrawable) notification.getIcon();
                                setBadgeCount(StudentActivity.this, icon, SharedPrefManager.getInstance(getApplicationContext()).getBadgeCount());
                            }
                        });
                    }
                } catch (InterruptedException e) {

                }
            }
        };
        thread.start();

        return super.onCreateOptionsMenu(menu);
    }

    public static void setBadgeCount(Context context, LayerDrawable icon, Boolean count) {

        BadgeDrawable badge;

        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notification:
                SharedPrefManager.getInstance(getApplicationContext()).setBadgeCount(false);
                NotificationFragment fragment = new NotificationFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).addToBackStack(null).commit();
                break;
            case R.id.refresh:
                finish();
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                break;
            case R.id.logout:
                new Logout(StudentActivity.this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void DataObserver() {

        versionCheckingViewModel = ViewModelProviders.of(this).get(VersionCheckingViewModel.class);
        LiveData<String> message = versionCheckingViewModel.getMessage();

        message.observe(StudentActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                switch (s) {
                    case "not_matched":
                        updateChecking();
                        break;
                    case "Session Expire":
                        new SessionExpire(getApplicationContext());
                        break;
                }
            }
        });
    }
}