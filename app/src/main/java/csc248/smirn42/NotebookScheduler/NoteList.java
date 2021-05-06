package csc248.smirn42.NotebookScheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;


import android.widget.Button;
public class NoteList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);


        Button toSchool= (Button) findViewById(R.id.addTask);

        toSchool.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(NoteList.this, notes_example.class));
            }

        });



    }
}