package com.education.smartclass.roles.admin.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.education.smartclass.Adapter.OrganisationListAdapter;
import com.education.smartclass.R;
import com.education.smartclass.roles.admin.model.HomeViewModel;
import com.education.smartclass.models.Organisation;
import com.education.smartclass.utils.Logout;
import com.education.smartclass.utils.SnackBar;

import java.util.ArrayList;

public class AdminHomeActivity extends AppCompatActivity {

    private RelativeLayout relativeLayout;
    private RecyclerView organisation_list;
    private HomeViewModel homeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        relativeLayout = findViewById(R.id.relativeLayout);
        organisation_list = findViewById(R.id.organisation_list);

        dataObserver();
        homeViewModel.fetchOrganisationList();
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
                    default:
                        new SnackBar(relativeLayout, "Invalid Credentials");
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
            }
        });
    }
}