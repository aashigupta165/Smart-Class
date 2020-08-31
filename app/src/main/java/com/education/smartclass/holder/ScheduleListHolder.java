package com.education.smartclass.holder;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.education.smartclass.R;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

public class ScheduleListHolder extends RecyclerView.ViewHolder {

    public TextView subject;
    public TextView time;
    public TextView count;
    public TextView standard;
    public TextView optionMenu;
    public RelativeLayout desc_card;
    public ExpandableRelativeLayout desc_layout;
    public TextView description;
    public ImageView dragbtn;

    public interface OnItemClickListener {
        void onCardClick(View view, int position);

        void onItemClick(View view, int position);

        void onDrag(View view, int position);
    }

    public ScheduleListHolder(@NonNull View itemView, final OnItemClickListener listener) {
        super(itemView);

        this.subject = itemView.findViewById(R.id.subject);
        this.time = itemView.findViewById(R.id.time);
        this.count = itemView.findViewById(R.id.count);
        this.standard = itemView.findViewById(R.id.standard);
        this.optionMenu = itemView.findViewById(R.id.optionMenu);
        this.desc_card = itemView.findViewById(R.id.desc_card);
        this.desc_layout = itemView.findViewById(R.id.expandable_desc);
        desc_layout.collapse();
        this.description = itemView.findViewById(R.id.desc);
        this.dragbtn = itemView.findViewById(R.id.dragbtn);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onCardClick(v, position);
                    }
                }
            }
        });

        optionMenu.setOnClickListener(new View.OnClickListener() {
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

        dragbtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onDrag(v, position);
                        desc_layout.toggle();
                    }
                }
                return true;
            }
        });
    }
}
