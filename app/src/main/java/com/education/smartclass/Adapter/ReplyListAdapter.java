package com.education.smartclass.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.education.smartclass.R;
import com.education.smartclass.holder.ReplyListHolder;
import com.education.smartclass.models.Reply;

import java.util.ArrayList;

public class ReplyListAdapter extends RecyclerView.Adapter<ReplyListHolder> {

    private Context c;
    private ArrayList<Reply> replies;

    private ReplyListHolder.OnItemClickListener mListener;

    public void setOnItemClickListener(ReplyListHolder.OnItemClickListener listener) {
        mListener = listener;
    }

    public ReplyListAdapter(Context c, ArrayList<Reply> replies) {
        this.c = c;
        this.replies = replies;
    }

    @NonNull
    @Override
    public ReplyListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.replies_row, null);
        return new ReplyListHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ReplyListHolder replyListHolder, int position) {
        if (replies.get(position).getReplierRole().equals("Teacher")) {
            replyListHolder.delete.setVisibility(View.VISIBLE);
            replyListHolder.standard.setText("Faculty");
        } else {
            replyListHolder.delete.setVisibility(View.GONE);
            replyListHolder.standard.setText("STD: " + replies.get(position).getReplierClass() + " " + replies.get(position).getReplierSection());
        }
        replyListHolder.time.setText(replies.get(position).getReplyDateTime());
        replyListHolder.name.setText("Posted by: " + replies.get(position).getReplierName());
        replyListHolder.reply.setText(replies.get(position).getReply());
    }

    @Override
    public int getItemCount() {
        return replies.size();
    }
}
