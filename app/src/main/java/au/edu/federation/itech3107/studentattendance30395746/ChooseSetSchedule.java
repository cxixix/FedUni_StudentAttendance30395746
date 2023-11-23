package au.edu.federation.itech3107.studentattendance30395746;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

public class ChooseSetSchedule extends AppCompatActivity {
    private DBOpenHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_set_schedule);
        ListView listView = findViewById(R.id.listview4);
        SharedPreferences sharedPreferences = getSharedPreferences("teacher", MODE_PRIVATE);
        String teacherName = sharedPreferences.getString("teacher_name", "");
        dbHelper = new DBOpenHelper(ChooseSetSchedule.this,"record.db",null,1);
        List<Course> courseList = dbHelper.getAllTeacherCourses(teacherName);

        CourseAdapter adapter = new CourseAdapter(this, courseList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Course course = courseList.get(position);
            Intent intent = new Intent(ChooseSetSchedule.this, CourseSchedule.class);
            intent.putExtra("course_id", course.getCourseId());
            intent.putExtra("course_name", course.getCourseName());
            startActivity(intent);
        });
    }
}