package ristudio.codepath.simpletodolist;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by IceStone on 2/29/2016.
 */
public enum TaskPriority {
    LOW("LOW"),
    MEDIUM("MEDIUM"),
    HIGH("HIGH");

    private static Map<Integer, TaskPriority> valuesToPriority = new HashMap<Integer, TaskPriority>();

    private final String text;
    TaskPriority(String text) {
        this.text = text;
    }

    public static TaskPriority fromValue(int value) {

        if (valuesToPriority.isEmpty()) {
            for (TaskPriority p : TaskPriority.values()) {
                valuesToPriority.put(p.ordinal(), p);
            }
        }
        return valuesToPriority.get(value);
    }

    @Override
    public String toString() {
        return this.text;
    }

}
