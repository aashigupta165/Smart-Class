package com.education.smartclass.holder;

import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
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
    public ExpandableRelativeLayout desc;
    public ImageView dragbtn;

    public interface OnItemClickListener {
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
        this.desc = itemView.findViewById(R.id.expandable_desc);
        desc.collapse();
        this.dragbtn = itemView.findViewById(R.id.dragbtn);

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
                        desc.toggle();
                    }
                }
                return true;
            }
        });
    }
}
