package csc248.smirn42.NotebookScheduler;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import csc248.smirn42.NotebookScheduler.NewTask;
import csc248.smirn42.NotebookScheduler.NoteList;
import csc248.smirn42.NotebookScheduler.ListModel;

import csc248.smirn42.NotebookScheduler.ListDataBaseHandler;

import java.util.List;

public class ListAdapters extends RecyclerView.Adapter<ListAdapters.ViewHolder>{
    private List<ListModel> todoList;
    private ListDatabaseHandler db;
    private NoteList activity;

    public ListAdapters(ListDatabaseHandler db, NoteList activity) {
        this.db = db;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_task, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        db.openDatabase();

        final ListModel item = todoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getTaskStatus()));
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    db.updateTaskStatus(item.getTaskId(), 1);
                } else {
                    db.updateTaskStatus(item.getTaskId(), 0);
                }
            }
        });
    }

    private boolean toBoolean(int n) {
        return n != 0;
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public Context getContext() {
        return activity;
    }

    public void setTasks(List<ListModel> todoList) {
        this.todoList = todoList;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        ListModel item = todoList.get(position);
        db.deleteTask(item.getTaskId());
        todoList.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position) {
        ListModel item = todoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getTaskId());
        bundle.putString("task", item.getTask());
        NewTask fragment = new NewTask();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), NewTask.TAG);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;

        ViewHolder(View view) {
            super(view);
            task = view.findViewById(R.id.TaskCheckBox);
        }
    }
}