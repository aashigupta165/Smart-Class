package com.education.smartclass.roles.admin.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.education.smartclass.R;
import com.education.smartclass.roles.admin.model.RegisterViewModel;
import com.education.smartclass.utils.SnackBar;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RegisterNewOrgActivity3 extends AppCompatActivity {

    private TextView submitbtn, status;
    private ImageButton logoUpload;
    private ProgressDialog progressDialog;

    private MultipartBody.Part logo;

    private RegisterViewModel registerViewModel;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_new_org_3);

        relativeLayout = findViewById(R.id.relativeLayout);
        logoUpload = findViewById(R.id.logo_upload);
        submitbtn = findViewById(R.id.submitbtn);
        status = findViewById(R.id.status);

        progressDialog = new ProgressDialog(this);

        buttonClickEvents();
        dataObserver();
    }

    private void buttonClickEvents() {

        logoUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 0);
            }
        });

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerOrganisation();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 0 && resultCode == RESULT_OK && data != null) {
                Bitmap bitmap = BitmapFactory.decodeFileDescriptor(this.getContentResolver()
                        .openFileDescriptor(data.getData(), "r").getFileDescriptor());
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                RequestBody requestLogo = RequestBody.create(MediaType.parse("image/*"), byteArray);
                Date date = Calendar.getInstance().getTime();
                logo = MultipartBody.Part.createFormData("file", date + "organisationLogo.png", requestLogo);
                status.setVisibility(View.VISIBLE);
            } else {
                new SnackBar(relativeLayout, "You haven't picked Image.");
            }
        } catch (Exception e) {
            new SnackBar(relativeLayout, "Something went wrong");
        }
    }

    private void dataObserver() {

        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        LiveData<String> message = registerViewModel.getMessage();

        message.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                progressDialog.dismiss();

                switch (s) {
                    case "org_created":
                        new SnackBar(relativeLayout, "Organisation Registered");
                        Intent intent = new Intent(RegisterNewOrgActivity3.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        break;
                    case "invalid_entry":
                        new SnackBar(relativeLayout, "Invalid Details");
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

    private void registerOrganisation() {

        if (logo == null) {
            new SnackBar(relativeLayout, "Please Upload the Logo");
            return;
        }

        progressDialog.setMessage("Loading...");
        progressDialog.show();

        Bundle bundle = getIntent().getExtras();

        String orgName = bundle.getString("orgName");
        String orgCode = bundle.getString("orgCode");
        String orgType = bundle.getString("orgType");
        String orgAddress = bundle.getString("orgAddress");
        String email = bundle.getString("email");
        String mobile = bundle.getString("mobile");

        registerViewModel.RegisterNewOrg(orgName, orgCode, orgType, orgAddress, email, mobile, logo);
    }
}