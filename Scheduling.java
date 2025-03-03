import java.util.*;

public class Scheduling {
    
 int totalCpuTime=0;

    List<process> FCFS = new ArrayList<>();
    PriorityQueue<event> EventPQ = new PriorityQueue<>();
    PriorityQueue<process> readyQueue = new PriorityQueue<>();



   void runScheduler() {


    Collections.sort(FCFS, Comparator.comparingInt(p -> p.arrivalTime));

    int totalCpuTime=0;
    int currentime = 0;
    int totalWeightTime = 0;
    int totalWaitingTime = 0;
      
    
    for (process p : FCFS) {

        if (currentime < p.arrivalTime) {
            currentime = p.arrivalTime;
        }

       p.startTime = currentime;
       p.endTime = currentime + p.burstTime; //terminate 
       currentime = p.endTime;





    }











   }










    //public class Main 

        public static void main(String args[]){
        
        Scanner scan = new Scanner(System.in);
        Scheduling scheduler = new Scheduling();

        System.out.println("Enter the number of processes:");
        int nProcesses = scan.nextInt();
        
      
    

        System.out.println("Enter Arrival and burst time for each process:");
        
        for ( int i = 0; i< nProcesses ; i++){
        System.out.print("p" + (i+1) + " Arivval time = " ); 

        int arrivaltTime = scan.nextInt();
        scan.nextLine();
        System.out.println( " , Burst time = " ); 
        int burstTime = scan.nextInt();

        process p = new process (i + 1, arrivaltTime , burstTime );
        scheduler.FCFS.add(p);
        
        
        scan.close(); 
    
        //runScheduler();
        }

}
}