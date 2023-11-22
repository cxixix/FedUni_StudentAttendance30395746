package au.edu.federation.itech3107.studentattendance30395746;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResetPassword extends AppCompatActivity {
    private DBOpenHelper dbOpenHelper;
    private EditText oldPassword;
    private EditText newPassword;
    private EditText confirmPassword;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        oldPassword = findViewById(R.id.oldPassword);
        newPassword = findViewById(R.id.newPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        button = findViewById(R.id.button15);

        dbOpenHelper =new DBOpenHelper(ResetPassword.this,"record.db",null,1);
        //Getting the database repository in write mode
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
// For example, get the original, new and confirmation passwords from the fields entered by the user
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
                String userId = sp.getString("userid", "");
                String oldPasswordText = oldPassword.getText().toString();
                String newPasswordText = newPassword.getText().toString();
                String confirmPasswordText = confirmPassword.getText().toString();
                boolean passwordChanged = dbOpenHelper.changePassword(userId, oldPasswordText, newPasswordText, confirmPasswordText);

                if (passwordChanged) {
                       // Password updated successfully
                    Toast.makeText(getApplicationContext(), "Password changed successfully", Toast.LENGTH_SHORT).show();

                } else {
                    // Password update failed, maybe the original password does not match or the new password does not match the confirmation password.
                    Toast.makeText(getApplicationContext(), "Password change failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}