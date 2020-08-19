package com.education.smartclass.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.education.smartclass.R;

public class OrganisationListHolder extends RecyclerView.ViewHolder {

    public TextView orgName;
    public TextView orgCode;

    public OrganisationListHolder(@NonNull View itemView) {
        super(itemView);

        this.orgName = itemView.findViewById(R.id.org_Name);
        this.orgCode = itemView.findViewById(R.id.org_Code);
    }
}
