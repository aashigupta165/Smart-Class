package com.education.smartclass.roles.teacher.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.education.smartclass.R;
import com.education.smartclass.roles.teacher.fragments.NotificationFragment;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.BadgeDrawable;
import com.education.smartclass.utils.Logout;
import com.google.android.material.navigation.NavigationView;

public class TeacherActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        Menu menu = navigationView.getMenu();

        MenuItem schedule = menu.findItem(R.id.schedule);
        MenuItem questionare = menu.findItem(R.id.questionaire);
        MenuItem addSchedule = menu.findItem(R.id.new_schedule);
        MenuItem addquestion = menu.findItem(R.id.new_question);

        schedule.setVisible(true);
        questionare.setVisible(true);
        addSchedule.setVisible(true);
        addquestion.setVisible(true);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorPrimaryDark));
        toggle.syncState();

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.home, R.id.profile, R.id.new_schedule, R.id.schedule, R.id.new_question, R.id.questionaire)
                .setDrawerLayout(drawerLayout)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
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
        MenuItem menuItem = menu.findItem(R.id.add);
        menuItem.setVisible(false);

        MenuItem notification = menu.findItem(R.id.notification);
        LayerDrawable icon = (LayerDrawable) notification.getIcon();
        setBadgeCount(this, icon, SharedPrefManager.getInstance(getApplicationContext()).getBadgeCount());

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

                MenuItem notification = item;
                LayerDrawable icon = (LayerDrawable) notification.getIcon();
                setBadgeCount(this, icon, SharedPrefManager.getInstance(getApplicationContext()).getBadgeCount());

                NotificationFragment fragment = new NotificationFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).addToBackStack(null).commit();

                break;
            case R.id.refresh:
                finish();
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                break;
            case R.id.logout:
                new Logout(TeacherActivity.this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}