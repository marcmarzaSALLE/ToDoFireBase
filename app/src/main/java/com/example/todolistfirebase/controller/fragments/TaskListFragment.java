package com.example.todolistfirebase.controller.fragments;

import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.example.todolistfirebase.R;
import com.example.todolistfirebase.controller.adapter.ListTaskAdapter;
import com.example.todolistfirebase.controller.manager.FireBaseController;
import com.example.todolistfirebase.model.Task;
import com.example.todolistfirebase.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class TaskListFragment extends Fragment {
    private FireBaseController fireBaseController;
    private ArrayList<Task> tasks;
    RecyclerView recyclerView;



    private ListTaskAdapter listTaskAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        syncronizeData(view);
        loadDataFirebaseController();
        // Inflate the layout for this fragment
        return view;
    }

    private void addDataToAdapter(ArrayList<Task> tasks) {
        listTaskAdapter = new ListTaskAdapter(tasks, requireContext(),getActivity() ,new ListTaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Task task) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("task", task);
                ShowInformationFragment showInformationFragment = new ShowInformationFragment();
                showInformationFragment.setArguments(bundle);
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, showInformationFragment).commit();
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(listTaskAdapter);

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                TaskListFragment.this.removeItem(position,viewHolder);

            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    // Obtener la vista del elemento
                    View itemView = viewHolder.itemView;
                    ImageView deleteIcon = getLayoutInflater().inflate(R.layout.list_item, null).findViewById(R.id.delete_icon);

                    // Dibujar el ícono de X en la posición correcta
                    if (dX < 0) {
                        int iconMargin = (itemView.getHeight() - deleteIcon.getDrawable().getIntrinsicHeight()) / 2;
                        int iconTop = itemView.getTop() + (itemView.getHeight() - deleteIcon.getDrawable().getIntrinsicHeight()) / 2;
                        float iconBottom = iconTop + deleteIcon.getDrawable().getIntrinsicHeight();

                        float iconLeft = itemView.getRight() - iconMargin - deleteIcon.getDrawable().getIntrinsicWidth();
                        float iconRight = itemView.getRight() - iconMargin;

                        deleteIcon.getDrawable().setBounds((int) iconLeft, (int) iconTop, (int) iconRight, (int) iconBottom);
                        deleteIcon.draw(c);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void removeItem(int position, RecyclerView.ViewHolder viewHolder){
        FragmentManager fm = ((FragmentActivity) requireContext()).getSupportFragmentManager();
        Task task1 = tasks.get(position);
        fireBaseController.deleteTaskFromUser(task1, new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task task) {
                if(task.isSuccessful()){
                    Log.wtf("TAG", "onComplete: " + task1.getName());
                    tasks.remove(position);
                    fm.beginTransaction().replace(R.id.fragmentLayout, new MenuListFragment()).commit();
                    listTaskAdapter.notifyItemRemoved(position);
                    listTaskAdapter.notifyItemRangeChanged(position, tasks.size());
                    listTaskAdapter.notifyDataSetChanged();
                }
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.wtf("TAG", "onFailure: " + e.getMessage());
            }
        });
    }


    private void syncronizeData(View view) {
        fireBaseController = new FireBaseController(requireContext());
        recyclerView = view.findViewById(R.id.recyclerViewTaskList);
        tasks = new ArrayList<>();
    }

    private void loadDataFirebaseController() {
        fireBaseController.getTasksFromUser(new OnCompleteListener<QuerySnapshot>(){

            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                        ArrayList<Task> tasks1 = documentSnapshot.toObject(User.class).getTasks();
                        TaskListFragment.this.logData(tasks1);
                    }
                }
            }
        });
    }

    private void logData(ArrayList<Task> taskList) {
        this.tasks = taskList;
        for (Task t : taskList) {
            Log.wtf("TAG", "logData: " + t.getName());
        }
        addDataToAdapter(this.tasks);
    }

}
