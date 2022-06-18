package com.example.task_dev_and_android.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "TASKS_DB";
    private static final String TABLE_TASKS = "tasks";
    private static final String KEY_ID = "id";
    private static final String KEY_TASK_NAME = "task_name";
    private static final String KEY_TASK_DESCRIPTION = "task_description";
    private static final String KEY_TASK_DONE = "task_done";

    public DatabaseHelper(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TASK_NAME + " TEXT,"
                + KEY_TASK_DESCRIPTION + " TEXT,"
                + KEY_TASK_DONE + " TEXT"+ ")";
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        // Create tables again
        onCreate(db);
    }

    // Adding new Task Details
    public void insertTaskDetails(String task_name, String task_description, String task_done){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues taskValues = new ContentValues();
        taskValues.put(KEY_TASK_NAME, task_name);
        taskValues.put(KEY_TASK_DESCRIPTION, task_description);
        taskValues.put(KEY_TASK_DONE, task_done);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_TASKS,null, taskValues);
        db.close();
    }

    // Get Task List
    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> GetTasks(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> taskList = new ArrayList<>();
        String query = "SELECT task_name, task_description, task_done FROM "+ TABLE_TASKS;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> taskObj = new HashMap<>();
            taskObj.put("task_name",cursor.getString(cursor.getColumnIndex(KEY_TASK_NAME)));
            taskObj.put("task_description",cursor.getString(cursor.getColumnIndex(KEY_TASK_DESCRIPTION)));
            taskObj.put("task_done",cursor.getString(cursor.getColumnIndex(KEY_TASK_DONE)));
            taskList.add(taskObj);
        }
        return  taskList;
    }

    // Get Task Details based on taskId
    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> GetTaskByTaskId(String taskId){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> taskList = new ArrayList<>();
        String query = "SELECT task_name, task_description, task_done FROM "+ TABLE_TASKS;
        Cursor cursor = db.query(TABLE_TASKS, new String[]{ KEY_TASK_NAME, KEY_TASK_DESCRIPTION, KEY_TASK_DONE},
                KEY_TASK_NAME+ "=?",new String[]{String.valueOf(taskId)},
                null, null, null, null);
        if (cursor.moveToNext()){
            HashMap<String,String> taskObj = new HashMap<>();
            taskObj.put("task_name",cursor.getString(cursor.getColumnIndex(KEY_TASK_NAME)));
            taskObj.put("task_description",cursor.getString(cursor.getColumnIndex(KEY_TASK_DESCRIPTION)));
            taskObj.put("task_done",cursor.getString(cursor.getColumnIndex(KEY_TASK_DONE)));
            taskList.add(taskObj);
        }
        return  taskList;
    }

    // Delete Task Details
    public void DeleteTask(String taskName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, KEY_TASK_NAME+" = ?",new String[]{String.valueOf(taskName)});
        db.close();
    }

    // Update Task Details
    public int UpdateTaskDetails( String task_name, String task_description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues taskValues = new ContentValues();
        taskValues.put(KEY_TASK_NAME, task_name);
        taskValues.put(KEY_TASK_DESCRIPTION, task_description);
        int count = db.update(TABLE_TASKS, taskValues, KEY_TASK_NAME+" = ?",new String[]{String.valueOf(task_name)});
        return  count;
    }
}