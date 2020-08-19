package com.education.smartclass.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.education.smartclass.R;
import com.education.smartclass.holder.OrganisationListHolder;
import com.education.smartclass.models.Organisation;

import java.util.ArrayList;

public class OrganisationListAdapter extends RecyclerView.Adapter<OrganisationListHolder> {

    Context c;
    ArrayList<Organisation> organisationList;

    public OrganisationListAdapter(Context c, ArrayList<Organisation> organisationList) {
        this.c = c;
        this.organisationList = organisationList;
    }

    @NonNull
    @Override
    public OrganisationListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.organisation_list_row, null);
        return new OrganisationListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrganisationListHolder organisationListHolder, int position) {
        organisationListHolder.orgName.setText(organisationList.get(position).getOrgName());
        organisationListHolder.orgCode.setText(organisationList.get(position).getOrgCode());
    }

    @Override
    public int getItemCount() {
        return organisationList.size();
    }
}
