package csc248.smirn42.NotebookScheduler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddNotebook extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton note, checklist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notebook_creation);

        radioGroup = findViewById(R.id.radio_group);
        note = findViewById(R.id.note_selection);
        checklist = findViewById(R.id.checklist_selection);

    }

    public void checkInput(View view) {
        if(note.isChecked()) {
            System.out.println("Notes");
        } else {
            System.out.println("Checklist");
        }
    }

}
