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

public class StudentAssignmentListHolder extends RecyclerView.ViewHolder {

    public CardView cardView;
    public TextView subject;
    public TextView time;
    public ImageView status;
    public TextView title;
    public RelativeLayout remark_card;
    public ExpandableRelativeLayout expandable_remark;
    public TextView remark;
    public ImageView dragbtn;

    public interface OnItemClickListener {
        void onCardClick(View view, int position);

        void onDrag(View view, int position);
    }

    public StudentAssignmentListHolder(@NonNull View itemView, final StudentAssignmentListHolder.OnItemClickListener listener) {
        super(itemView);

        this.cardView = itemView.findViewById(R.id.card);
        this.subject = itemView.findViewById(R.id.subject);
        this.time = itemView.findViewById(R.id.time);
        this.status = itemView.findViewById(R.id.status);
        this.title = itemView.findViewById(R.id.title);
        this.remark_card = itemView.findViewById(R.id.remark_card);
        this.expandable_remark = itemView.findViewById(R.id.expandable_remark);
        expandable_remark.collapse();
        this.remark = itemView.findViewById(R.id.remark);
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

        dragbtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onDrag(v, position);
                        expandable_remark.toggle();
                    }
                }
                return true;
            }
        });
    }
}
