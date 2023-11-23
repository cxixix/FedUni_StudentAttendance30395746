package au.edu.federation.itech3107.studentattendance30395746;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

public class CourseSchedule extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_schedule);
        CalendarView calendarView = findViewById(R.id.calendarView2);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Logic to handle date selection here
                // Store the selected date in the database
                Intent intent = getIntent();
                String course_id = intent.getStringExtra("course_id");
                String course_name = intent.getStringExtra("course_name");
                DBOpenHelper dbOpenHelper = new DBOpenHelper(CourseSchedule.this, "record.db", null, 1);
                SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("course_id", course_id);
                values.put("coursename", course_name);
                values.put("year", year);
                values.put("month", month);
                values.put("day", dayOfMonth);
                db.insert("schedule", null, values);
                db.close();
                Toast.makeText(getApplicationContext(), "Date selected: " + year + "-" + (month+1) + "-" + dayOfMonth, Toast.LENGTH_SHORT).show();
            }
        });
    }

}