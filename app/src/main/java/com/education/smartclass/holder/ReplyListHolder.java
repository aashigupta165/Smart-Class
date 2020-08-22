package com.education.smartclass.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.education.smartclass.R;

public class ReplyListHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public TextView standard;
    public TextView reply;
    public TextView time;
    public ImageView delete;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public ReplyListHolder(@NonNull View itemView, final OnItemClickListener listener) {
        super(itemView);

        this.name = itemView.findViewById(R.id.replier_name);
        this.standard = itemView.findViewById(R.id.std);
        this.reply = itemView.findViewById(R.id.reply);
        this.time = itemView.findViewById(R.id.time);
        this.delete = itemView.findViewById(R.id.delete);

        delete.setOnClickListener(new View.OnClickListener() {
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
