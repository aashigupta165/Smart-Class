package com.education.smartclass.roles.admin.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.smartclass.Adapter.OrganisationListAdapter;
import com.education.smartclass.R;
import com.education.smartclass.roles.Organisation.activities.OrganisationActivity;
import com.education.smartclass.roles.Organisation.model.VersionCheckingViewModel;
import com.education.smartclass.roles.admin.model.HomeViewModel;
import com.education.smartclass.models.Organisation;
import com.education.smartclass.utils.Logout;
import com.education.smartclass.utils.SessionExpire;
import com.education.smartclass.utils.SnackBar;

import java.util.ArrayList;

public class AdminHomeActivity extends AppCompatActivity {

    private RelativeLayout relativeLayout;
    private RecyclerView organisation_list;

    private TextView no_data;

    private HomeViewModel homeViewModel;
    private VersionCheckingViewModel versionCheckingViewModel;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        relativeLayout = findViewById(R.id.relativeLayout);
        organisation_list = findViewById(R.id.organisation_list);

        no_data = findViewById(R.id.no_data);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        dataObserver();

        homeViewModel.fetchOrganisationList();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_toolbar_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.add);
        menuItem.setVisible(true);
        menuItem = menu.findItem(R.id.notification);
        menuItem.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent(AdminHomeActivity.this, RegisterNewOrgActivity1.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.refresh:
                finish();
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                break;
            case R.id.logout:
                new Logout(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void dataObserver() {

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        LiveData<String> message = homeViewModel.getMessage();

        message.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressBar.setVisibility(View.GONE);
                switch (s) {
                    case "list_found":
                        fetchList();
                        break;
                    case "Invalid_orgCode":
                        new SnackBar(relativeLayout, "Invalid Details");
                        break;
                    case "Invalid_role":
                        new SnackBar(relativeLayout, "Invalid Account");
                        break;
                    case "Internet_Issue":
                        new SnackBar(relativeLayout, "Please connect to the Internet!");
                        break;
                    case "Session Expire":
                        new SnackBar(relativeLayout, "Session Expire, Please Login Again!");
                        new SessionExpire(AdminHomeActivity.this);
                        break;
                    default:
                        new SnackBar(relativeLayout, "Invalid Credentials");
                }
            }
        });

        versionCheckingViewModel = ViewModelProviders.of(this).get(VersionCheckingViewModel.class);
        LiveData<String> msg = versionCheckingViewModel.getMessage();

        msg.observe(AdminHomeActivity.this, new Observer<String>() {
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

    private void fetchList() {
        LiveData<ArrayList<Organisation>> list = homeViewModel.getList();

        list.observe(this, new Observer<ArrayList<Organisation>>() {
            @Override
            public void onChanged(ArrayList<Organisation> organisations) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AdminHomeActivity.this);
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                organisation_list.setLayoutManager(linearLayoutManager);
                OrganisationListAdapter organisationListAdapter = new OrganisationListAdapter(AdminHomeActivity.this, organisations);
                organisation_list.setAdapter(organisationListAdapter);

                if (organisationListAdapter.getItemCount() == 0) {
                    no_data.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}