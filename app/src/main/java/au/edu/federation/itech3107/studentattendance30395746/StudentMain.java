package au.edu.federation.itech3107.studentattendance30395746;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StudentMain extends AppCompatActivity {
    Button resetPasswordButton;
    Button viewCourseScheduleButton;
    Button viewAttendanceButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        resetPasswordButton = findViewById(R.id.button16);
        viewCourseScheduleButton = findViewById(R.id.button17);
        viewAttendanceButton = findViewById(R.id.button);
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(StudentMain.this,ResetPassword.class);
                startActivity(intent);

            }
        });
        viewCourseScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(StudentMain.this,StudentChooseViewSchedule.class);
                startActivity(intent);
            }
        });
        viewAttendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(StudentMain.this,StudentAttendView.class);
                startActivity(intent);
            }
        });
        }
    }