package au.edu.federation.itech3107.studentattendance30395746;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

public class AddStutoCourse extends AppCompatActivity {
    private DBOpenHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stuto_course);
        ListView listView = findViewById(R.id.listView);
        SharedPreferences sp = getSharedPreferences("teacher", MODE_PRIVATE);
        String teacherName = sp.getString("teacher_name", "");
        dbHelper = new DBOpenHelper(AddStutoCourse.this,"record.db",null,1);

        List<Course> courseList = dbHelper.getAllTeacherCourses(teacherName);

        CourseAdapter adapter = new CourseAdapter(this, courseList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Course course = courseList.get(position);
            SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("course_id", course.getCourseId());
            editor.apply();
                Intent intent = new Intent(AddStutoCourse.this, SetCourseTime.class);
                intent.putExtra("course_id", course.getCourseId());
                intent.putExtra("course_name", course.getCourseName());
                startActivity(intent);

                finish();
        });
    }
}