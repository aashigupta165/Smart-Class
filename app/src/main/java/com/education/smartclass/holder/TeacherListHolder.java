package com.education.smartclass.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.education.smartclass.R;

public class TeacherListHolder extends RecyclerView.ViewHolder {

    public TextView teacherName;
    public TextView teacherCode;
    public ImageView status;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public TeacherListHolder(@NonNull View itemView, final OnItemClickListener listener) {
        super(itemView);

        this.teacherName = itemView.findViewById(R.id.subject);
        this.teacherCode = itemView.findViewById(R.id.Time);
        this.status = itemView.findViewById(R.id.status);

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(v, position);
                    }
                }
            }
        });
    }
}
