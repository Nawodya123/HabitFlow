package com.example.habitflow;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class HabitAdapter extends ArrayAdapter<Habit> {

    public HabitAdapter(Context context, List<Habit> habits) {
        super(context, 0, habits);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_habit, parent, false);
        }
        final Habit h = getItem(position);

        TextView tvName = convertView.findViewById(R.id.tvItemName);
        TextView tvDetails = convertView.findViewById(R.id.tvItemDetails);
        ProgressBar pb = convertView.findViewById(R.id.pbItem);
        Button btnDone = convertView.findViewById(R.id.btnDone);

        tvName.setText(h.getName());
        tvDetails.setText(h.getCategory() + " | " + h.getFrequency()
                + " | " + h.getDaysCompleted() + "/" + h.getGoalDays()
                + " days (" + h.getProgressPercent() + "%)");
        pb.setProgress(h.getProgressPercent());
        btnDone.setText(h.isCompleted() ? "Completed" : "Done");

        btnDone.setOnClickListener(v -> {
            if (h.markDone()) {
                Toast.makeText(getContext(), "Streak: " + h.getDaysCompleted()
                        + " days!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Goal already reached!",
                        Toast.LENGTH_SHORT).show();
            }
            notifyDataSetChanged();
        });
        return convertView;
    }
}