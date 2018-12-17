package com.walk.angel.angelwalk.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.walk.angel.angelwalk.Data.board.CommentData;
import com.walk.angel.angelwalk.R;

import java.util.ArrayList;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder>{
    private ArrayList<CommentData> arrayListOfCommentData = new ArrayList<>();

    public CommentListAdapter(ArrayList<CommentData> arrayListOfCommentData){
        this.arrayListOfCommentData = arrayListOfCommentData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View studentView = layoutInflater.inflate(R.layout.list_of_comment, parent, false);

        ViewHolder viewHolder = new ViewHolder(studentView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommentData commentData = arrayListOfCommentData.get(position);

        TextView txtNickName = holder.txtNickName;
        txtNickName.setText(commentData.getNickName());

        TextView txtCommentDate = holder.txtCommentDate;
        txtCommentDate.setText(commentData.getCreateDate());

        TextView txtContent = holder.txtContent;
        txtContent.setText(commentData.getContent());
    }

    @Override
    public int getItemCount() {
        return arrayListOfCommentData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtContent;
        public TextView txtCommentDate;
        public TextView txtNickName;

        public ViewHolder(View itemView) {
            super(itemView);
            txtContent = (TextView) itemView.findViewById(R.id.txtContent);
            txtCommentDate = (TextView) itemView.findViewById(R.id.txtCommentDate);
            txtNickName = (TextView) itemView.findViewById(R.id.txtNickName);
        }
    }
}
