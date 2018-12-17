package com.walk.angel.angelwalk.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.walk.angel.angelwalk.Data.board.BoardData;
import com.walk.angel.angelwalk.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BoardListAdapter extends RecyclerView.Adapter<BoardListAdapter.ViewHolder>{

    private ArrayList<BoardData> arrayListOfBoardData = new ArrayList<>();

    public BoardListAdapter(ArrayList<BoardData> arrayListOfBoardData){
        this.arrayListOfBoardData = arrayListOfBoardData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View studentView = layoutInflater.inflate(R.layout.list_of_board, parent, false);

        ViewHolder viewHolder = new ViewHolder(studentView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BoardData boardData = arrayListOfBoardData.get(position);

        TextView txtTitle = holder.txtTitle;
        txtTitle.setText(boardData.getTitle());

        TextView txtWriterName = holder.txtWriterName;
        txtWriterName.setText(boardData.getNickName());

    }

    @Override
    public int getItemCount() {
        return arrayListOfBoardData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtTitle;
        public TextView txtWriterName;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtWriterName = (TextView) itemView.findViewById(R.id.txtWriterName);
        }
    }
}


