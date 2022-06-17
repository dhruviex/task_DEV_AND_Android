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

    private void newTaskBtnTapped() {
        binding.btnNewTask.setOnClickListener(v -> {
            startActivity(new Intent(this, NewTaskActivity.class));
        });
    }

    private void addCustomTaskList() {
        taskList = new ArrayList<>();
        //add static tasks for testing purpose
        taskList.add(new TaskModel("id1", "Test Task1", "Task Description1",false));
        taskList.add(new TaskModel("id2", "Test Task2", "Task Description2",false));
        taskList.add(new TaskModel("id3", "Test Task3", "Task Description3",false));
        taskList.add(new TaskModel("id4", "Test Task4", "Task Description4",false));
        taskList.add(new TaskModel("id5", "Test Task5", "Task Description5",false));
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