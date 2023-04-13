package com.example.todolistfirebase.model;
import com.example.todolistfirebase.R;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

@IgnoreExtraProperties
public class Task implements Serializable {
    public String name;
    public String description;

    public String typeTask;

    public String date;
    public String time;
    private Date dateTime;
    public Task() {
    }

    public Task(String typeTask,String name, String description,String date,String time) {
        this.typeTask = typeTask;
        this.name = name;
        this.description = description;
        this.date = date;
        this.time = time;
    }

    public String getTypeTask() {
        return typeTask;
    }


    public String getDate() {
        return date;
    }


    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getIconOfTypeTask(){
        HashMap<String,Integer> iconTypeTask = new HashMap<>();
        iconTypeTask.put("Homework", R.drawable.baseline_school_64);
        iconTypeTask.put("Shopping", R.drawable.baseline_shopping_cart_64);
        iconTypeTask.put("Dinner", R.drawable.baseline_dinner_dining_64);
        iconTypeTask.put("Fitness", R.drawable.baseline_fitness_center_64);
        iconTypeTask.put("Study", R.drawable.baseline_menu_book_64);
        iconTypeTask.put("Other", R.drawable.baseline_more_horiz_64);
        return iconTypeTask.get(getTypeTask());

    }
}
