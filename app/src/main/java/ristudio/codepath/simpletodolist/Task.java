package ristudio.codepath.simpletodolist;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by IceStone on 2/29/2016.
 */
public class Task {

    private String name;
    private String description;
    private Calendar deadline;
    private TaskPriority priority;
    private boolean isComplete;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.priority = TaskPriority.LOW; // default priority is LOW
        this.deadline = Calendar.getInstance(); // deadline is today by default
        this.isComplete = false;
    }

    public Task(String name, String description, long dueDateInMillis, int priority) {
        this.name = name;
        this.description = description;
        this.isComplete = false;
        this.deadline = new GregorianCalendar();
        this.deadline.setTimeInMillis(dueDateInMillis);
        this.priority = TaskPriority.fromValue(priority);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getDeadline() {
        return deadline;
    }

    public void setDeadline(Calendar deadline) {
        this.deadline = deadline;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public String toString() {
        return name + "," + description + "," + this.deadline.getTimeInMillis() + "," + this.priority.ordinal();
    }
}