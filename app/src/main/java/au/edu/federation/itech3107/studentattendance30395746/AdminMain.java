package au.edu.federation.itech3107.studentattendance30395746;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminMain extends AppCompatActivity {
    private Button addUserButton;
    private Button addCourseButton;
    private Button assignCourseButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        addUserButton = findViewById(R.id.button12);
        addCourseButton = findViewById(R.id.button4);
        assignCourseButton = findViewById(R.id.button13);
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdminMain.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdminMain.this,AddCourse.class);
                startActivity(intent);
            }
        });
        assignCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdminMain.this,AdminAssignmentCourse.class);
                startActivity(intent);
            }
        });
    }
}