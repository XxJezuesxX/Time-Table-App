package com.example.timetable;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class row {

    int index;
    int hours;
    String tasksName;

    public row() {
        int i = index;
        int h = hours;
        String tN = tasksName;
    }

    public int getHours() {
        return hours;
    }

    public int getIndex() {
        return index;
    }

    public String getTasksName() {
        return tasksName;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setTasksName(String tasksName) {
        this.tasksName = tasksName;
    }
}

