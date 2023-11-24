package au.edu.federation.itech3107.studentattendance30395746;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class StudentSchedule extends AppCompatActivity {
    private CalendarView calendarView;
    private Button nextButton;
    private ArrayList<CalendarDate> datesList;
    private int currentIndex = 0; // Used to track the index of the currently displayed date in the list
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_schedule);
        SharedPreferences sharedPreferences = getSharedPreferences("course_data", MODE_PRIVATE);
        String course_id = sharedPreferences.getString("course_name", "");
        String course_name = sharedPreferences.getString("course_id", "");
        DBOpenHelper dbHelper = new DBOpenHelper(this, "record.db", null, 1);
        datesList = dbHelper.getAllDates(course_name, course_id);

        calendarView = findViewById(R.id.calendarView3);

        // If there is a date, the first date is displayed
        if (!datesList.isEmpty()) {
            CalendarDate firstDate = datesList.get(0);
            long millis = getDateInMillis(firstDate.getYear(), firstDate.getMonth(), firstDate.getDay());
            calendarView.setDate(millis, true, false);
        }

        // Setting the button click event
        nextButton = findViewById(R.id.button19);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextDate();
            }
        });
    }

    private void showNextDate() {
        // Check if the next date is still available
        if (currentIndex < datesList.size() - 1) {
            currentIndex++;
            CalendarDate nextDate = datesList.get(currentIndex);
            long millis = getDateInMillis(nextDate.getYear(), nextDate.getMonth(), nextDate.getDay());
            calendarView.setDate(millis, true, false);
        }
        else {
            Toast.makeText(getApplicationContext(), "There is no next one. It will be displayed from the first date.", Toast.LENGTH_SHORT).show();
            // If there is no next date, the first date is displayed
            currentIndex = 0;
            CalendarDate firstDate = datesList.get(0);
            long millis = getDateInMillis(firstDate.getYear(), firstDate.getMonth(), firstDate.getDay());
            calendarView.setDate(millis, true, false);
        }
    }

    private long getDateInMillis(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTimeInMillis();
    }

}