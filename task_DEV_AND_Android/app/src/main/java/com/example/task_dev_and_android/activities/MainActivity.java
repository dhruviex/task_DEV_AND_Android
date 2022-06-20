package com.example.task_dev_and_android.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.task_dev_and_android.adapter.TaskAdapter;
import com.example.task_dev_and_android.database.DatabaseHelper;
import com.example.task_dev_and_android.databinding.ActivityMainBinding;
import com.example.task_dev_and_android.model.TaskModel;

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

        setTaskList();
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

    //on delete btn tap listener
    @Override
    public void onDeleteClickListener(int position) {
        //check current position for task position
        if (taskList.size() > 0) {
            if (taskList.get(position) != null) {
                TaskModel currentPlace = taskList.get(position);
                DatabaseHelper db = new DatabaseHelper(this);

                //delete task with current task ID
                db.DeleteTask(currentPlace.getTaskName());
                Toast.makeText(this,"Task Deleted Successfully.",Toast.LENGTH_SHORT).show();

                //reload activity
                setTaskList();
                setAdapter();
            }
        }
    }

    @Override
    public void onUpdateClickListener(int position) {
        //check current position for task position
        if (taskList.size() > 0) {
            if (taskList.get(position) != null) {
                TaskModel currentPlace = taskList.get(position);
                //call update activity and pass current record data
                Intent intent = new Intent(getBaseContext(), TaskDetails.class);
                intent.putExtra("TASK_NAME", currentPlace.getTaskName());
                intent.putExtra("TASK_DESCRIPTION", currentPlace.getTaskDescription());
                intent.putExtra("TASK_IS_DONE", currentPlace.getTaskIsDone());
                startActivity(intent);
            }
        }
    }

    @Override
    public void onCheckBoxClick(int position, boolean value) {
        DatabaseHelper dbHandler = new DatabaseHelper(MainActivity.this);
        TaskModel currentPlace = taskList.get(position);
        //update database values with new values
        dbHandler.UpdateTaskDetails(
                currentPlace.getTaskName(),
                currentPlace.getTaskDescription(),
                String.valueOf(value)
        );
        //success toast
        Toast.makeText(getApplicationContext(), "Task Updated Successfully.", Toast.LENGTH_SHORT).show();
        setTaskList();
        setAdapter();
    }

}