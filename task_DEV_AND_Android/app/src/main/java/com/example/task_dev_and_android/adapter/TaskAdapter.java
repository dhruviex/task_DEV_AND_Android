package com.example.task_dev_and_android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_dev_and_android.databinding.TaskViewBinding;
import com.example.task_dev_and_android.model.TaskModel;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {
    Context context;
    TaskClickListener mListener;
    ArrayList<TaskModel> taskList;

    public interface TaskClickListener{
        void onDeleteClickListener(int position);
        void onUpdateClickListener(int position);
    }

    public TaskAdapter(Context context, TaskClickListener mListener){
        this.context = context;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(TaskViewBinding.inflate((LayoutInflater.from(context)), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            holder.setDataToView(taskList.get(position));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void doRefresh(ArrayList<TaskModel> taskList) {
        this.taskList = taskList;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TaskViewBinding itemView;
        public MyViewHolder(@NonNull TaskViewBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
            init();
        }

        private void init() {
            itemView.deleteTaskBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onDeleteClickListener(getAdapterPosition());
                }
            });

            itemView.btnTaskDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onUpdateClickListener(getAdapterPosition());
                }
            });
        }

        public void setDataToView(TaskModel taskModel) {
            itemView.taskTitle.setText(taskModel.getTaskName());
            itemView.taskDesc.setText(taskModel.getTaskDescription());
        }
    }
}
