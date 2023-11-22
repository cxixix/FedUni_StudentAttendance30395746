package au.edu.federation.itech3107.studentattendance30395746;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
        Button loginButton;
        EditText username;
        EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = findViewById(R.id.button6);
        username = findViewById(R.id.editTextText3);
        password = findViewById(R.id.editTextTextPassword2);
        DBOpenHelper dbOpenHelper =new DBOpenHelper(MainActivity.this,"record.db",null,1);
        //Getting the database repository in write mode
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the username and password entered
                String inputUsername = username.getText().toString();
                String inputPassword = password.getText().toString();
                if (inputUsername.isEmpty()){
                    Toast.makeText(getApplicationContext(), "User name cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (inputPassword.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Query the database to verify the username
                Cursor usernameCursor = db.query(
                        "user", // Table to query
                        new String[]{"name"}, // Columns to return
                        "name=?", // Selection (where) clauses
                        new String[]{inputUsername}, // Selection of parameters
                        null, // clusters
                        null, // filtration
                        null); //arrange in order
                boolean isUsernameExists = usernameCursor.getCount() > 0;
                if (!isUsernameExists) {
                    // User name does not exist
                    Toast.makeText(getApplicationContext(), "This user is not registered, please contact an administrator", Toast.LENGTH_LONG).show();
                    Intent intent =new Intent(MainActivity.this,SignUpActivity.class);
                    startActivity(intent);
                    return; // Ends the method and does not continue
                }
                Cursor passwordCursor = db.query(
                        "user", // Table to query
                        new String[]{"password"}, // Columns to return
                        "name=? AND password=?", // Selection (where) clauses
                        new String[]{inputUsername, inputPassword}, // Selection of parameters
                        null, // clusters
                        null, // filtration
                        null); // arrange in order


                // Check that the password is correct
                boolean isPasswordValid = passwordCursor.getCount() > 0;

                if (!isPasswordValid) {
                    // incorrect password
                    Toast.makeText(getApplicationContext(), "Incorrect password, please check input", Toast.LENGTH_SHORT).show();
                    return; // Ends the method and does not continue
                }

                Cursor ID = db.query(
                        "user", // Table to query
                        new String[]{"userid"}, // Columns to return
                        "name=? AND password=?", // Selection (where) clauses
                        new String[]{inputUsername, inputPassword}, // Selection of parameters
                        null, // clusters
                        null, // filtration
                        null); // arrange in order
                SharedPreferences sp1 = getSharedPreferences("user", MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sp1.edit();
                ID.moveToFirst();

                @SuppressLint("Range") String userid = ID.getString(ID.getColumnIndex("userid"));
                editor1.putString("userid", userid);


                Cursor typeCursor = db.query(
                        "user", // Table to query
                        new String[]{"type"}, // Columns to return
                        "name=? AND password=?", // Selection (where) clauses
                        new String[]{inputUsername, inputPassword}, // Selection of parameters
                        null, // clusters
                        null, //filtration
                        null); //
                typeCursor.moveToFirst();

                @SuppressLint("Range") String type = typeCursor.getString(typeCursor.getColumnIndex("type"));

                Cursor nameCursor = db.query(
                        "user", // Table to query
                        new String[]{"name"}, // Columns to return
                        "name=? AND password=?", // Selection (where) clauses
                        new String[]{inputUsername, inputPassword}, // Selection of parameters
                        null, // clusters
                        null, // filtration
                        null); // arrange in order
                nameCursor.moveToFirst();
                @SuppressLint("Range") String name = nameCursor.getString(nameCursor.getColumnIndex("name"));
                editor1.putString("username", name);
                editor1.apply();
                if (type.equals("admin")) {
                    Intent intent = new Intent(MainActivity.this, AdminMain.class);
                    Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else if (type.equals("teacher")) {
                    SharedPreferences sp = getSharedPreferences("teacher", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("teacher_name", username.getText().toString());
                    editor.apply();
                    Intent intent = new Intent(MainActivity.this, TeacherMain.class);
                    Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else if (type.equals("student")) {
                    SharedPreferences sp = getSharedPreferences("student", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("student_name", username.getText().toString());
                    editor.apply();
                    Intent intent = new Intent(MainActivity.this, StudentMain.class);
                    Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "User type error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}