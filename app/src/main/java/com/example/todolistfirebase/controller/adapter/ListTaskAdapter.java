package com.example.todolistfirebase.controller.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistfirebase.R;
import com.example.todolistfirebase.controller.fragments.MenuListFragment;
import com.example.todolistfirebase.controller.manager.FireBaseController;
import com.example.todolistfirebase.model.MenuTask;
import com.example.todolistfirebase.model.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;

import java.util.ArrayList;
import java.util.List;

public class ListTaskAdapter extends RecyclerView.Adapter<ListTaskAdapter.ViewHolder> {
    private ArrayList<Task> taskList;
    private LayoutInflater inflater;
    private Context context;
    private Activity myActivity;

    final ListTaskAdapter.OnItemClickListener listener;


    public ListTaskAdapter(ArrayList<Task> taskList, Context context, Activity myActivity, OnItemClickListener listener1) {
        this.taskList = taskList;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.listener = listener1;
        this.myActivity = myActivity;
    }


    public interface OnItemClickListener {
        void onItemClick(Task task);
    }

    @NonNull
    @Override
    public ListTaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.box_recyclerview_task, null);
        return new ListTaskAdapter.ViewHolder(view, myActivity);
    }

    @Override
    public void onBindViewHolder(ListTaskAdapter.ViewHolder holder, int position) {
        holder.bindData(this.taskList.get(position));
    }

    @Override
    public int getItemCount() {
        return this.taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtViewTitleTask;
        ImageView imgViewIconTask;

        private FireBaseController fireBaseController;

        ViewHolder(View itemView, Activity myActivity) {
            super(itemView);
            txtViewTitleTask = itemView.findViewById(R.id.txtNameTask);
            imgViewIconTask = itemView.findViewById(R.id.imgIconTask);
            fireBaseController = new FireBaseController(myActivity.getApplicationContext());
        }

        void bindData(final Task task) {
            txtViewTitleTask.setText(task.getName());
            imgViewIconTask.setImageResource(task.getIconOfTypeTask());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(task);

                }
            });
        }

    }
}
