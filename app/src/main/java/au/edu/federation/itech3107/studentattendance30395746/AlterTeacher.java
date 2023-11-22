package au.edu.federation.itech3107.studentattendance30395746;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class AlterTeacher extends AppCompatActivity {
    private List<User> teacherList;
    private ArrayAdapter<User> adapter;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_teacher);
        Intent intent = getIntent();
        String course_id = intent.getStringExtra("course_id");
        listView = findViewById(R.id.listview4);
        teacherList = getTeachersFromDatabase();

        adapter = new TeacherAdapter(this, teacherList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            User teacher = teacherList.get(position);
            String teacher_name = teacher.getName();
            DBOpenHelper dbHelper = new DBOpenHelper(AlterTeacher.this,"record.db",null,1);
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("teachername", teacher_name);
            db.update("course", values, "course_id = ?", new String[]{course_id});
            Toast.makeText(AlterTeacher.this, "Alter teacher successfully", Toast.LENGTH_SHORT).show();

        });
    }

    private List<User> getTeachersFromDatabase() {
        DBOpenHelper dbHelper = new DBOpenHelper(AlterTeacher.this,"record.db",null,1);
        return dbHelper.getTeachers();
    }
}

