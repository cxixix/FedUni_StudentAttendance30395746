package au.edu.federation.itech3107.studentattendance30395746;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SetCourseTime extends AppCompatActivity {
    private CalendarView calendarView;
    private Button addTimeButton;
    private Button AttendButton;
    private Button addStudentButton;
    private DBOpenHelper dbHelper;

    private String selectedStartDate;
    private String selectedEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_course_time);


        calendarView = findViewById(R.id.calendarView);
        addTimeButton = findViewById(R.id.button7);
        AttendButton = findViewById(R.id.button8);
        addStudentButton = findViewById(R.id.button9);
        dbHelper = new DBOpenHelper(SetCourseTime.this, "record.db", null, 1);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                handleDateSelection(year, month, dayOfMonth);
            }
        });
        addStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = getIntent();
                String course_id = intent2.getStringExtra("course_id");
                String course_name = intent2.getStringExtra("course_name");
                Log.d("11111111", "onClick: "+course_id);
                Intent intent = new Intent(SetCourseTime.this, AddStudent.class);
                intent.putExtra("course_id", course_id);
                intent.putExtra("course_name", course_name);
                startActivity(intent);
            }
        });

        AttendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = getIntent();
                String course_id = intent2.getStringExtra("course_id");
                Log.d("11111111", "onClick: "+course_id);
                Intent intent = new Intent(SetCourseTime.this, AttendanceView.class);
                intent.putExtra("course_id", course_id);
                startActivity(intent);
            }
        });
        addTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCourseToDatabase();
            }
        });
    }

    private void handleDateSelection(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String selectedDate = dateFormat.format(calendar.getTime());

        if (selectedStartDate == null) {
            selectedStartDate = selectedDate;
            Toast.makeText(SetCourseTime.this, "Start Date Selected: " + selectedDate, Toast.LENGTH_SHORT).show();
        } else {
            selectedEndDate = selectedDate;
            Toast.makeText(SetCourseTime.this, "End Date Selected: " + selectedDate, Toast.LENGTH_SHORT).show();
        }
    }

    private void saveCourseToDatabase() {
        if (selectedStartDate != null && selectedEndDate != null) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Intent intent = getIntent();
            String course_id = intent.getStringExtra("course_id");
            // Check if a record already exists
            Cursor cursor = db.rawQuery("SELECT * FROM course WHERE course_id ="+course_id, null);

            if (cursor.moveToFirst()) {
                // If a record already exists and the start time and end time are null, perform the update operation
                ContentValues values = new ContentValues();
                values.put("course_start_time", selectedStartDate);
                values.put("course_end_time", selectedEndDate);

                int rowsAffected = db.update("course", values, "course_id ="+course_id, null);

                if (rowsAffected > 0) {
                    Toast.makeText(SetCourseTime.this, "Course updated in database", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SetCourseTime.this, "Failed to update course in database", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}