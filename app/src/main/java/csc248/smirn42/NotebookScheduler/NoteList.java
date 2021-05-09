package csc248.smirn42.NotebookScheduler;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import csc248.smirn42.NotebookScheduler.ListAdapters;
import csc248.smirn42.NotebookScheduler.ListModel;
package csc248.smirn42.NotebookScheduler.ListDataBaseHandler;


import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class NoteList extends AppCompatActivity implements ListDialogListener {

    private DataBaseHelper db;

    private RecyclerView tasksRecyclerView;
    private ListAdapters tasksAdapter;
    private Button addTask;

    private List<ListModel> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        Objects.requireNonNull(getSupportActionBar()).hide();


        Button toSchool= (Button) findViewById(R.id.addTask);

        toSchool.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(csc248.smirn42.NotebookScheduler.NoteList.this, notes_example.class));
            }

        });

        db = new DataBaseHelper(this);
        db.openDatabase();

        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new ListAdapters(db,csc248.smirn42.NotebookScheduler.NoteList.this);
        tasksRecyclerView.setAdapter(tasksAdapter);

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new ItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        addTask = findViewById(R.id.new);

        taskList = db.getAllTasks();
        Collections.reverse(taskList);

        tasksAdapter.setTasks(taskList);

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewTask.newInstance().show(getSupportFragmentManager(), NewTask.TAG);
            }
        });
    }

    @Override
    public void handleDialogClose(DialogInterface dialog){
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();
    }
}







