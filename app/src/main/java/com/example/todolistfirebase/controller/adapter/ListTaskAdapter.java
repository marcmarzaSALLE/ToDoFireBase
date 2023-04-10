package com.example.todolistfirebase.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistfirebase.R;
import com.example.todolistfirebase.model.MenuTask;
import com.example.todolistfirebase.model.Task;

import java.util.ArrayList;
import java.util.List;

public class ListTaskAdapter extends RecyclerView.Adapter<ListTaskAdapter.ViewHolder> {
    private ArrayList<Task> taskList;
    private LayoutInflater inflater;
    private Context context;
    final ListTaskAdapter.OnItemClickListener listener;

    public ListTaskAdapter(ArrayList<Task> taskList, Context context, OnItemClickListener listener1){
        this.taskList = taskList;
        this.inflater=LayoutInflater.from(context);
        this.context=context;
        this.listener = listener1;
    }


    public interface OnItemClickListener{
        void onItemClick(Task task);
    }
    @NonNull
    @Override
    public ListTaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.box_recyclerview_task, null);
        return new ListTaskAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListTaskAdapter.ViewHolder holder, int position) {
        holder.bindData(this.taskList.get(position));
    }

    @Override
    public int getItemCount() {
        return this.taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtViewTitleTask;
        ViewHolder(View itemView){
            super(itemView);
            txtViewTitleTask = itemView.findViewById(R.id.txtNameTask);
        }
        void bindData(final Task task){
            txtViewTitleTask.setText(task.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(task);
                }
            });
        }
    }
}
