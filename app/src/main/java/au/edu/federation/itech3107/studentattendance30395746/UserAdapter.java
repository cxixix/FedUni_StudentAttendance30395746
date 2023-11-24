package au.edu.federation.itech3107.studentattendance30395746;

// UserAdapter.java

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;



public class UserAdapter extends ArrayAdapter<UserModel> {

    public UserAdapter(Context context, List<UserModel> userList) {
        super(context, 0, userList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        UserModel userModel = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_user, parent, false);
        }

        TextView nameTextView = convertView.findViewById(R.id.textViewName);
        TextView userIdTextView = convertView.findViewById(R.id.textViewUserId);
        TextView courseIdTextView = convertView.findViewById(R.id.textViewCourseId);
        CheckBox checkBox = convertView.findViewById(R.id.checkBox);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        String course_id = sharedPreferences.getString("course_id", "");
        nameTextView.setText("Name:"+userModel.getName());
        userIdTextView.setText("ID:"+userModel.getUserId());
        courseIdTextView.setText("Course ID:"+course_id); // Show course_id

        // Handling checkbox status
        checkBox.setOnCheckedChangeListener(null); // Avoid triggering the listener repeatedly
        checkBox.setChecked(userModel.isSelected());
        checkBox.setTag(position);
        // Handling of checkboxes when their status changes
        if (userModel.getStatus().equals("attended")) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Handling of checkboxes when their status changes
            userModel.setSelected(isChecked);

        });

        return convertView;
    }
}

