package au.edu.federation.itech3107.studentattendance30395746;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TeacherAdapter extends ArrayAdapter<User> {
    private Context context;
    private List<User> userList;

    public TeacherAdapter(Context context, List<User> userList) {
        super(context, R.layout.list_item_user, userList);
        this.context = context;
        this.userList = userList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = inflater.inflate(R.layout.list_item_teacher, parent, false);
        }

        User currentUser = userList.get(position);

        TextView textViewName = listItemView.findViewById(R.id.textViewName2);
        TextView textViewUserId = listItemView.findViewById(R.id.textViewUserId2);
        TextView textViewType = listItemView.findViewById(R.id.textViewType2);

        textViewName.setText("Name: " + currentUser.getName());
        textViewUserId.setText("User ID: " + currentUser.getUserId());
        textViewType.setText("Type: " + currentUser.getType());

        return listItemView;
    }
}
