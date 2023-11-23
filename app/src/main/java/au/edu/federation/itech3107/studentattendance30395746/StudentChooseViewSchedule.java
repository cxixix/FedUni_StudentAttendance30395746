package au.edu.federation.itech3107.studentattendance30395746;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

public class StudentChooseViewSchedule extends AppCompatActivity {
    private DBOpenHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_choose_view_schedule);
        ListView listView = findViewById(R.id.listview5);
        SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
        String student_id = sp.getString("userid", "");
        String student_name = sp.getString("username", "");
        dbHelper = new DBOpenHelper(StudentChooseViewSchedule.this,"record.db",null,1);
        List<Course> courseList = dbHelper.getAllCoursesStudent(student_id, student_name);

        CourseAdapter adapter = new CourseAdapter(this, courseList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Course course = courseList.get(position);
            SharedPreferences sharedPreferences = getSharedPreferences("course_data", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("course_id", course.getCourseId());
            editor.putString("course_name", course.getCourseName());
            editor.apply();
            Intent intent = new Intent(StudentChooseViewSchedule.this, StudentSchedule.class);

            startActivity(intent);
        });
    }
}