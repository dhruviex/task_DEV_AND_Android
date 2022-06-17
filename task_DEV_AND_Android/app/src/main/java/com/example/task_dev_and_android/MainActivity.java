package com.example.task_dev_and_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.task_dev_and_android.databinding.ActivityMainBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements TaskAdapter.TaskClickListener {
    private ActivityMainBinding binding;
    TaskAdapter mAdapter;
    ArrayList<TaskModel> taskList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addCustomTaskList();
        setAdapter();
        newTaskBtnTapped();

        
    }

    @Override
    protected void onResume() {
        super.onResume();
        //to reload task list on re appearing the activity
        setTaskList();
        setAdapter();
    }

    //set task list from data base
    private void setTaskList() {
       taskList = new ArrayList<>();

        //get task list from database and set to task list
        DatabaseHelper db = new DatabaseHelper(this);
        ArrayList<HashMap<String, String>> tasks_list = db.GetTasks();

        if (tasks_list.size() > 0) {
            for (int i = 0; i < tasks_list.size(); i++) {
                HashMap<String, String> currentTask = tasks_list.get(i);
                taskList.add(new TaskModel(
                        currentTask.get("task_name"),
                        currentTask.get("task_description"),
                        currentTask.get("task_done")));
            }
        }

    }

    private void newTaskBtnTapped() {
        binding.btnNewTask.setOnClickListener(v -> {
            startActivity(new Intent(this, NewTaskActivity.class));
        });
    }

    private void addCustomTaskList() {
        taskList = new ArrayList<>();
        //add static tasks for testing purpose
        taskList.add(new TaskModel("Test Task1", "Task Description1","false"));
        taskList.add(new TaskModel( "Test Task2", "Task Description2","false"));
        taskList.add(new TaskModel( "Test Task3", "Task Description3","false"));
        taskList.add(new TaskModel( "Test Task4", "Task Description4","false"));
        taskList.add(new TaskModel( "Test Task5", "Task Description5","false"));
    }

    //set task adapter method
    private void setAdapter() {
        if (mAdapter == null) {
            mAdapter = new TaskAdapter(this, this);
        }
        if (binding.list.getAdapter() == null) {
            binding.list.setLayoutManager(new LinearLayoutManager(this));
            binding.list.setHasFixedSize(false);
            binding.list.setAdapter(mAdapter);
        }

        mAdapter.doRefresh(taskList);
    }


    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
    }



}