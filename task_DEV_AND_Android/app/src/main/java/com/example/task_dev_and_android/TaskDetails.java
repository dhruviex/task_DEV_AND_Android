package com.example.task_dev_and_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.task_dev_and_android.database.DatabaseHelper;

public class TaskDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        //get previous details for task
        String preTaskName = getIntent().getStringExtra("TASK_NAME");
        String preTaskDescription = getIntent().getStringExtra("TASK_DESC");


        //set previous task name
        EditText taskNameEditText = findViewById(R.id.et_task_name);
        taskNameEditText.setText(preTaskName);

        //set previous task description
        EditText taskDescriptionEditText = findViewById(R.id.et_task_desc);
        taskDescriptionEditText.setText(preTaskDescription);

        Button updateTaskButton = findViewById(R.id.btn_edit);
        Button deleteTaskButton = findViewById(R.id.btn_delete);

        //update button listener
        updateTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                //get current values of task
                String taskName = taskNameEditText.getText().toString();
                String taskDescription = taskDescriptionEditText.getText().toString();

                //check for empty values
                if (!taskName.isEmpty() && !taskDescription.isEmpty()) {
                    DatabaseHelper dbHandler = new DatabaseHelper(TaskDetails.this);
                    //update database values with new values
                    dbHandler.UpdateTaskDetails(
                            taskNameEditText.getText().toString(),
                            taskDescriptionEditText.getText().toString()
                    );
                    //success toast
                    Toast.makeText(getApplicationContext(), "Task Updated Successfully.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill all values properly!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}