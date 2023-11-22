package au.edu.federation.itech3107.studentattendance30395746;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    private RadioButton teacherRadioButton;
    private RadioButton studentRadioButton;
    private RadioButton adminRadioButton;
    private Button confirmButton;

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText idEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        teacherRadioButton = findViewById(R.id.radioButton);
        studentRadioButton = findViewById(R.id.radioButton2);
        adminRadioButton = findViewById(R.id.radioButton3);
        confirmButton = findViewById(R.id.button5);
        usernameEditText = findViewById(R.id.editTextText);
        passwordEditText = findViewById(R.id.editTextTextPassword);
        idEditText = findViewById(R.id.editTextText2);
        DBOpenHelper dbOpenHelper =new DBOpenHelper(SignUpActivity.this,"record.db",null,1);
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        // Setting the click listener for the confirmation button
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Checking the selection status of a radio box
                if (teacherRadioButton.isChecked()) {
                    // Check that the username and password are not empty
                    if (usernameEditText.getText().toString().isEmpty() || passwordEditText.getText().toString().isEmpty()) {
                        Toast.makeText(SignUpActivity.this, "Please enter a username and password", Toast.LENGTH_SHORT).show();
                    }
                    String username = usernameEditText.getText().toString();
                    String password = passwordEditText.getText().toString();
                    String id = idEditText.getText().toString();
                    ContentValues contentValues=new ContentValues();
                    contentValues.put("userid",id);
                    contentValues.put("name",username);
                    contentValues.put("password",password);
                    contentValues.put("type","teacher");
                    db.insertWithOnConflict("user",null,contentValues,SQLiteDatabase.CONFLICT_IGNORE);
                    Toast.makeText(SignUpActivity.this, "Sign up successfully", Toast.LENGTH_SHORT).show();

                } else if (studentRadioButton.isChecked()) {
                    // Check that the username and password are not empty
                    if (usernameEditText.getText().toString().isEmpty() || passwordEditText.getText().toString().isEmpty()) {
                        Toast.makeText(SignUpActivity.this, "Please enter a username and password", Toast.LENGTH_SHORT).show();
                    }
                    String username = usernameEditText.getText().toString();
                    String password = passwordEditText.getText().toString();
                    String id = idEditText.getText().toString();
                    ContentValues contentValues=new ContentValues();
                    contentValues.put("userid",id);
                    contentValues.put("name",username);
                    contentValues.put("password",password);
                    contentValues.put("type","student");
                    db.insertWithOnConflict("user",null,contentValues,SQLiteDatabase.CONFLICT_IGNORE);
                    Toast.makeText(SignUpActivity.this, "Sign up successfully", Toast.LENGTH_SHORT).show();
                } else if (adminRadioButton.isChecked()) {
                    if (usernameEditText.getText().toString().isEmpty() || passwordEditText.getText().toString().isEmpty()) {
                        Toast.makeText(SignUpActivity.this, "Please enter a username and password", Toast.LENGTH_SHORT).show();
                    }
                    String username = usernameEditText.getText().toString();
                    String password = passwordEditText.getText().toString();
                    String id = idEditText.getText().toString();
                    ContentValues contentValues=new ContentValues();
                    contentValues.put("userid",id);
                    contentValues.put("name",username);
                    contentValues.put("password",password);
                    contentValues.put("type","admin");
                    db.insertWithOnConflict("user",null,contentValues,SQLiteDatabase.CONFLICT_IGNORE);
                    Toast.makeText(SignUpActivity.this, "Sign up successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignUpActivity.this, "Please select a role", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}