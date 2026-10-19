package com.example.habitflow;


public class Habit {
    private String name;
    private String category;
    private String frequency;
    private int targetPerDay;
    private int goalDays;
    private int daysCompleted;
    private boolean reminder;

    public Habit(String name, String category, String frequency,
                 int targetPerDay, int goalDays, boolean reminder) {
        this.name = name;
        this.category = category;
        this.frequency = frequency;
        this.targetPerDay = targetPerDay;
        this.goalDays = goalDays;
        this.reminder = reminder;
        this.daysCompleted = 0;
    }

    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getFrequency() { return frequency; }
    public int getTargetPerDay() { return targetPerDay; }
    public int getGoalDays() { return goalDays; }
    public int getDaysCompleted() { return daysCompleted; }
    public boolean isReminder() { return reminder; }

    public void setName(String name) { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setFrequency(String frequency) { this.frequency = frequency; }
    public void setTargetPerDay(int targetPerDay) { this.targetPerDay = targetPerDay; }
    public void setGoalDays(int goalDays) { this.goalDays = goalDays; }
    public void setReminder(boolean reminder) { this.reminder = reminder; }

    public int getProgressPercent() {
        if (goalDays <= 0) return 0;
        return Math.min(100, daysCompleted * 100 / goalDays);
    }

    public boolean isCompleted() { return daysCompleted >= goalDays; }

    public boolean markDone() {
        if (isCompleted()) return false;
        daysCompleted++;
        return true;
    }
}