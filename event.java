import java.util.*;

public class event implements Comparable<event> {
    int Time;
    String Type;
    process process;

    public event(int Time, String Type, process process) {
        this.Time = Time;
        this.Type = Type;
        this.process = process;
    }

    public int compareTo(event other) {
        return this.Time - other.Time;
    }

}
