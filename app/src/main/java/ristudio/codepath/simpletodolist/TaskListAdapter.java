package ristudio.codepath.simpletodolist;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.List;

/**
 * Created by IceStone on 3/1/2016.
 */
public class TaskListAdapter extends ArrayAdapter<Task> {


    public TaskListAdapter(Context context, List<Task> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Task task = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_row, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvDesc = (TextView) convertView.findViewById(R.id.tvDesc);
        TextView tvDeadline = (TextView) convertView.findViewById(R.id.tvDeadline);
        TextView tvPriority = (TextView) convertView.findViewById(R.id.tvPriority);
        // Populate the data into the template view using the data object
        tvName.setText(task.getName());
        tvDesc.setText(task.getDescription());

        Calendar due = task.getDeadline();
        if (due != null) {

            String dateText = getMonthForInt(due.get(Calendar.MONTH)) + " " + due.get(Calendar.DAY_OF_MONTH) + ", " + due.get(Calendar.YEAR);
            tvDeadline.setText(dateText);
        } else {
            tvDeadline.setText("");
        }

        tvPriority.setText(task.getPriority().toString());

        switch (task.getPriority()) {
            case LOW:
                tvPriority.setTextColor(Color.parseColor("#008000"));
                break;
            case MEDIUM:
                tvPriority.setTextColor(Color.parseColor("#FFD700"));
                break;
            case HIGH:
                tvPriority.setTextColor(Color.parseColor("#FF4500"));
                break;
        }
        // Return the completed view to render on screen
        return convertView;

    }

    private String getMonthForInt(int num) {
        String month = "";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }
}