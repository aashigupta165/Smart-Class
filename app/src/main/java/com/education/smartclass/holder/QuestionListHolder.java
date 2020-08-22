package com.education.smartclass.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.education.smartclass.R;

public class QuestionListHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public TextView standard;
    public TextView question;
    public TextView subject;
    public TextView time;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public QuestionListHolder(@NonNull View itemView, final OnItemClickListener listener) {
        super(itemView);

        this.name = itemView.findViewById(R.id.name);
        this.standard = itemView.findViewById(R.id.class_section);
        this.question = itemView.findViewById(R.id.question);
        this.subject = itemView.findViewById(R.id.subject);
        this.time = itemView.findViewById(R.id.time);

        itemView.setOnClickListener(new View.OnClickListener() {
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
