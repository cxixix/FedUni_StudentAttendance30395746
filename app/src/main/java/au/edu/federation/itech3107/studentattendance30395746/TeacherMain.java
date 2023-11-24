package au.edu.federation.itech3107.studentattendance30395746;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TeacherMain extends AppCompatActivity {

        Button addStudentButton;
        Button ResetPasswordButton;
        Button scheduleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);
        addStudentButton = findViewById(R.id.button2);
        ResetPasswordButton = findViewById(R.id.button14);
        scheduleButton = findViewById(R.id.button18);
        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(TeacherMain.this,ChooseSetSchedule.class);
                startActivity(intent);
            }
        });

        addStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(TeacherMain.this,AddStutoCourse.class);
                startActivity(intent);
            }
        });
        ResetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(TeacherMain.this,ResetPassword.class);
                startActivity(intent);
            }
        });
    }
}