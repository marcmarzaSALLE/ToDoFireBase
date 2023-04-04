package com.example.todolistfirebase.model;

public class MenuTask {
    private int id;
    private int urlIcon;
    private String nameTask;
    private String numberTask;

    public MenuTask(int id,int urlIcon, String nameTask, String numberTask) {
        this.id = id;
        this.urlIcon = urlIcon;
        this.nameTask = nameTask;
        this.numberTask = numberTask;
    }

    public int getUrlIcon() {
        return urlIcon;
    }

    public String getNameTask() {
        return nameTask;
    }

    public String getNumberTask() {
        return numberTask;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUrlIcon(int urlIcon) {
        this.urlIcon = urlIcon;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public void setNumberTask(String numberTask) {
        this.numberTask = numberTask;
    }
}
