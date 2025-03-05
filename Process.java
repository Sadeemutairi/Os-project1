import java.util.*;

class Process implements Comparable<Process> {
    int processId;
    int arrival_Time;
    int burst_Time;
    int remaining_Time;
    int start_Time = -1;
    int finish_Time = -1;

    public Process(int processId, int arrivalTime, int burstTime) {
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

    @Override
    public int compareTo(Process other) {
        if (this.remaining_Time != other.remaining_Time) {
            return this.remaining_Time - other.remaining_Time;
        }
        return this.arrival_Time - other.arrival_Time;
    }

    @Override
    public String toString() {
        return "P" + processId;
    }
}





