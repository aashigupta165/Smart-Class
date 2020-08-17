package com.education.smartclass.roles.Organisation.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.education.smartclass.R;
import com.education.smartclass.storage.SharedPrefManager;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ProfileFragment extends Fragment {

    private TextView orgCode, orgName, orgAddress;
    private ImageView orgLogo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        orgLogo = view.findViewById(R.id.org_Logo);
        orgName = view.findViewById(R.id.org_Name);
        orgCode = view.findViewById(R.id.org_Code);
        orgAddress = view.findViewById(R.id.org_Address);

//        orgLogo.setImageDrawable(LoadImageFromWebOperations(SharedPrefManager.getInstance(getContext()).getUser().getOrgLogo()));
        orgName.setText(SharedPrefManager.getInstance(getContext()).getUser().getOrgName());
        orgCode.setText("Org's id - " + SharedPrefManager.getInstance(getContext()).getUser().getOrgCode());
        orgAddress.setText(SharedPrefManager.getInstance(getContext()).getUser().getOrgAddress());

        Bitmap b = null;
        try
        {
            URL url = new URL(SharedPrefManager.getInstance(getContext()).getUser().getOrgLogo());
            InputStream is = new BufferedInputStream(url.openStream());
            b = BitmapFactory.decodeStream(is);
        } catch(Exception e){}
        orgLogo.setImageBitmap(b);

        return view;
    }

//    public static Drawable LoadImageFromWebOperations(String url) {
//        try {
//            InputStream is = (InputStream) new URL(url).getContent();
//            Drawable d = Drawable.createFromStream(is, "src name");
//            return d;
//        } catch (Exception e) {
//            return null;
//        }
//    }
}