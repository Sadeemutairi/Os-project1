class process {

int PID;
int arrivalTime;
int burstTime;
int startTime;
int endTime;
int leftTime; // Used in Preemptive SJF


  public process (int PID, int arrivalTime, int burstTime){

   this.PID = PID;
   this.arrivalTime = arrivalTime;
   this.burstTime = burstTime;
   startTime = -1;
   endTime = -1;
   leftTime = burstTime;


  }





}