package au.edu.federation.itech3107.studentattendance30395746;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

public class AdminAssignmentCourse extends AppCompatActivity {
    private DBOpenHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_assignment_course);
        ListView listView = findViewById(R.id.listview3);

        dbHelper = new DBOpenHelper(AdminAssignmentCourse.this,"record.db",null,1);
        List<Course> courseList = dbHelper.getAllCourses();

        CourseAdapter adapter = new CourseAdapter(this, courseList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Course course = courseList.get(position);
            Intent intent = new Intent(AdminAssignmentCourse.this, AlterTeacher.class);
            intent.putExtra("course_id", course.getCourseId());
            startActivity(intent);
        });
    }
}