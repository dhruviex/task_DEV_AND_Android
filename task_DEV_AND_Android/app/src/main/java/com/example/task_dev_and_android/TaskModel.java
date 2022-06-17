package com.example.task_dev_and_android;

import java.sql.Blob;

public class TaskModel {
//    task variable declarations
    private String taskId;
    private String taskName;
    private String taskDescription;
    private String taskIsDone;

    //task details constructor
    public TaskModel( String taskName, String taskDescription, String taskIsDone) {
//        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskIsDone = taskIsDone;
    }

    //task details getter methods
//    public String getTaskId() {
//        return taskId;
//    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public String getTaskIsDone() {
        return taskIsDone;
    }

}
