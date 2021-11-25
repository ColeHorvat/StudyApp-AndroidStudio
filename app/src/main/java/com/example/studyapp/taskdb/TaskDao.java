package com.example.studyapp.taskdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void insert(Task task);

    @Query("DELETE FROM task_table")
    void deleteAll();

    @Query("SELECT * FROM task_table ORDER BY id ASC")
    LiveData<List<Task>> getAllTasks();

}
