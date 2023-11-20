package au.edu.federation.itech3107.studentattendance30395746;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddCourse extends AppCompatActivity {

    Button addCourseButton;
    EditText courseName;
    EditText courseID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        addCourseButton = findViewById(R.id.button3);
        courseName = findViewById(R.id.editTextText4);
        courseID = findViewById(R.id.editTextText5);
        DBOpenHelper dbOpenHelper =new DBOpenHelper(AddCourse.this,"record.db",null,1);
        //Getting the database repository in write mode
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = courseName.getText().toString();
                String id = courseID.getText().toString();
                ContentValues contentValues=new ContentValues();
                contentValues.put("coursename",name);
                contentValues.put("course_id",id);
                // Check if the same course_id already exists
                boolean isDuplicate = checkIfCourseIdExists(id);

                if (!isDuplicate) {
                    // If course_id does not exist, perform the insert operation
                    db.insert("course", null, contentValues);
                    Toast.makeText(AddCourse.this, "Add Course Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // If course_id already exists, display a failure message
                    Toast.makeText(AddCourse.this, "Failed to add course with duplicate ID", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private boolean checkIfCourseIdExists(String courseId) {
        DBOpenHelper dbOpenHelper =new DBOpenHelper(AddCourse.this,"record.db",null,1);
        //Getting the database repository in write mode
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        String[] columns = {"course_id"};
        String selection = "course_id=?";
        String[] selectionArgs = {courseId};
        Cursor cursor = db.query("course", columns, selection, selectionArgs, null, null, null);

        boolean exists = cursor.getCount() > 0;


        return exists;
    }

}