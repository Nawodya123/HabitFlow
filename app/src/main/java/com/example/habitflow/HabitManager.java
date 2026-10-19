package com.example.habitflow;


import java.util.ArrayList;

public class HabitManager {
    private static final ArrayList<Habit> habits = new ArrayList<>();

    public static ArrayList<Habit> getHabits() { return habits; }
    public static void add(Habit h) { habits.add(h); }
    public static Habit get(int index) { return habits.get(index); }
    public static void delete(int index) { habits.remove(index); }

    public static int completedCount() {
        int c = 0;
        for (Habit h : habits) if (h.isCompleted()) c++;
        return c;
    }

    public static int bestStreak() {
        int best = 0;
        for (Habit h : habits) best = Math.max(best, h.getDaysCompleted());
        return best;
    }

    public static int overallPercent() {
        int done = 0, goal = 0;
        for (Habit h : habits) {
            done += Math.min(h.getDaysCompleted(), h.getGoalDays());
            goal += h.getGoalDays();
        }
        return goal == 0 ? 0 : done * 100 / goal;
    }
}