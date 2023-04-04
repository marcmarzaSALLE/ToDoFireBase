package com.example.todolistfirebase.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.todolistfirebase.R;
import com.example.todolistfirebase.model.MenuTask;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class ListMenuAdapter extends RecyclerView.Adapter<ListMenuAdapter.ViewHolder>{
    private List<MenuTask> menuTaskList=new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;

    final ListMenuAdapter.OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(MenuTask menuTask);
    }
    public ListMenuAdapter(List<MenuTask> menuTaskList, Context context, OnItemClickListener listener1){
        this.menuTaskList = menuTaskList;
        this.inflater=LayoutInflater.from(context);
        this.context=context;
        this.listener = listener1;
    }
    @Override
    public int getItemCount(){
        return menuTaskList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.box_recyclerview_menu,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListMenuAdapter.ViewHolder holder, int position) {
        holder.bindData(menuTaskList.get(position));

    }

    public void setItems(List<MenuTask>items){
        menuTaskList = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView txtViewTitleTask,txtViewNumberTask;
        ViewHolder(View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.imgViewIconTypeTask);
            txtViewTitleTask = itemView.findViewById(R.id.txtTitleTask);
            txtViewNumberTask = itemView.findViewById(R.id.txtNumberTasks);
        }
        void bindData(final MenuTask task){
            imageView.setImageResource(task.getUrlIcon());
            txtViewTitleTask.setText(task.getNameTask());
            txtViewNumberTask.setText(task.getNumberTask());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(task);
                }
            });
        }
    }
}
