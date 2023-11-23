package au.edu.federation.itech3107.studentattendance30395746;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CourseAdapter extends ArrayAdapter<Course> {

    private List<Course> courseList;

    public CourseAdapter(Context context, List<Course> courses) {
        super(context, 0, courses);
        this.courseList = courses;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_course, parent, false);
        }

        // Get the data item for this position
        Course course = getItem(position);

        // Find and set the views within the layout
        TextView courseNameTextView = convertView.findViewById(R.id.courseNameTextView);
        TextView teacherNameTextView = convertView.findViewById(R.id.teacherNameTextView);
        TextView startTimeTextView = convertView.findViewById(R.id.startTimeTextView);
        TextView endTimeTextView = convertView.findViewById(R.id.endTimeTextView);

        // Populate the data into the template view using the data object
        if (course != null) {
            courseNameTextView.setText("Course Name:"+course.getCourseName());
            teacherNameTextView.setText("Teacher:"+course.getTeacherName());
            startTimeTextView.setText("Start time:"+course.getStartTime());
            endTimeTextView.setText("End time:"+course.getEndTime());
        }

        // Return the completed view to render on screen
        return convertView;
    }
}