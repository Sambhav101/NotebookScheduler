package csc248.smirn42.NotebookScheduler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class DayView extends AppCompatActivity {

    Button linkButton;
    TextView day_textView ;
    ListView listView ;
    ImageButton right;
    ImageButton Left;
    Date d;
    String currentDay , currentMonth , currentYear , date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_view);
        linkButton =   (Button) findViewById(R.id.linkButton);
        day_textView = findViewById(R.id.day);
        listView  = findViewById(R.id.list_of_events);
        right = findViewById(R.id.rightImage);
        Left = findViewById(R.id.leftImage);
        //get intent
        Intent comingIntent = getIntent();
         date = getComingIntentExtras(comingIntent);
        day_textView.setText(date);
        String [] str = date.split("-");
        currentDay = str[0];
        currentMonth = str[1];
        currentYear = str[2];

        FillListView(currentDay , currentMonth , currentYear);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy");
                Calendar c = Calendar.getInstance();
                try {
                    Date d = sdf.parse(date);
                    c.setTime(Objects.requireNonNull(d));
                    c.add(Calendar.DATE ,1 );
                    date = sdf.format(c.getTime());
                    day_textView.setText(date);
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }
                FillListView(currentDay , currentMonth , currentYear);
            }
        });
        Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy");
                Calendar c = Calendar.getInstance();
                try {
                    Date d = sdf.parse(date);
                    c.setTime(Objects.requireNonNull(d));
                    c.add(Calendar.DATE ,-1 );
                    date = sdf.format(c.getTime());
                    day_textView.setText(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                FillListView(currentDay , currentMonth , currentYear);
            }
        });
        linkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : add the link of that button
            }
        });



    }

    private String getComingIntentExtras(Intent intent){

       String dayOfMonth =intent.getStringExtra("date");
        return dayOfMonth;
    }
    private void FillListView(String dayOfMonth , String month , String year){
        ArrayList<Event> arrayOfEvents = DataBaseWithUI.ListOfEvents(dayOfMonth , month , year);
        if(arrayOfEvents != null) {
            eventsAdapter adapter = new eventsAdapter(csc248.smirn42.NotebookScheduler.DayView.this, arrayOfEvents);
            listView.setAdapter(adapter);
        }
    }
}