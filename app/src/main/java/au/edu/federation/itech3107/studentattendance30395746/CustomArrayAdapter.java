package au.edu.federation.itech3107.studentattendance30395746;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter<AttendanceData> {

    private LayoutInflater inflater;

    public CustomArrayAdapter(Context context, List<AttendanceData> objects) {
        super(context, 0, objects);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_attend, parent, false);
        }

        // Get the data items for this location
        AttendanceData data = getItem(position);

        // Find the TextView in the custom layout
        TextView textViewStudentName = convertView.findViewById(R.id.textViewStudentName);
        TextView textViewCourseID = convertView.findViewById(R.id.textViewCourseID);
        TextView textViewAttendanceTime = convertView.findViewById(R.id.textViewAttendanceTime);
        TextView textViewStatus = convertView.findViewById(R.id.textViewStatus);

        // Setting data into TextView
        textViewStudentName.setText("Student Name: " + data.getStudentName());
        textViewCourseID.setText("Course ID: " + data.getCourseID());
        textViewAttendanceTime.setText("Attendance Times: " + data.getAttendanceTime());
        textViewStatus.setText("Status: " + data.getStatus());

        return convertView;
    }
}