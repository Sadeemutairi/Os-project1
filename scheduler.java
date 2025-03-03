import java.util.*;

public class scheduler {

    private PriorityQueue<event> EventQueue = new PriorityQueue<>();
    private PriorityQueue<process> ReadyQueue = new PriorityQueue<>();
    List<process> prossesList = new ArrayList<>();
    private List<String> ganttChart = new ArrayList<>();
    static int Context_Switch = 1;
    int CurrentTime = 0;
    int CpuTime = 0;
    int Cpu_Idle_Time = 0;
    static int NumOfProcesses = 0;

    void runscheduler() {
        // current process
        process runningProcess = null;

        // check if ready queue and event queue is not empty and there is a process
        while (!EventQueue.isEmpty() || runningProcess != null || !ReadyQueue.isEmpty()) {

            while (!EventQueue.isEmpty() && EventQueue.peek().Time <= CurrentTime) {

                // poll the event from the event queue
                event Event = EventQueue.poll();
                // get the process from the event
                switch (Event.Type) {
                    case "arrival":
                        ReadyQueue.add(Event.process);
                        break;
                    case "preemption":
                        ReadyQueue.add(Event.process);
                        CurrentTime += Context_Switch;
                        break;
                    case "termination":
                        break;
                    default:
                        break;
                }
            } // end iner while
              // chack if the cpu is idle add a new process
            if (runningProcess == null && !ReadyQueue.isEmpty()) {
                runningProcess = ReadyQueue.poll();
                if (runningProcess.start_Time == -1)
                    runningProcess.start_Time = CurrentTime;
            }
            // check if the process is running
            if (runningProcess != null) {
                ganttChart.add(runningProcess.toString());
                runningProcess.remaining_Time--;
                CpuTime++;

                // check if the process has fineshed excution
                if (runningProcess.remaining_Time == 0) {
                    runningProcess.finish_Time = CurrentTime + 1;
                    EventQueue.add(new event(CurrentTime + 1, "termination", runningProcess));
                    runningProcess = null;
                    // check if there is a process with shortest time
                } else {
                    if (!ReadyQueue.isEmpty() && ReadyQueue.peek().remaining_Time < runningProcess.remaining_Time) {
                        EventQueue.add(new event(CurrentTime, "preemption", runningProcess));
                        runningProcess = null;
                    }

                }

            } else {
                ganttChart.add("IDLE");
                Cpu_Idle_Time++;
            }
            CurrentTime++;

        } // end while

    } // end runscheduler

    void printProcessInfo() {
        System.out.println("Scheduling Algorithm: Shortest remaining time first");
        System.out.println("Context	Switch: " + Context_Switch + " ms	");
        System.out.println("Time  Process/CS");
        for (int i = 0; i < ganttChart.size(); i++) {
            System.out.println(ganttChart.get(i));

        }

        System.out.println("Performance Metrics");
        int totalTurnaround = 0;
        int totalWaiting = 0;
        for (process p : prossesList) {
            totalTurnaround += p.getTurnaround();
            totalWaiting += p.getWaitingTime();
        }
        int AverageTurnaround = totalTurnaround / NumOfProcesses;
        int AverageWaiting = totalWaiting / NumOfProcesses;
        System.out.println("Average Turnaround Time: " + AverageTurnaround);
        System.out.println("Average Waiting Time: " + AverageWaiting);
        System.out.println("CPU Utilization: " + CpuTime);

    }

    void userInfo() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter the number of processes:");
        NumOfProcesses = scan.nextInt();

        System.out.println("Enter Arrival and burst time for each process:");
        for (int i = 0; i < NumOfProcesses; i++) {
            System.out.print("p" + (i + 1) + " Arivval time = ");
            int arrivaltTime = scan.nextInt();
            System.out.print(" , Burst time = ");
            int burstTime = scan.nextInt();
            process p = new process(i + 1, arrivaltTime, burstTime);
            prossesList.add(p);

        }
    }

    public static void main(String args[]) {
        scheduler object1 = new scheduler();
        object1.userInfo();
        object1.runscheduler();
        object1.printProcessInfo();

    }
}

/*
 * Number of processes= 4 (P1, P2, P3, P4)
 * Arrival times and burst times as follows:
 * P1: Arrival time = 0, Burst time = 8 ms
 * P2: Arrival time = 1, Burst time = 4 ms
 * P3: Arrival time = 2, Burst time = 5 ms
 * P4: Arrival time = 3, Burst time = 5 ms
 * Scheduling Algorithm: Shortest remaining time first
 * Context Switch: 1 ms
 * Time Process/CS
 * 0-1 P1
 * 1-2 CS
 * 2-6 P2
 * 6-7 CS
 * 7-12 P3
 * 12-13 CS
 * 13-18 P4
 * 18-19 CS
 * 19-26 P1
 * Performance Metrics
 * Average Turnaround Time: 14
 * Average Waiting Time: 8.5
 * CPU Utilization: 84.62
 */