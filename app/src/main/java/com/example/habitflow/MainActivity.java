package com.example.habitflow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private TextView tvEmpty;
    private HabitAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listHabits);
        tvEmpty = findViewById(R.id.tvEmpty);
        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnSummary = findViewById(R.id.btnSummary);

        adapter = new HabitAdapter(this, HabitManager.getHabits());
        listView.setAdapter(adapter);

        // CREATE
        btnAdd.setOnClickListener(v -> {
            Intent i = new Intent(this, AddEditHabitActivity.class);
            i.putExtra("index", -1);
            startActivity(i);
        });

        btnSummary.setOnClickListener(v ->
                startActivity(new Intent(this, SummaryActivity.class)));

        // UPDATE: tap a habit
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent i = new Intent(this, AddEditHabitActivity.class);
            i.putExtra("index", position);
            startActivity(i);
        });

        // DELETE: long press a habit
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            new AlertDialog.Builder(this)
                    .setTitle("Delete habit")
                    .setMessage("Delete \"" + HabitManager.get(position).getName() + "\"?")
                    .setPositiveButton("Delete", (d, w) -> {
                        HabitManager.delete(position);
                        refresh();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh(); // READ
    }

    private void refresh() {
        adapter.notifyDataSetChanged();
        tvEmpty.setVisibility(HabitManager.getHabits().isEmpty() ? View.VISIBLE : View.GONE);
    }
}