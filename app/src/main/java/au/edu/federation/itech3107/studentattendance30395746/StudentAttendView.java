package au.edu.federation.itech3107.studentattendance30395746;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.List;

public class StudentAttendView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attend_view);
        ListView listView = findViewById(R.id.listview6);
        SharedPreferences sharedPreferences = getSharedPreferences("student", MODE_PRIVATE);
        String studentName = sharedPreferences.getString("student_name", "");
        DBOpenHelper dbHelper = new DBOpenHelper(StudentAttendView.this,"record.db",null,1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Replace "YourTableName" and "YourColumnName" with the actual table and column names.
        String[] projection = {
                "id",
                "student_id",
                "student_name",
                "course_id",
                "attendancetime",
                "status"
        };

        // Replace "YourTableName" with the actual table name.
        String selection = "student_name=?";
        String[] selectionArgs = {studentName}; // Replace with actual student names

        // Query Database
        Cursor cursor = db.query("record", projection, selection, selectionArgs, null, null, null);

        List<AttendanceData> attendanceDataList = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String studentName2 = cursor.getString(cursor.getColumnIndex("student_name"));
                @SuppressLint("Range") String courseID = cursor.getString(cursor.getColumnIndex("course_id"));
                @SuppressLint("Range") String attendanceTime = cursor.getString(cursor.getColumnIndex("attendancetime"));
                @SuppressLint("Range") String status = cursor.getString(cursor.getColumnIndex("status"));

                AttendanceData data = new AttendanceData(studentName2, courseID, attendanceTime, status);
                attendanceDataList.add(data);

            } while (cursor.moveToNext());
        }

        // Creating an ArrayAdapter
        CustomArrayAdapter adapter = new CustomArrayAdapter(this, attendanceDataList);

        // Set the adapter to the ListView
        listView.setAdapter(adapter);
    }
}
