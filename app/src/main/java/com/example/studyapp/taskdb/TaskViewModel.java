package com.example.studyapp.taskdb;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private TaskRepository mRepository;

    private LiveData<List<Task>> mAllTasks;

    public TaskViewModel (Application application) {
        super(application);
        mRepository = new TaskRepository(application);
        mAllTasks = mRepository.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks() { return mAllTasks; }

    public void insert(Task task) {
        mRepository.insert(task);
        FirebaseDatabase remoteDB = FirebaseDatabase.getInstance();
        DatabaseReference myRef = remoteDB.getReference("Task");
        myRef.setValue(task);
    }
}
