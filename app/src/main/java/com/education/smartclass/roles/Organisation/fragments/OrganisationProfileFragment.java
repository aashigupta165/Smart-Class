package com.education.smartclass.roles.Organisation.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.education.smartclass.R;
import com.education.smartclass.storage.SharedPrefManager;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class OrganisationProfileFragment extends Fragment {

    private TextView orgCode, orgName, orgAddress;
    private ImageView orgLogo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_organisation_profile, container, false);

        orgLogo = view.findViewById(R.id.org_Logo);
        orgName = view.findViewById(R.id.org_Name);
        orgCode = view.findViewById(R.id.org_Code);
        orgAddress = view.findViewById(R.id.org_Address);

        orgName.setText(SharedPrefManager.getInstance(getContext()).getUser().getOrgName());
        orgCode.setText("Org's id - " + SharedPrefManager.getInstance(getContext()).getUser().getOrgCode());
        orgAddress.setText(SharedPrefManager.getInstance(getContext()).getUser().getOrgAddress());

        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    try {
                        URL url = new URL(SharedPrefManager.getInstance(getContext()).getUser().getOrgLogo());
                        Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        orgLogo.setImageBitmap(bitmap);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 1000);
    }
}