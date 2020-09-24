package com.education.smartclass.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.education.smartclass.R;

public class TeacherFetchStudentAssignmentListHolder extends RecyclerView.ViewHolder {

    public CardView card;
    public TextView name_roll;
    public TextView time;
    public ImageView status;
    public TextView description;
    public TextView remark;
    public TextView download;
    public TextView add_remark;

    public interface OnItemClickListener {
        void onDownload(View view, int position);

        void addRemark(View view, int position);
    }

    public TeacherFetchStudentAssignmentListHolder(@NonNull View itemView, final TeacherFetchStudentAssignmentListHolder.OnItemClickListener listener) {
        super(itemView);

        this.card = itemView.findViewById(R.id.card);
        this.name_roll = itemView.findViewById(R.id.name_rollno);
        this.time = itemView.findViewById(R.id.time);
        this.status = itemView.findViewById(R.id.status);
        this.description = itemView.findViewById(R.id.description);
        this.remark = itemView.findViewById(R.id.remark);
        this.download = itemView.findViewById(R.id.download_link);
        this.add_remark = itemView.findViewById(R.id.add_remark);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onDownload(v, position);
                    }
                }
            }
        });

        add_remark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.addRemark(v, position);
                    }
                }
            }
        });
    }


}
