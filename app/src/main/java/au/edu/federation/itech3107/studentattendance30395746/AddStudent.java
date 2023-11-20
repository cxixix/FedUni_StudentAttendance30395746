package au.edu.federation.itech3107.studentattendance30395746;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddStudent extends AppCompatActivity {
    Button addStudentButton;
    EditText studentName;
    EditText studentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        addStudentButton = findViewById(R.id.button10);
        studentName = findViewById(R.id.editTextText7);
        studentID = findViewById(R.id.editTextText6);
        DBOpenHelper dbOpenHelper =new DBOpenHelper(AddStudent.this,"record.db",null,1);
        //Getting the database repository in write mode
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        addStudentButton.setOnClickListener(v -> {
            String name = studentName.getText().toString();
            String id = studentID.getText().toString();
            String course_id = getIntent().getStringExtra("course_id");
            String course_name = getIntent().getStringExtra("course_name");
            ContentValues contentValues=new ContentValues();
            contentValues.put("student_id",id);
            contentValues.put("student_name",name);
            contentValues.put("course_id",course_id);
            contentValues.put("status","absent");
            db.insertWithOnConflict("record",null,contentValues,SQLiteDatabase.CONFLICT_IGNORE);

            ContentValues contentValues1 = new ContentValues();
            contentValues1.put("course_id", course_id);
            contentValues1.put("coursename",course_name);
            contentValues1.put("userid", id);
            contentValues1.put("name", name);
            contentValues1.put("type", "student");
            db.insertWithOnConflict("designatedcourses",null,contentValues1,SQLiteDatabase.CONFLICT_IGNORE);


            Toast.makeText(AddStudent.this, "Add student successfully", Toast.LENGTH_SHORT).show();
        });
    }
}