
public class event implements Comparable<event> { 
    int time;
    String type; 
    Process process; 

   
    public event(int time, String type, Process process) {
        this.time = time;
        this.type = type;
        this.process = process;
    }

    
  
    public int compareTo(event other) {
        return Integer.compare(this.time, other.time); //  Correct comparison for sorting
    }
}
