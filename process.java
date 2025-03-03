import java.util.*;
import java.util.PriorityQueue;

public class process implements Comparable<process> {
    int processId;
    int arrival_Time;
    int burst_Time;
    int remaining_Time;
    int start_Time = -1;
    int finish_Time;

    public process(int processId, int arrivalTime, int burstTime) {
        this.processId = processId;
        this.arrival_Time = arrivalTime;
        this.burst_Time = burstTime;
        this.remaining_Time = burstTime;
    }

    public int getTurnaround() {
        return finish_Time - arrival_Time;
    }

    public int getWaitingTime() {
        return getTurnaround() - burst_Time;
    }

    public int compareTo(process other) {
        if (this.remaining_Time != other.remaining_Time) {
            return this.remaining_Time - other.remaining_Time;
        }
        return this.arrival_Time - other.arrival_Time;
    }
}