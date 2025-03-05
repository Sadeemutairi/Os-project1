import java.util.*;

public class Scheduler {
    // Priority queues to handle events and ready processes
    private PriorityQueue<Event> eventQueue = new PriorityQueue<>(Comparator.comparingInt(e -> e.Time));
    private PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator.comparingInt(p -> p.remaining_Time));

    // List to hold processes and Gantt chart
    private ArrayList<Process> processList = new ArrayList<>();
    private ArrayList<String> ganttChart = new ArrayList<>();

    // Constant for context switch time (1ms)
    static final int CONTEXT_SWITCH_TIME = 1;

    // Variables to track current time, CPU time, and idle time
    int currentTime = 0, cpuTime = 0, idleTime = 0;

    // Number of processes in the system
    static int numProcesses = 0;

    public void runScheduler() {
        Process runningProcess = null;
        int lastStartTime = -1; // Track when a process starts running
    
        while (!eventQueue.isEmpty() || !readyQueue.isEmpty() || runningProcess != null) {
            // Process all arrival events at the current time
            while (!eventQueue.isEmpty() && eventQueue.peek().Time <= currentTime) {
                Event event = eventQueue.poll();
                if (event.Type.equals("arrival")) {
                    readyQueue.add(event.process);
                }
            }
            /* 
    
            // If no process is running and the ready queue is empty, the CPU is idle
            if (runningProcess == null && readyQueue.isEmpty() && eventQueue.isEmpty()) {
                break; // Exit if no remaining processes
            } 
            else if (runningProcess == null && readyQueue.isEmpty()) {
                if (lastStartTime != -1) { // Log the last running process before idle
                    ganttChart.add(lastStartTime + "-" + currentTime + " P" + runningProcess.processId);
                    lastStartTime = -1;
                }
                idleTime++;
                currentTime++;
                continue;
            } */
    
            // If a new process has a shorter remaining time, switch
            if (runningProcess == null || (!readyQueue.isEmpty() && readyQueue.peek().remaining_Time < runningProcess.remaining_Time)) {
                if (runningProcess != null) {
                    // Log last running process before context switch
                    ganttChart.add(lastStartTime + "-" + currentTime + " P" + runningProcess.processId);
                    ganttChart.add(currentTime + "-" + (currentTime + CONTEXT_SWITCH_TIME) + " CS");
                        currentTime += CONTEXT_SWITCH_TIME;
                    
                    
                    readyQueue.add(runningProcess);
                }
    
                // Pick the new process to run
                runningProcess = readyQueue.poll();
                lastStartTime = currentTime; // Track the start time of this process
                if (runningProcess.start_Time == -1) {
                    runningProcess.start_Time = currentTime;
                }
            }
    
            // Execute process and log it
            runningProcess.remaining_Time--;
            cpuTime++;
            currentTime++;
    
            // If process is finished, log and remove it
            if (runningProcess.remaining_Time == 0) {
                ganttChart.add(lastStartTime + "-" + currentTime + " P" + runningProcess.processId);
                
                
    
                runningProcess.finish_Time = currentTime;
                runningProcess = null;
                lastStartTime = -1; // Reset for the next process

                // *Only add CS if there is another process waiting*
                if (!readyQueue.isEmpty()) { 
                    ganttChart.add(currentTime + "-" + (currentTime + CONTEXT_SWITCH_TIME) + " CS");
                    currentTime += CONTEXT_SWITCH_TIME;
                }
            }
        }
        System.out.println("\nProcess Execution Details:");
        System.out.println("-------------------------------------------------");
        System.out.printf("%-5s %-10s %-10s %-10s %-10s %-10s\n", 
                          "PID", "Arrival", "Burst", "Finish", "TAT", "Waiting");
        System.out.println("-------------------------------------------------");
    
        for (Process p : processList) { // Assuming 'allProcesses' contains all processes
            System.out.printf("%-5d %-10d %-10d %-10d %-10d %-10d\n", 
                              p.processId, p.arrival_Time, p.burst_Time, 
                              p.finish_Time, p.getTurnaround(), p.getWaitingTime());
        }
    } 
    

    // Method to print the results of the scheduling
    public void printResults() {
        double totalTurnaround = 0, totalWaiting = 0;

        // Print basic details of the processes
        System.out.println("\nNumber of processes= " + numProcesses);
        System.out.println("Arrival times and burst times as follows:");
        for (Process p : processList) {
            System.out.println("P" + p.processId + ": Arrival time = " + p.arrival_Time + ", Burst time = " + p.burst_Time + " ms");
        }
        System.out.println("Scheduling Algorithm: Shortest remaining time first");
        System.out.println("Context Switch: " + CONTEXT_SWITCH_TIME + " ms");

        // Print the Gantt chart with process and context switch timings
        System.out.println("\nTime  Process/CS");
        for (String event : ganttChart) {
            System.out.println(event);
        }

        // Calculate and print performance metrics
        System.out.println("\nPerformance Metrics");
        for (Process p : processList) {
            totalTurnaround += p.getTurnaround();
            totalWaiting += p.getWaitingTime();
        }

        // Calculate averages for turnaround and waiting time
        double avgTurnaround = totalTurnaround / numProcesses;
        double avgWaiting = totalWaiting / numProcesses;
        double cpuUtilization = ((double) cpuTime / currentTime) * 100;

        // Print the final performance results
        System.out.printf("Average Turnaround Time: %.2f%n", avgTurnaround);
        System.out.printf("Average Waiting Time: %.2f%n", avgWaiting);
        System.out.printf("CPU Utilization: %.2f%%%n", cpuUtilization);
    }

    // Method to get user input for processes
    public void getUserInput() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        numProcesses = scanner.nextInt();

        System.out.println("Enter Arrival Time and Burst Time for each process:");
        for (int i = 0; i < numProcesses; i++) {
            System.out.print("P" + (i + 1) + " Arrival Time = ");
            int arrivalTime = scanner.nextInt();
            System.out.print(" Burst Time = ");
            int burst_Time = scanner.nextInt();

            Process p = new Process(i + 1, arrivalTime, burst_Time);
            processList.add(p);
            eventQueue.add(new Event(arrivalTime, "arrival", p)); // Add an arrival event for each process
        }
        scanner.close();
    }

    // Main method to run the scheduler
    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler();
        scheduler.getUserInput(); // Get user input for processes
        scheduler.runScheduler(); // Run the scheduling algorithm
        scheduler.printResults(); // Print the results of the scheduling
    }
}
