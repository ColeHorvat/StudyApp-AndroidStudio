package com.example.studyapp.taskdb;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;



class TaskTypeConverters {
    @TypeConverter
    public static List<Date> stringToDate(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Date>>() {}.getType();
        List<Date> dates = gson.fromJson(json, type);
        return dates;
    }

    @TypeConverter
    public static String datesToString(List<Date> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Date>>() {}.getType();
        String json = gson.toJson(list, type);
        return json;
    }
}

@Entity(tableName = "task_table")
public class Task {

    @PrimaryKey(autoGenerate = true)
    private Integer id = 1;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "group")
    private String group;

    @ColumnInfo(name = "timeProgress")
    private Float timeProgress;

    //Constructor
    public Task(
            String title,
            String description,
            String group,
            Float timeProgress
    ) {
        this.title = title;
        this.description = description;
        this.group = group;
        this.timeProgress = timeProgress;
    }

    //Getters & Setters

    @NonNull
    public Integer getId() { return id; }

    public void setId(@NonNull Integer id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getGroup() { return group; }

    public void setGroup(String group) { this.group = group; }

    public Float getTimeProgress() { return timeProgress; }

    public void setTimeProgress(Float timeProgress) {
        this.timeProgress = timeProgress;
    }
}
