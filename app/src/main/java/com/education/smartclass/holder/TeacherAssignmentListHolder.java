package com.education.smartclass.holder;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.education.smartclass.R;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

public class TeacherAssignmentListHolder extends RecyclerView.ViewHolder {

    public CardView cardView;
    public TextView time;
    public ImageView delete;
    public TextView title;
    public TextView download;
    public TextView class_subject;
    public RelativeLayout desc_card;
    public ExpandableRelativeLayout desc_layout;
    public TextView description;
    public ImageView dragbtn;

    public interface OnItemClickListener {
        void onCardClick(View view, int position);

        void onDownload(View view, int position);

        void onDelete(View view, int position);

        void onDrag(View view, int position);
    }

    public TeacherAssignmentListHolder(@NonNull View itemView, final TeacherAssignmentListHolder.OnItemClickListener listener) {
        super(itemView);

        this.cardView = itemView.findViewById(R.id.card);
        this.time = itemView.findViewById(R.id.time);
        this.delete = itemView.findViewById(R.id.delete);
        this.title = itemView.findViewById(R.id.title);
        this.download = itemView.findViewById(R.id.download_link);
        class_subject = itemView.findViewById(R.id.class_section_subject);
        this.desc_card = itemView.findViewById(R.id.desc_card);
        this.desc_layout = itemView.findViewById(R.id.expandable_desc);
        desc_layout.collapse();
        this.description = itemView.findViewById(R.id.desc);
        this.dragbtn = itemView.findViewById(R.id.dragbtn);

        cardView.setOnClickListener(new View.OnClickListener() {
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

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onDelete(v, position);
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
