package com.example.habitflow;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditHabitActivity extends AppCompatActivity {

    private final String[] categories = {"Health", "Study", "Fitness", "Finance", "Personal"};

    private EditText etName, etGoalDays;
    private Spinner spCategory;
    private RadioGroup rgFrequency;
    private RadioButton rbDaily, rbWeekly;
    private SeekBar sbTarget;
    private TextView tvTarget, tvTitle;
    private CheckBox cbReminder;
    private Button btnDelete;
    private int index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        tvTitle = findViewById(R.id.tvFormTitle);
        etName = findViewById(R.id.etName);
        etGoalDays = findViewById(R.id.etGoalDays);
        spCategory = findViewById(R.id.spCategory);
        rgFrequency = findViewById(R.id.rgFrequency);
        rbDaily = findViewById(R.id.rbDaily);
        rbWeekly = findViewById(R.id.rbWeekly);
        sbTarget = findViewById(R.id.sbTarget);
        tvTarget = findViewById(R.id.tvTarget);
        cbReminder = findViewById(R.id.cbReminder);
        Button btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);

        spCategory.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, categories));

        sbTarget.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar s, int progress, boolean fromUser) {
                tvTarget.setText("Daily target: " + Math.max(1, progress));
            }
            @Override public void onStartTrackingTouch(SeekBar s) {}
            @Override public void onStopTrackingTouch(SeekBar s) {}
        });
        sbTarget.setProgress(1);
        tvTarget.setText("Daily target: 1");

        index = getIntent().getIntExtra("index", -1);
        if (index >= 0) {
            loadForEdit();
            btnDelete.setVisibility(View.VISIBLE);
            btnDelete.setOnClickListener(v -> confirmDelete());
        }

        btnSave.setOnClickListener(v -> save());
    }

    private void loadForEdit() {
        tvTitle.setText("Edit Habit");
        Habit h = HabitManager.get(index);
        etName.setText(h.getName());
        etGoalDays.setText(String.valueOf(h.getGoalDays()));
        for (int i = 0; i < categories.length; i++) {
            if (categories[i].equals(h.getCategory())) spCategory.setSelection(i);
        }
        if (h.getFrequency().equals("Weekly")) rbWeekly.setChecked(true);
        else rbDaily.setChecked(true);
        sbTarget.setProgress(h.getTargetPerDay());
        cbReminder.setChecked(h.isReminder());
    }

    // DELETE
    private void confirmDelete() {
        new AlertDialog.Builder(this)
                .setTitle("Delete habit")
                .setMessage("Delete \"" + HabitManager.get(index).getName() + "\"?")
                .setPositiveButton("Delete", (d, w) -> {
                    HabitManager.delete(index);
                    Toast.makeText(this, "Habit deleted", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void save() {
        // INPUT
        String name = etName.getText().toString().trim();
        String goalText = etGoalDays.getText().toString().trim();

        // PROCESS: validation
        if (name.isEmpty()) {
            etName.setError("Habit name is required");
            return;
        }
        if (goalText.isEmpty()) {
            etGoalDays.setError("Enter goal days");
            return;
        }
        int goalDays = Integer.parseInt(goalText);
        if (goalDays <= 0) {
            etGoalDays.setError("Must be greater than 0");
            return;
        }

        String category = spCategory.getSelectedItem().toString();
        String frequency = rbWeekly.isChecked() ? "Weekly" : "Daily";
        int target = Math.max(1, sbTarget.getProgress());
        boolean reminder = cbReminder.isChecked();

        if (index >= 0) {
            // UPDATE
            Habit h = HabitManager.get(index);
            h.setName(name);
            h.setCategory(category);
            h.setFrequency(frequency);
            h.setTargetPerDay(target);
            h.setGoalDays(goalDays);
            h.setReminder(reminder);
            Toast.makeText(this, "Habit updated", Toast.LENGTH_SHORT).show();
        } else {
            // CREATE
            HabitManager.add(new Habit(name, category, frequency, target, goalDays, reminder));
            Toast.makeText(this, "Habit added", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}