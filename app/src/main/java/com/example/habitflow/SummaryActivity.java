package com.example.habitflow;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        TextView tvTotal = findViewById(R.id.tvTotal);
        TextView tvCompleted = findViewById(R.id.tvCompleted);
        TextView tvBest = findViewById(R.id.tvBest);
        TextView tvOverall = findViewById(R.id.tvOverall);
        ProgressBar pbOverall = findViewById(R.id.pbOverall);
        Button btnBack = findViewById(R.id.btnBack);

        // OUTPUT
        int overall = HabitManager.overallPercent();
        tvTotal.setText("Total habits: " + HabitManager.getHabits().size());
        tvCompleted.setText("Completed habits: " + HabitManager.completedCount());
        tvBest.setText("Best streak: " + HabitManager.bestStreak() + " days");
        tvOverall.setText("Overall completion: " + overall + "%");
        pbOverall.setProgress(overall);

        btnBack.setOnClickListener(v -> finish());
    }
}