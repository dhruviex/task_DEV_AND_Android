package com.example.task_dev_and_android.activities;

import android.Manifest;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.task_dev_and_android.R;
import com.example.task_dev_and_android.database.DatabaseHelper;
import com.example.task_dev_and_android.databinding.ActivityMainBinding;
import com.example.task_dev_and_android.model.TaskModel;

import java.io.File;

public class NewTaskActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private static int MICROPHONE_PERMISSION_CODE = 200;
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;

    private TaskModel selectTask;
    private EditText titleEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setContentView(R.layout.new_task);

        if(isMicrophonePresent()){
            getMicrophonePermission();
        }

        EditText titleEditText = findViewById(R.id.titleEditText);
        EditText descEditText = findViewById(R.id.descEditText);
        Button addTaskButton = findViewById(R.id.btn_add_task);


        //add button click listener
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                //get current values of task
                String taskName = titleEditText.getText().toString();
                String taskDescription = descEditText.getText().toString();

                //check for empty values
                if (!taskName.isEmpty() && !taskDescription.isEmpty()) {
                    DatabaseHelper dbHandler = new DatabaseHelper(NewTaskActivity.this);
                    //insert new values to database
                    dbHandler.insertTaskDetails(
                            taskName,
                            taskDescription,
                            "false"
                    );
                    //success toast
                    Toast.makeText(getApplicationContext(), "Task Added Successfully.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill all values properly!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void saveList(View view) {
    }

    //Sound Recording Code
    public void btnRecordPressed(View v){

        try {


            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.prepare();
            mediaRecorder.start();

            Toast.makeText(this,"Recording Started!!",Toast.LENGTH_LONG).show();

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void btnPlayPressed(View v){
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(getRecordingFile());
            mediaPlayer.prepare();
            mediaPlayer.start();

        }catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(this,"Recording Playing",Toast.LENGTH_LONG).show();
    }
    public void btnStopPressed(View v){

        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;

        Toast.makeText(this,"Recording Stopped",Toast.LENGTH_LONG).show();
    }
    private boolean isMicrophonePresent() {
        if (this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE)) {
            return true;
        } else {
            return false;
        }
    }
    private void getMicrophonePermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.RECORD_AUDIO},MICROPHONE_PERMISSION_CODE);
        }
    }
    private String getRecordingFile(){
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File musicDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file  =new File(musicDirectory, "RecordingFile" + ".mp3");
        return file.getPath();
    }

}
