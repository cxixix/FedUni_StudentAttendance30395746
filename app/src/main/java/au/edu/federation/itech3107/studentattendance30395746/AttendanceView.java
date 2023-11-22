package au.edu.federation.itech3107.studentattendance30395746;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AttendanceView extends AppCompatActivity {
    private SQLiteDatabase db;
    List<UserModel> userList = new ArrayList<>();
    private UserAdapter userAdapter;
    private DatePicker datePicker;
    private Spinner spinnerRecentDays;
    private String selectedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_view);

        spinnerRecentDays = findViewById(R.id.spinnerRecentDays);
        // 设置 DatePicker 显示当前日期
//        setCurrentDate();

        // Setting Spinner to show the last few days
        setupRecentDaysSpinner();
        DBOpenHelper dbHelper = new DBOpenHelper(AttendanceView.this, "record.db", null, 1);
        db = dbHelper.getWritableDatabase();
        Intent intent = getIntent();
        String course_id = intent.getStringExtra("course_id");
        // perform a search
        String[] columns = {"student_name", "student_id", "status"};
        String selection = "course_id=?";
        String[] selectionArgs = {course_id};

        Cursor cursor = db.query("record", columns, selection, selectionArgs, null, null, null);

        //Get result set


        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("student_name"));
                @SuppressLint("Range") String userId = cursor.getString(cursor.getColumnIndex("student_id"));
                @SuppressLint("Range") String status = cursor.getString(cursor.getColumnIndex("status")); // 添加这一行
                // Creating Data Structures
                UserModel userModel = new UserModel(name, userId,status);
                userList.add(userModel);
            } while (cursor.moveToNext());

            // Create the adapter
            userAdapter = new UserAdapter(this, userList);

            // Setting up the adapter
            ListView listView = findViewById(R.id.listview2); // 替换为你的实际ListView的ID
            listView.setAdapter(userAdapter);

            // Close Cursor
            cursor.close();
        }
        Button saveButton = findViewById(R.id.button11);
        saveButton.setOnClickListener(v -> saveSelectedItems());

    }
    private void setCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


    }

    private void setupRecentDaysSpinner() {
        List<String> recentDaysList = getRecentDaysList(5); // Get a list of dates in the last five days

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, recentDaysList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerRecentDays.setAdapter(adapter);

        spinnerRecentDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedDate = (String) parentView.getItemAtPosition(position);
                // This is where the logic for the selected date is handled, e.g. displaying the Toast.
                Toast.makeText(AttendanceView.this, "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Logic to handle unchecked dates here
            }
        });
    }

    private List<String> getRecentDaysList(int numberOfDays) {
        List<String> recentDaysList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        for (int i = numberOfDays - 1; i >= 0; i--) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -i);
            Date recentDate = calendar.getTime();
            String formattedDate = dateFormat.format(recentDate);
            recentDaysList.add(formattedDate);
        }

        return recentDaysList;
    }

    private void saveSelectedItems() {
        if (userList != null && userAdapter != null) {
            SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
            String course_id = sharedPreferences.getString("course_id", "");

            for (UserModel userModel : userList) {
                ContentValues contentValues = new ContentValues();



                if (userModel.isSelected()) {
                    Log.d("123456", "saveSelectedItems: " + userModel.getUserId() + " " + course_id);
                    // Check if a record already exists in the database
                    String[] columns = {"id"};
                    String selection = "student_id=? AND course_id=? AND student_name=?";
                    String[] selectionArgs = {userModel.getUserId(), course_id, userModel.getName()};
                    Cursor cursor = db.query("record", columns, selection, selectionArgs, null, null, null);

                    if (cursor != null && cursor.moveToFirst()) {
                        // Records exist in the database, perform update operations.
                        contentValues.put("status", "attended");
                        contentValues.put("attendancetime", selectedDate);
                        String whereClause = "student_id=? AND course_id=? AND student_name=?";
                        String[] whereArgs = {userModel.getUserId(), course_id, userModel.getName()};
                        db.update("record", contentValues, whereClause, whereArgs);
                        Toast.makeText(this, "Attendance status updated", Toast.LENGTH_SHORT).show();
                    } else {
                        // No record exists in the database, perform an insert operation
                        contentValues.put("student_name", userModel.getName());
                        contentValues.put("student_id", userModel.getUserId());
                        contentValues.put("status", "attended");
                        contentValues.put("attendancetime", selectedDate);
                        contentValues.put("course_id", course_id);
                        db.insertWithOnConflict("record", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
                        Toast.makeText(this, "Attendance status saved", Toast.LENGTH_SHORT).show();
                    }

                    // Close Cursor
                    if (cursor != null) {
                        cursor.close();
                    }

                } else {
                    // If not checked, you may need to update to another status
                    contentValues.put("status", "absent");
                    contentValues.put("attendancetime", selectedDate);
                    String whereClause = "student_id=? AND course_id=? AND student_name=?";
                    String[] whereArgs = {userModel.getUserId(), course_id, userModel.getName()};
                    db.update("record", contentValues, whereClause, whereArgs);
                }
            }

            // Prompt to save successfully or perform another action
            Toast.makeText(this, "Attendance status saved", Toast.LENGTH_SHORT).show();
        }
    }


}
