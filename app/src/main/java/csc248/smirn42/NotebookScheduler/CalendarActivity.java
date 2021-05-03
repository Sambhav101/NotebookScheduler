package csc248.smirn42.NotebookScheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class CalendarActivity extends AppCompatActivity {

    Button linkButton;
    CompactCalendarView calendarView;
    TextView textView;
    String currentMonth, currentYear;
    ImageButton left, right;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM-yyyy", Locale.getDefault());
    boolean scrollFromButton = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_main);
        linkButton = (Button) findViewById(R.id.linkButton);
        calendarView = (CompactCalendarView) findViewById(R.id.simpleCalendarView);
        calendarView.setUseThreeLetterAbbreviation(true);
        textView = findViewById(R.id.monthandyear);
        left = findViewById(R.id.leftImage);
        right = findViewById(R.id.rightImage);
        //set current date as init of the text view
        getCurrentDate();
        //calendarView.shouldScrollMonth(false);
        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Intent intent = new Intent(getBaseContext(), DayView.class);
                SimpleDateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
                String formattedDate = df.format(dateClicked);
                intent.putExtra("date", formattedDate);
                intent.putExtra("dateIndateFormate", dateClicked);
                startActivity(intent);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                if (scrollFromButton) {
                    scrollFromButton = false;
                    return;
                }
                SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
                currentMonth = sdf.format(firstDayOfNewMonth);
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
                currentYear = sdf2.format(firstDayOfNewMonth);
                textView.setText(dateFormat.format(firstDayOfNewMonth));
            }
        });
        //scroll with left button
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollFromButton = true;
                clickOnLeft();
            }
        });
        //scroll with right button
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollFromButton = true;
                clickOnRight();
            }
        });


        //Mark days has events
        MarkTheDaysHasEvents();

        linkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNotebook();
            }
        });
    }

    public void goToNotebook() {
        Intent intent = new Intent(this, Notebook.class);
        startActivity(intent);
    }

    private void clickOnRight() {
        calendarView.showNextMonth();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
        Calendar c = Calendar.getInstance();
        String date = currentMonth + "-" + currentYear;
        try {
            Date d = sdf.parse(date);
            c.setTime(Objects.requireNonNull(d));
            if (currentMonth.equals("December")) {
                int x = Integer.parseInt(currentYear) + 1;
                currentYear = String.valueOf(x);
            }
            c.add(Calendar.MONTH, 1);
            date = sdf.format(c.getTime());
            currentMonth = date;
            textView.setText(date + "-" + currentYear);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void clickOnLeft() {
        calendarView.showPreviousMonth();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
        Calendar c = Calendar.getInstance();
        String date = currentMonth + "-" + currentYear;
        try {
            Date d = sdf.parse(date);
            c.setTime(Objects.requireNonNull(d));
            if (currentMonth.equals("January")) {
                int x = Integer.parseInt(currentYear) - 1;
                currentYear = String.valueOf(x);
            }
            c.add(Calendar.MONTH, -1);
            date = sdf.format(c.getTime());
            currentMonth = date;
            textView.setText(date + "-" + currentYear);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void MarkTheDaysHasEvents() {
        DataBaseWithUI dataBaseWithUI = new DataBaseWithUI(getApplicationContext());
        ArrayList<String> dates = dataBaseWithUI.ListOfDatesHasEvents();
        //   DummyData d = new DummyData();
        //    ArrayList<String> dates = d.arrayList;
        if (dates != null) {
            for (int i = 0; i < dates.size(); i++) {
                String date = dates.get(i);
                Calendar calendar = Calendar.getInstance();
                String[] str = date.split("-");
                calendar.set(Calendar.YEAR, Integer.parseInt(str[2]));
                calendar.set(Calendar.MONTH, mapMonth(str[1]) - 1);
                calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(str[0]));
                long milliTime = calendar.getTimeInMillis();
                Event event = new Event(Color.RED, milliTime, "dd");
                calendarView.addEvent(event);
            }
        }
    }

    private int mapMonth(String month) {
        HashMap<String, Integer> daysOfMonth = new HashMap<>();
        daysOfMonth.put("January", 1);
        daysOfMonth.put("February", 2);
        daysOfMonth.put("March", 3);
        daysOfMonth.put("April", 4);
        daysOfMonth.put("May", 5);
        daysOfMonth.put("June", 6);
        daysOfMonth.put("July", 7);
        daysOfMonth.put("August", 8);
        daysOfMonth.put("September", 9);
        daysOfMonth.put("October", 10);
        daysOfMonth.put("November", 11);
        daysOfMonth.put("December", 12);
        return daysOfMonth.get(month);
    }

    private void getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
        SimpleDateFormat YearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        //    String formattedDate = df.format(c);
        currentMonth = monthFormat.format(c);
        currentYear = YearFormat.format(c);
        textView.setText(currentMonth + "-" + currentYear);
    }
}


