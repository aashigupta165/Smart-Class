package com.education.smartclass.roles.Organisation.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.education.smartclass.models.Organisation;
import com.education.smartclass.roles.Organisation.fragments.CSVReader;
import com.education.smartclass.roles.admin.ui.RegisterNewOrgActivity1;
import com.education.smartclass.R;
import com.education.smartclass.utils.Logout;
import com.education.smartclass.utils.SnackBar;
import com.google.android.material.navigation.NavigationView;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class OrganisationHomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        toolbar.setTitle("Organisation");

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorPrimaryDark));
        toggle.syncState();

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.home, R.id.profile, R.id.teacher, R.id.classes, R.id.student, R.id.logout)
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
        MenuItem item = menu.findItem(R.id.add);
        item.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                new Logout(OrganisationHomeActivity.this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Toast.makeText(OrganisationHomeActivity.this, "mai hu super", Toast.LENGTH_LONG).show();
////        Toast.makeText(OrganisationHomeActivity.this, requestCode + resultCode, Toast.LENGTH_LONG).show();
////        String path = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
//        try {
//            Toast.makeText(OrganisationHomeActivity.this, "1", Toast.LENGTH_LONG).show();
////            File file=new File(path);
//            Uri uri = data.getData();
//            String uriString = uri.toString();
//            File file = new File(uriString);
//            Toast.makeText(OrganisationHomeActivity.this, "2", Toast.LENGTH_LONG).show();
//            CSVReader csvReader=new CSVReader(OrganisationHomeActivity.this);
//            Toast.makeText(OrganisationHomeActivity.this, "3", Toast.LENGTH_LONG).show();
//            List csv=csvReader.read(file.getAbsoluteFile());
//            Toast.makeText(OrganisationHomeActivity.this, "4", Toast.LENGTH_LONG).show();
//            if(csv.size()>0){
//                String[] header_row =(String[]) csv.get(0);
//                if(header_row.length>1){
//                    String col1=header_row[0];
//                    String col2=header_row[1];
//                }
//            }
//
//            Toast.makeText(OrganisationHomeActivity.this,csv.size() + " rows", Toast.LENGTH_LONG).show();
////            Bitmap bitmap = BitmapFactory.decodeFileDescriptor(this.getContentResolver()
////                    .openFileDescriptor(data.getData(), "r").getFileDescriptor());
////            ByteArrayOutputStream stream = new ByteArrayOutputStream();
////            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
////            byte[] byteArray = stream.toByteArray();
////            Uri uri = data.getData();
////            String uriString = uri.toString();
////            File myFile = new File(uriString);
////            Toast.makeText(OrganisationHomeActivity.this, myFile.getName(), Toast.LENGTH_LONG).show();
////            Uri selectedImage = data.getData();
////            String[] filePathColumn = {MediaStore.Images.Media.DATA};
////
////            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
////            assert cursor != null;
////            cursor.moveToFirst();
////
////            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
////            String mediaPath = cursor.getString(columnIndex);
////            Toast.makeText(OrganisationHomeActivity.this, mediaPath, Toast.LENGTH_LONG).show();
//////            str1.setText(mediaPath);
////            // Set the Image in ImageView for Previewing the Media
////            cursor.close();
//
//        }catch (Exception e){
//
//        }
//         try {
//            if (requestCode == 0 && resultCode == RESULT_OK) {
//                Toast.makeText(OrganisationHomeActivity.this, "ho gya", Toast.LENGTH_LONG).show();
////                String path = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
////                Toast.makeText(OrganisationHomeActivity.this, path, Toast.LENGTH_LONG).show();
////                File file = new File(path);
////                if (path != null){
////                    filePath = path;
////                    Toast.makeText(getContext(), path, Toast.LENGTH_LONG).show();
////                }
////                Map<String, RequestBody> map = new HashMap<>();
////                RequestBody requestBody = RequestBody.create(MediaType.parse("application/*"), file);
////                filef = MultipartBody.Part.createFormData("file", "organisationLogo.csv", requestBody);
////                status.setVisibility(View.VISIBLE);
//            } else {
//                Toast.makeText(OrganisationHomeActivity.this, "Please pick", Toast.LENGTH_LONG).show();
////                new SnackBar(relativeLayout, "You haven't picked Image.");
//            }
//        } catch (Exception e) {
//            Toast.makeText(OrganisationHomeActivity.this, e.toString(), Toast.LENGTH_LONG).show();
////            new SnackBar(relativeLayout, "Something went wrong");
//        }
//    }
//
//    //    @Override
////    public boolean onCreateOptionsMenu(Menu menu) {
////        getMenuInflater().inflate(R.menu.admin_toolbar_menu, menu);
////        return super.onCreateOptionsMenu(menu);
////    }
//
////    @Override
////    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
////        switch (item.getItemId()) {
////            case R.id.add:
////                Intent intent = new Intent(OrganisationHomeActivity.this, TeacherRegisterActivity.class);
////                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                startActivity(intent);
////                break;
////            case R.id.logout:
////                break;
////        }
////        return super.onOptionsItemSelected(item);
////    }
}